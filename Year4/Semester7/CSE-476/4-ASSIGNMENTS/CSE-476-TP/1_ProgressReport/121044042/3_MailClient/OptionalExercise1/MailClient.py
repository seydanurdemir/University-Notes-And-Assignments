from socket import *
import base64
import ssl

msg = "\r\n I love computer networks!"
endmsg = "\r\n.\r\n"

# Choose a mail server (e.g. Google mail server) and call it mailserver
mailserver = ("smtp.gmail.com", 587)

# Create socket called clientSocket and establish a TCP connection with mailserver
# Create the client socket
clientSocket = socket(AF_INET, SOCK_STREAM)
# Connect to the socket
clientSocket.connect(mailserver)
# Receive the connection response
recv = clientSocket.recv(1024).decode()
print("Message after connection request: " + recv)
if recv[:3] != '220':
    print('220 reply not received from server.')

# Send first HELO command
firstHelo = 'HELO Alice\r\n'
# Encode command to UTF-8
clientSocket.send(bytes(firstHelo,'utf-8'))
# Receive the response
recv1 = clientSocket.recv(1024).decode()
print("Message after HELO command: " + recv1)
if recv1[:3] != '250':
    print('250 reply not received from server.')

# Gmail requires TLS security
tlsCommand = "STARTTLS\r\n"
# Encode command to UTF-8
clientSocket.send(bytes(tlsCommand, 'utf-8'))
# Receive the response
recvTLS = clientSocket.recv(1024).decode()
print(recvTLS)

# Wrap the socket with SSL - as required by Gmail
clientSocketSSL = ssl.wrap_socket(clientSocket)
# Send authentication command
command = "AUTH LOGIN\r\n"
# Encode command to UTF-8
clientSocketSSL.send(bytes(command,'utf-8'))
# Receive the response
recvSSL = clientSocketSSL.recv(1024).decode()

# Ask user to input email for authentication
username = input('Enter your username: ')
# Encode to UTF-8
username = username.encode('utf-8')
# Gmail requires both UTF-8 and base64 encoding - send with both encodings
clientSocketSSL.send(base64.b64encode(username) + '\r\n'.encode('utf-8'))
# Receive the response
recvAuth = clientSocketSSL.recv(1024).decode()

# Ask user to input password for authentication
password = input ('Enter your password: ')
# Encode to UTF-8
password = password.encode('utf-8')
# Gmail requires both UTF-8 and base64 encoding - send with both encodings
clientSocketSSL.send(base64.b64encode(password) + '\r\n'.encode('utf-8'))
# Receive the response
recvAuth = clientSocketSSL.recv(1024).decode()

# Send second HELO command
secondHelo = 'HELO Alice\r\n'
# Encode to UTF-8
clientSocketSSL.send(secondHelo.encode())
# Receive the response
recv1 = clientSocketSSL.recv(1024).decode()
print("Message after HELO command: " + recv1)
if recv1[:3] != '250':
	print('250 reply not received from server.')

# Send RCPT TO command and print server response.
# Ask user for destination input
destinationMail = input("Enter the recipient: ")
mailFrom = "MAIL FROM: <" + username.decode() + ">\r\n"
clientSocketSSL.send(mailFrom.encode())
recv2 = clientSocketSSL.recv(1024)
recv2 = recv2.decode()
print ("MAIL FROM command response: " + recv2)
rcptTo = "RCPT TO:<" + destinationMail + ">\r\n"

# Send DATA command and print server response.
clientSocketSSL.send(rcptTo.encode())
recv3 = clientSocketSSL.recv(1024)
recv3 = recv3.decode()
print ("RCPT TO command response: " + recv3)

# Send message data.
data = "DATA\r\n"
clientSocketSSL.send(data.encode())
recv4 = clientSocketSSL.recv(1024)
recv4 = recv4.decode()
print ("DATA command response: " + recv4)

# Message ends with a single period.
subject = "Subject: CSE 476 - Lab 3\r\n\r\n"
clientSocketSSL.send(subject.encode())
clientSocketSSL.send(msg.encode())
clientSocketSSL.send(endmsg.encode())
recv_msg = clientSocketSSL.recv(1024)
print("Response after receiving message: " + recv_msg.decode())

# Send QUIT command and get server response.
quit = "QUIT\r\n"
clientSocketSSL.send(quit.encode())
recv5 = clientSocketSSL.recv(1024)
print(recv5.decode())
clientSocketSSL.close()