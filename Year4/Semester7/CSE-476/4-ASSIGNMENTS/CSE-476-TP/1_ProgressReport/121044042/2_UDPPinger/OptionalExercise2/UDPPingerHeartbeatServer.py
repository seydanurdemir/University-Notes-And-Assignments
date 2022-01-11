# UDPPingerHeartbeatServer.py
from socket import *

from datetime import datetime as dt
# import time so we can calculate the time difference
import time

# Create a UDP socket
# Notice the use of SOCK_DGRAM for UDP packets
serverSocket = socket(AF_INET, SOCK_DGRAM)
# Assign IP address and port number to socket
serverSocket.bind(('', 12000))

# Set up heartbeat
serverSocket.settimeout(2)

count = 0
packets_lost = 0
listen_for_heartbeat = True

while listen_for_heartbeat:        
	count += 1
	try:
		# Receive the client packet along with the address it is coming from
		message, address = serverSocket.recvfrom(1024)
		# Calculate the time difference
		timeDifference = abs((float(time.time()) - float(message)) * 1000)
		# Print this difference
		print("Time difference = " + str(round(timeDifference, 3)) + "ms")
		# the server responds
		data = 'ping #' + str(count) + ' ' + str(dt.fromtimestamp(float(message)).strftime('%H:%M:%S.%f')[:-3])
		serverSocket.sendto(data.upper().encode(), address)
		packets_lost = 0

	except timeout:
		# If the server does not receive a message for two seconds
		print("REQUEST TIMED OUT - PACKET LOST")
		packets_lost += 1

	if packets_lost > 10:
		# Once the packet loss reaches 10, assume that the client has stopped
		listen_for_heartbeat = False

# Assumed client has stopped
print("Client Application has stopped")
serverSocket.close()
