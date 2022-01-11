from socket import *
import base64

msg = "\r\n I love computer networks!"
endmsg = "\r\n.\r\n"

# Choose a mail server (e.g. Google mail server) and call it mailserver
mailserver = ("smtp.gmail.com", 465)

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

#Send HELO command and print server response
heloCommand = 'HELO Alice\r\n'
# Encode command to UTF-8
clientSocket.send(bytes(heloCommand,'utf-8'))
# Receive the response
recv1 = clientSocket.recv(1024).decode()
print(recv1)
if recv1[:3] != '250':
    print('250 reply not received from server.')

#Send AUTH command and print server response.
print("Sending AUTH Command")
#AUTH with base64 encoded user name password
clientSocket.send("AUTH PLAIN ***\r\n".encode('utf-8'))
recv2 = clientSocket.recv(1024)
print(recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')


#Send MAIL FROM command and print server response.
print("Sending MAIL FROM Command")
clientSocket.send("MAIL From: sncakar7@gmail.com\r\n".encode('utf-8'))
recv2 = clientSocket.recv(1024)
print(recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')

#Send RCPT TO command and print server response.
print("Sending RCPT TO Command")
clientSocket.send("RCPT TO: dmrsydnr@gmail.com\r\n".encode('utf-8'))
recv2 = clientSocket.recv(1024)
print(recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')

#Send DATA command and print server response.
print("Sending DATA Command")
clientSocket.send("DATA\r\n".encode())
recv2 = clientSocket.recv(1024)
print(recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')

#Send Data and print server response.
print("Sending Data")
clientSocket.send("SUBJECT: SMTP Mail Client Test\nSMTP Mail Client Test\n.\n\r\n".encode('utf-8'))
recv2 = clientSocket.recv(1024)
print(recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')

#Send QUIT and print server response.
print("Sending QUIT")
clientSocket.send("QUIT\r\n".encode())
recv2 = clientSocket.recv(1024)
print(recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')

print("Mail Sent")