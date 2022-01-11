from socket import *
import sys
from datetime import datetime as dt
# Import time so can send a current time stamp to the server
import time

server_address = ('127.0.0.1', 12000)

count = 10
if len(sys.argv) > 1:
    count = int(sys.argv[1])
received_count = 0
lost_count = 0

max_rtt = 0.0
min_rtt = 1.0
sum_rtt = 0.0

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
        if data:
            received_count += 1
        rtt = (time.time() - start) * 1000
        max_rtt = max(max_rtt, rtt)
        min_rtt = min(min_rtt, rtt)
        sum_rtt += rtt
        print(data.decode())
        print('Round Trip Time = ' + str(rtt)[:5] + 'ms')
        print()

    except timeout:
        lost_count += 1
        print('#' + str(i + 1) + ' REQUEST TIMED OUT')
        print()

lost_percentage = (100 * lost_count) / count
print('Packets: Sent = ' + str(count) + ', Received = ' + str(received_count) + ', Lost = ' + str(lost_count) + ' (' + str(lost_percentage)[:5] + '% loss)')
rtt_average = sum_rtt / count
print('Minimum = ' + str(min_rtt)[:5] + 'ms, Maximum = ' + str(max_rtt)[:5] + 'ms, Average = ' + str(rtt_average)[:5] + 'ms')
# clientSocket.close()
