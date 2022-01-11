import socket
import sys

HOST = sys.argv[1]
PORT = int(sys.argv[2])
FileName = sys.argv[3]

BUFFER_SIZE = 1024

ADDRESS = (HOST, PORT)

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.connect(ADDRESS)
# F-String after Pythnon3.6
server.send(f"GET /{FileName}".encode())

data = b''
while True:
    buf = server.recv(BUFFER_SIZE)
    if not buf:
        break
    data += buf

server.close()
print(data.decode())