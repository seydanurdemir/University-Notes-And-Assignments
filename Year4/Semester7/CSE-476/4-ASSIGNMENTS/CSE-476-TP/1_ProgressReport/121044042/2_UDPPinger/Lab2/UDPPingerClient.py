from socket import *
import sys
from datetime import datetime as dt
# Import time so can send a current time stamp to the server
import time

server_address = ('127.0.0.1', 12000)

count = 10

clientSocket = socket(AF_INET, SOCK_DGRAM)
clientSocket.settimeout(1)

# Ping ten times
for i in range(count):
    start = time.time()
    message = 'ping #' + str(i + 1) + ' ' + \
        str(dt.fromtimestamp(start).strftime('%H:%M:%S.%f')[:-3])
    clientSocket.sendto(message.encode(), server_address)

    try:
        data, server = clientSocket.recvfrom(1024)
        print(data.decode())
        print()

    except timeout:
        print('#' + str(i + 1) + ' REQUEST TIMED OUT')
        print()

# clientSocket.close()
