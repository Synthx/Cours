import socket
import logbook

logbook.StreamHandler(sys.stdout, level=logbook.INFO).push_application()
UDP_PORT = 4456
TCP_PORT = 4457

def main():
	socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	socket.bind('', TCP_PORT)

if __name__ == '__main__':
	main()