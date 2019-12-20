import socket
import logbook
import sys
import struct
import time
import threading
import random
from exception import ParseIdentifierError


# logger
logbook.StreamHandler(sys.stdout, level=logbook.DEBUG).push_application()
log = logbook.Logger('CHAT')
# global variables
MAX_CONNECTION = 64
UDP_PORT = 4456
TCP_PORT = 4457


"""
UTILS
"""


def create_identifier(name, ip):
	name = name + b'\x00'.decode()

	# only 16 characters
	if len(name) > 16:
		raise ValueError

	return struct.pack('B%ds' % len(name), len(name), name.encode()) + socket.inet_aton(ip)


def decode_identifier(data):
	try:
		# decode name length
		start = 0
		end = 1
		name_length = struct.unpack('B', data[start:end])[0]
		# decode name
		start = end
		end += name_length
		name = struct.unpack('%ds' % name_length, data[start:end])[0]
		name = name[:-1].decode()
		# decode IP address
		start = end
		ip = socket.inet_ntoa(data[start:])

		return ip, name
	except:
		raise ParseIdentifierError('test', 'message')


def get_packet_prefix(name):
	switch = {
		# UDP prefix
		'HELLO': struct.pack('B', 0x1),
		'POPULATE': struct.pack('B', 0x2),
		'BYE': struct.pack('B', 0x3),
		# TCP prefix
		'CHAT': struct.pack('B', 0x1),
		'ACK': struct.pack('B', 0x2),
	}

	return switch[name]


def get_packet(name, **kwargs):
	switch = {
		# UDP packets
		'HELLO': get_packet_prefix('HELLO') + kwargs.get('identifier', b''),
		'POPULATE': get_packet_prefix('POPULATE') + kwargs.get('size', b'') + kwargs.get('identifiers', b''),
		'BYE': get_packet_prefix('BYE'),
		# TCP packets
		'CHAT': get_packet_prefix('CHAT') + kwargs.get('recipient', b'') + kwargs.get('sender', b'') + kwargs.get('message', b'') + 
			kwargs.get('forwarders', b''),
		'ACK': get_packet_prefix('ACK') + kwargs.get('recipient', b'') + kwargs.get('sender', b'') + kwargs.get('message', b'') + 
			kwargs.get('forwarders', b''),
	}

	return switch[name]


def add_to_nodes(nodes, ip, name, identifier):
	# add to dict
	nodes[ip] = {'name': name, 'identifier': identifier, 'time': time.time()}
	# sort by IP address
	new_nodes = {}
	for ip in sorted(nodes.keys(), key=lambda item: socket.inet_aton(item)):
		new_nodes[ip] = nodes[ip]
	# reset nodes
	for ip, node in list(nodes.items()):
		nodes.pop(ip, None)
	# then recreate
	for ip in new_nodes.keys():
		nodes[ip] = new_nodes[ip]
	# inform user
	log.notice('%s joins the chat' % name)


def node_is_successor(current_ip, node_ip, nodes):
	current_ip_index = list(nodes.keys()).index(current_ip)
	node_ip_index = list(nodes.keys()).index(node_ip)

	if current_ip_index == len(nodes) - 1 and node_ip_index == 0:
		return True
	elif node_ip_index == current_ip_index + 1:
		return True

	return False


def get_successor_ip(current_ip, nodes):
	keys = list(nodes.keys())
	current_ip_index = keys.index(current_ip)
	successor_index = current_ip_index + 1 if current_ip_index != len(nodes) - 1 else 0
	return keys[successor_index]


"""
SENDERS
"""


def send_with_udp(udp, ip, message):
	address = (ip, UDP_PORT)
	udp.sendto(message, address)
	log.debug('SEND >> %s: %s' % (ip, message))


def send_with_tcp(ip, message):
	# create TCP socket client
	client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	address = (ip, TCP_PORT)
	try:
		client.connect(address)
		client.send(message)
		log.debug('SEND >> %s: %s' % (ip, message))
	except ConnectionRefusedError:
		log.warn('SEND >> FAILED >> %s: %s' % (ip, message))
	client.close()


def send_populate_message(udp, address, nodes):
	size = struct.pack('B', len(nodes))
	identifiers = b''
	for node in nodes.values():
		identifiers += node['identifier']
	send_with_udp(udp, address[0], get_packet('POPULATE', size=size, identifiers=identifiers))


def send_confirmation_message(current_ip, sender_ip, nodes):
	message = ''
	data = struct.pack('B%ds' % len(message), len(message), message.encode())
	sender = nodes[current_ip]['identifier']
	recipient = nodes[sender_ip]['identifier']

	send_with_tcp(sender_ip, get_packet('ACK', recipient=recipient, sender=sender, message=data))


"""
HANDLERS
"""


def handle_chat_message(tcp, address, data, current_ip, nodes):
	data = data[1:]
	log.debug('RECV << CHAT %s: %s' % (address, data))
	# get recipient information
	start = 0
	recipient_data_length = 1 + struct.unpack('B', data[:1])[0] + 4
	end = recipient_data_length
	recipient = decode_identifier(data[start:end])
	# if i'm not the recipient then forward the message
	if recipient[0] != current_ip:
		# get successor ip
		successor_ip = get_successor_ip(current_ip, nodes)
		# add my identifier to forwarders
		data += nodes[current_ip]['identifier']
		# send message
		send_with_tcp(successor_ip, get_packet_prefix('CHAT') +  data)
	else:
		# get sender information
		start = end
		sender_data_length = 1 + struct.unpack('B', data[start:start + 1])[0] + 4
		end = start + sender_data_length
		sender = decode_identifier(data[start:end])
		# get message
		start = end
		message_length = struct.unpack('B', data[start:start + 1])[0]
		start += 1
		end = start + message_length
		message = struct.unpack('%ds' % message_length, data[start:end])[0].decode()
		# display message
		log.notice('%s: %s' % (sender[1], message))
		# and then send ACK messages
		send_confirmation_message(current_ip, sender[0], nodes)


