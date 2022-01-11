# UDPPingerServer.py
# We will need the following module to generate randomized lost packets
import random
from socket import *

# Create a UDP socket
# Notice the use of SOCK_DGRAM for UDP packets
serverSocket = socket(AF_INET, SOCK_DGRAM)

# Assign IP address and port number to socket
serverSocket.bind(('', 12000))

while True:
	# Generate random number in the range of 0 to 10
	print('Waiting for Client!')
	rand = random.randint(0, 10)
	# Receive the client packet along with the address it is coming from
	message, address = serverSocket.recvfrom(1024)
	print(message)
	# Capitalize the message from the client
	# If rand is less is than 4, we consider the packet lost and do not respond
	data = message.upper()
	if rand < 4:
		continue

	# Otherwise, the server responds
	serverSocket.sendto(data, address)