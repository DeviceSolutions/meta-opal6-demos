SRC=src
INC=include
LIB=.
CLIENT=client

#CC=gcc
CCFLAGS=-I$(INC) -Wall -O -pthread
LDFLAGS=-L$(LIB) -lemqtt
AR=ar
GTKLIBS=`pkg-config --cflags --libs gtk+-2.0`

c: $(CLIENT)/opal6-demo

$(CLIENT)/opal6-demo: libemqtt.a opal6-demo.o
	$(CC) opal6-demo.o -o $(CLIENT)/opal6-demo $(LDFLAGS) $(GTKLIBS)

opal6-demo.o: $(CLIENT)/opal6-demo.c $(INC)/libemqtt.h
	$(CC) $(CCFLAGS) -c $(CLIENT)/opal6-demo.c -o opal6-demo.o $(GTKLIBS)

libemqtt.a: libemqtt.o
	$(AR) rcs libemqtt.a libemqtt.o

libemqtt.o: $(SRC)/libemqtt.c $(INC)/libemqtt.h
	$(CC) $(CCFLAGS) -c $(SRC)/libemqtt.c -o libemqtt.o

all: c

clean:
	rm -f *.o libemqtt.a
	rm -f $(CLIENT)/opal6-demo
