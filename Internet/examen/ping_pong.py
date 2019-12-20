import socket
import logbook
import sys
import struct
import random


# logger
logbook.StreamHandler(sys.stdout, level=logbook.INFO).push_application()
log = logbook.Logger('APP')
# global variables
IP = '127.0.0.1'
N = 50


def get_random_port(ports, current_port):
	port = random.choice(ports)
	while port == current_port:
		port = random.choice(ports)
	return port


def get_port_of_socket(udp):
	info = udp.getsockname()
	return info[1]


def create_socket(sockets):
	# create udp socket
	udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	udp.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	udp.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
	# listen on random available port
	udp.bind((IP, 0))

	# get socket port
	port = get_port_of_socket(udp)
	# add to dictionary
	sockets[port] = udp


def listen_to(udp):
	data, src_address = udp.recvfrom(1024)
	log.debug('(%d) RECV << %d: %s' % (get_port_of_socket(udp), src_address[1], data))
	return data
 

def send_to(udp, port, packet):
	address = (IP, port)
	udp.sendto(packet, address)
	log.debug('(%d) SEND >> %d: %s' % (get_port_of_socket(udp), port, packet))


def handle_ping_pong(sockets, ports, current_port):
	# get new socket & listen
	udp = sockets[current_port]
	data = listen_to(udp)
	# add current port to packet
	data += struct.pack('H', current_port)
	# get new random port
	next_port = get_random_port(ports, current_port)
	# send new packet to this port
	send_to(udp, next_port, data)
	# return packet length
	return len(data), next_port


def main():
	packet_length = 0
	trace = []
	# create & fill socket dictionary
	sockets = {}
	for i in range(0, N):
		create_socket(sockets)
	log.info('created %d sockets' % len(sockets))
	# get all socket ports
	ports = list(sockets.keys())
	# get first socket
	first_port = ports[0]
	udp = sockets[first_port]
	log.info('socket with port %d selected' % first_port)
	trace.append(first_port)
	# create packet
	data = struct.pack('H', first_port)
	# send to random port
	next_port = get_random_port(ports, first_port)
	send_to(udp, next_port, data)
	# program will stop when packet length > 1000
	while packet_length < 1000:
		trace.append(next_port)
		packet_length, next_port = handle_ping_pong(sockets, ports, next_port)
	# display ports trace
	log.info('ping pong was done with the following %d ports :' % len(trace))
	log.info(trace)


if __name__ == '__main__':
	main()