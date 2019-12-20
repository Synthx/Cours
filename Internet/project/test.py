import pytest
from project import *
from exception import ParseIdentifierError
from unittest.mock import Mock, patch


def test_create_identifier():
	identifier = create_identifier('Remi', '127.0.0.1')
	assert identifier == b'\x05Remi\x00\x7f\x00\x00\x01'
	identifier = create_identifier('', '127.0.0.1')
	assert identifier == b'\x01\x00\x7f\x00\x00\x01'
	identifier = create_identifier('Jean-Pierre', '127.0.0.1')
	assert identifier == b'\x0cJean-Pierre\x00\x7f\x00\x00\x01'


def test_create_identifier_raise_value_error():
	with pytest.raises(ValueError):
		create_identifier('Chaine_de_caractere_trop_longue', '127.0.0.1')


def test_create_identifier_raise_os_error():
	with pytest.raises(OSError):
		create_identifier('Remi', '127.0.0.1.0')


def test_decode_identifier():
	identifier = decode_identifier(b'\x05Remi\x00\x7f\x00\x00\x01')
	assert identifier == ('127.0.0.1', 'Remi')
	identifier = decode_identifier(b'\x01\x00\x7f\x00\x00\x01')
	assert identifier == ('127.0.0.1', '')
	identifier = decode_identifier(b'\x0cJean-Pierre\x00\x7f\x00\x00\x01')
	assert identifier == ('127.0.0.1', 'Jean-Pierre')


def test_decode_identifier_raise_parse_error():
	with pytest.raises(ParseIdentifierError):
		decode_identifier(b'\x05Remi\x00\x7f\x00\x00')


def test_add_to_nodes():
	nodes = {}
	assert len(nodes) == 0
	add_to_nodes(nodes, '127.0.0.1', 'Remi', b'')
	assert len(nodes) == 1
	assert list(nodes.keys()).index('127.0.0.1') == 0
	add_to_nodes(nodes, '127.0.0.2', 'Jean-Pierre', b'')
	assert len(nodes) == 2
	assert list(nodes.keys()).index('127.0.0.1') == 0
	assert list(nodes.keys()).index('127.0.0.2') == 1
	add_to_nodes(nodes, '126.0.0.1', 'Jean', b'')
	assert len(nodes) == 3
	assert list(nodes.keys()).index('126.0.0.1') == 0
	assert list(nodes.keys()).index('127.0.0.1') == 1
	assert list(nodes.keys()).index('127.0.0.2') == 2


def test_node_is_successor():
	nodes = {}
	add_to_nodes(nodes, '127.0.0.1', 'Remi', b'')
	add_to_nodes(nodes, '127.0.1.1', 'Jean-Pierre', b'')
	add_to_nodes(nodes, '126.0.0.1', 'Jean', b'')
	add_to_nodes(nodes, '127.0.0.3', 'Alexandre', b'')
	assert node_is_successor('127.0.0.1', '127.0.0.3', nodes) == True
	assert node_is_successor('127.0.0.1', '126.0.0.1', nodes) == False
	assert node_is_successor('127.0.0.1', '127.0.1.1', nodes) == False
	assert node_is_successor('127.0.1.1', '126.0.0.1', nodes) == True


def test_send_with_udp():
	identifier = create_identifier('Remi', '127.0.0.1')
	socket = Mock()
	send_with_udp(socket, '127.0.0.1', identifier)
	socket.sendto.assert_called_once_with(identifier, ('127.0.0.1', 4456))


def test_send_populate_message():
	nodes = {}
	add_to_nodes(nodes, '127.0.0.1', 'Remi', create_identifier('Remi', '127.0.0.1'))
	add_to_nodes(nodes, '126.0.0.1', 'Jean', create_identifier('Jean', '126.0.0.1'))
	socket = Mock()
	send_populate_message(socket, ('127.0.0.1', 'Remi'), nodes)
	socket.sendto.assert_called_once_with(b'\x02\x02' + nodes['126.0.0.1']['identifier'] + nodes['127.0.0.1']['identifier'], ('127.0.0.1', 4456))


def test_handle_bye_message():
	nodes = {}
	add_to_nodes(nodes, '127.0.0.1', 'Remi', create_identifier('Remi', '127.0.0.1'))
	add_to_nodes(nodes, '126.0.0.1', 'Jean', create_identifier('Jean', '126.0.0.1'))
	handle_bye_message(('126.0.0.0', UDP_PORT), get_packet('BYE'), nodes)
	assert len(nodes) == 2
	handle_bye_message(('126.0.0.1', UDP_PORT), get_packet('BYE'), nodes)
	assert len(nodes) == 1


def test_handle_populate_message():
	identifiers = b''
	nodes = {}
	add_to_nodes(nodes, '127.0.0.1', 'Remi', create_identifier('Remi', '127.0.0.1'))
	handle_populate_message(('126.0.0.1', UDP_PORT), get_packet('POPULATE', size=b'\x00', identifiers=identifiers), '127.0.0.1', nodes)
	assert len(nodes) == 1
	identifiers += create_identifier('Jean', '126.0.0.1') + create_identifier('Alexandre', '126.0.1.1')
	handle_populate_message(('126.0.0.1', UDP_PORT), get_packet('POPULATE', size=b'\x02', identifiers=identifiers), '127.0.0.1', nodes)
	assert len(nodes) == 3


def test_handle_hello_message():
	socket = Mock()
	nodes = {}
	add_to_nodes(nodes, '127.0.0.1', 'Remi', create_identifier('Remi', '127.0.0.1'))
	add_to_nodes(nodes, '126.0.1.1', 'Alexandre', create_identifier('Alexandre', '126.0.1.1'))
	identifier = create_identifier('Jean', '126.0.0.1')
	handle_hello_message(socket, ('126.0.0.1', UDP_PORT), get_packet('HELLO', identifier=identifier), '127.0.0.1', nodes)
	assert len(nodes) == 3
	handle_hello_message(socket, ('126.0.0.1', UDP_PORT), get_packet('HELLO', identifier=identifier), '127.0.0.1', nodes)
	assert len(nodes) == 3
	identifiers = identifier + nodes['126.0.1.1']['identifier'] + nodes['127.0.0.1']['identifier']
	packet = get_packet('POPULATE', size=b'\x03', identifiers=identifiers)
	socket.sendto.assert_called_once_with(packet, ('126.0.0.1', UDP_PORT))

