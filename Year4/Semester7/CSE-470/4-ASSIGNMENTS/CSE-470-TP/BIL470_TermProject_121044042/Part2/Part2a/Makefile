all: Part2a.exe
	
Part2a.exe : main.o aes_ecb_mode.o aes_implementation.o
	gcc main.o aes_ecb_mode.o aes_implementation.o -o Part2a.exe

main.o: main.c
	gcc -c main.c

aes_ecb_mode.o: aes_ecb_mode.c
	gcc -c aes_ecb_mode.c

aes_implementation.o: aes_implementation.c
	gcc -c aes_implementation.c

clean:
	rm -rf *o *exe