def handle_hello_message(udp, address, data, current_ip, nodes):
	try:
		data = data[1:]
		log.debug('RECV << HELLO %s: %s' % (address, data))
		identifier = decode_identifier(data)
		# add node to known nodes
		if identifier[0] not in nodes:
			add_to_nodes(nodes, identifier[0], identifier[1], data)
			# if node is a successor then send populate message
			if node_is_successor(current_ip, identifier[0], nodes):
				send_populate_message(udp, address, nodes)
		# update time in known nodes
		else:
			node = nodes[identifier[0]]
			node['time'] = time.time()
	except:
		pass


def handle_populate_message(address, data, current_ip, nodes):
	data = data[1:]
	log.debug('RECV << POPULATE %s: %s' % (address, data))
	number_of_nodes = struct.unpack('B', data[:1])[0]
	log.debug('%d entries in nodes' % number_of_nodes)
	data = data[1:]
	for i in range(number_of_nodes):
		# get identifier length
		length = 1 + struct.unpack('B', data[:1])[0] + 4
		# get identifier
		new_data = data[0:length]
		identifier = decode_identifier(new_data)
		if identifier[0] not in nodes:
			add_to_nodes(nodes, identifier[0], identifier[1], new_data)
		data = data[length:]


def handle_bye_message(address, data, nodes):
	data = data[1:]
	log.debug('RECV << BYE %s: %s' % (address, data))
	ip = address[0]
	# check if ip is in nodes before removing
	if ip in nodes:
		# get information and inform user
		information = nodes[ip]
		log.notice('%s lefts the chat' % information['name'])
		# delete in nodes
		nodes.pop(ip, None)


"""
THEADING
"""


def listen_to_chat_messages(tcp, current_ip, nodes):
	while True:
		client, src_address = tcp.accept()
		data = client.recv(1024)
		if data.startswith(get_packet_prefix('CHAT')):
			handle_chat_message(tcp, src_address, data, current_ip, nodes)
		elif data.startswith(get_packet_prefix('ACK')):
			log.debug('ACK')
		else:
			log.debug('RECV << UNKNOWN %s: %s' % (src_address, data))


def send_chat_messages(current_ip, nodes):
	while True:
		# get all known ip
		ips = list(nodes.keys())
		ips.remove(current_ip)
		# if i'm not the only one in the chat
		if ips:
			# get successor ip
			successor_ip = get_successor_ip(current_ip, nodes)
			# choose recipient randomly
			recipient_ip = random.choice(ips)

			recipient = nodes[recipient_ip]['identifier']
			sender = nodes[current_ip]['identifier']
			message = 'L\'empereur est de retour'
			data = struct.pack('B%ds' % len(message), len(message), message.encode())

			send_with_tcp(successor_ip, get_packet('CHAT', recipient=recipient, sender=sender, message=data))
		# retry after x seconds
		time.sleep(random.randint(3,6))


def listen_to_discovery_messages(udp, current_ip, nodes):
	while True:
		data, src_address = udp.recvfrom(1024)
		# filter own message
		if src_address[0] != current_ip:
			if data.startswith(get_packet_prefix('HELLO')):
				handle_hello_message(udp, src_address, data, current_ip, nodes)
			elif data.startswith(get_packet_prefix('POPULATE')):
				handle_populate_message(src_address, data, current_ip, nodes)
			elif data.startswith(get_packet_prefix('BYE')):
				handle_bye_message(src_address, data, nodes)
			else:
				log.debug('RECV << UNKNOWN %s: %s' % (src_address, data))


def send_hello_message(udp, identifier):
	while True:
		send_with_udp(udp, '<broadcast>', get_packet('HELLO', identifier=identifier))
		time.sleep(random.randint(3,5))


def check_if_nodes_still_logged(current_ip, nodes):
	while True:
		current_time = time.time()
		for ip, node in list(nodes.items()):
			# more of 6 seconds
			if current_time - node['time'] > 6 and ip != current_ip:
				log.notice('%s lefts the chat' % node['name'])
				nodes.pop(ip, None)
		time.sleep(0.5)


"""
PROGRAM
"""


def main():
	# create identifier
	identifier = create_identifier(name, ip)
	log.debug('identifier: %x' % identifier)
	nodes = {}
	add_to_nodes(nodes, ip, name, identifier)

	# create TCP socket server
	tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	tcp.bind(('', TCP_PORT))
	tcp.listen(MAX_CONNECTION)
	log.debug('TCP bind connect on port %d' % TCP_PORT)

	# create UDP socket server
	udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	udp.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	udp.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
	udp.bind(('', UDP_PORT))
	log.debug('UDP bind connect on port %d' % UDP_PORT)

	# multi threads
	log.notice('Welcome to the chat %s' % name)
	threading.Thread(target=listen_to_discovery_messages, args=(udp,ip,nodes,)).start()
	threading.Thread(target=send_hello_message, args=(udp, identifier,)).start()
	threading.Thread(target=check_if_nodes_still_logged, args=(ip,nodes,)).start()
	threading.Thread(target=listen_to_chat_messages, args=(tcp, ip, nodes,)).start()
	threading.Thread(target=send_chat_messages, args=(ip, nodes,)).start()


if __name__ == '__main__':
	# arguments
	if (len(sys.argv) < 3):
		log.error('program expect 2 parameters: name & IP')
		sys.exit(0)
	name = sys.argv[1]
	ip = sys.argv[2]
	main()