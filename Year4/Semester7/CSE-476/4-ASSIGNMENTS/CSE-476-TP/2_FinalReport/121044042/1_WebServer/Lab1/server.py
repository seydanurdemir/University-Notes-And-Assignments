# import socket module
from socket import *
# In order to terminate the program
import sys

serverSocket = socket(AF_INET, SOCK_STREAM)

# Prepare a server socket
TCP_HOST = '127.0.0.1'
TCP_PORT = 6789
BUFFER_SIZE = 4096
serverSocket.bind((TCP_HOST, TCP_PORT))
serverSocket.listen(5)

while True:
    # Establish the connection
    print('Ready to serve...')
    connectionSocket, addr =  serverSocket.accept()

    try:
        message = connectionSocket.recv(BUFFER_SIZE)
        filename = message.split()[1]
        f = open(filename[1:])
        outputdata = f.readlines()
        # Send one HTTP header line into socket
        
        connectionSocket.send("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n".encode())
        connectionSocket.send("\r\n".encode())  # Empty line

        # Send the content of the requested file to the client
        for i in range(0, len(outputdata)):
            connectionSocket.send(outputdata[i].encode())
        connectionSocket.send("\r\n".encode())

        connectionSocket.close()
        print(message)

    except IOError:
        # Send response message for file not found
        connectionSocket.send("HTTP/ 1.1 404 Not Found\r\n".encode())
        connectionSocket.send("Content-Type: text/html\r\n".encode())
        connectionSocket.send("\r\n".encode())
        connectionSocket.send("<html><head></head><body><h1>404 Not Found</h1></body></html>\r\n".encode())

        # Close client socket
        connectionSocket.close()

serverSocket.close()
# Terminate the program after sending the corresponding data
sys.exit()