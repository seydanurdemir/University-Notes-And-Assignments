from socket import *
from time import ctime
from threading import Thread
# In order to terminate the program
import sys

class ClientHandler(Thread):
    "Handles a client request."

    def __init__(self, client):
        Thread.__init__(self)
        self._client = client

    def run(self):
        try:
            message = client.recv(BUFFER_SIZE)
            filename = message.split()[1]
            f = open(filename[1:])
            outputdata = f.readlines()
            
            # Send one HTTP header line into socket
            self._client.send(
                "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n".encode())
            # Empty line
            self._client.send("\r\n".encode())

            # Send the content of the requested file to the client
            for i in range(0, len(outputdata)):
                self._client.send(outputdata[i].encode())
            self._client.send("\r\n".encode())

            self._client.close()
        except IOError:
            # Send response message for file not found
            self._client.send("HTTP/ 1.1 404 Not Found\r\n".encode())
            self._client.send("Content-Type: text/html\r\n".encode())
            self._client.send("\r\n".encode())
            self._client.send(
                "<html><head></head><body><h1>404 Not Found</h1></body></html>\r\n".encode())

            # Close client socket
            self._client.close()

HOST = sys.argv[1]
PORT = int(sys.argv[2])
BUFFER_SIZE = 1024
ADDRESS = (HOST, PORT)

server = socket(AF_INET, SOCK_STREAM)
server.bind(ADDRESS)
server.listen(5)

# The server now just waits for connections from clients
# and hands sockets off to client handlers
while True:
    # Establish the connection
    print('Ready to serve...')
    client, address = server.accept()
    handler = ClientHandler(client)
    handler.start()

server.close()
# Terminate the program after sending the corresponding data
sys.exit()
