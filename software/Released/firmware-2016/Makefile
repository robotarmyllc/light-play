# makefile, written by guido socher
# Select your target chip:
#MCU=atmega8
#DUDECPUTYPE=m8
# this specific circuit works also for atmega88 and family
# in general that is however not the case.
MCU=atmega328p
DUDECPUTYPE=m328p
#MCU=atmega88
#DUDECPUTYPE=m88
#MCU=atmega328p
#DUDECPUTYPE=m328
#
#PROGRAMER=stk500v2
#PROGRAMER=usbtiny
PROGRAMER=avrisp2
LOADCMD=avrdude
LOADARG=-p $(DUDECPUTYPE) -c $(PROGRAMER) -e -U flash:w:
#
CC=avr-gcc
OBJCOPY=avr-objcopy
# optimize for size:
CFLAGS=-g -mmcu=$(MCU) -Wall -Wstrict-prototypes -Os -mcall-prologues
#-------------------
all: BrainBoard.hex
#-------------------
help: 
	@echo "Usage: make all|load|load_pre|rdfuses"
	@echo "The Makefile has also fuse settings for atmega8 in the code."
	@echo "Do not use those make targets for any other chip than atmega8."
	@echo "The atmega8 fuse targest are: wrfuse1mhz|wrfuse4mhz|wrfuse8mhz|wrfusecrystal"
	@echo "Warning: you will not be able to undo wrfusecrystal unless you connect an"
	@echo "         external crystal! The uC is dead after wrfusecrystal if you do"
	@echo "         not have an external crystal."
#-------------------
BrainBoard.hex : BrainBoard.out 
	avr-size BrainBoard.out
	$(OBJCOPY) -R .eeprom -O ihex BrainBoard.out BrainBoard.hex 
BrainBoard.out : BrainBoard.o 
	$(CC) $(CFLAGS) -o BrainBoard.out -Wl,-Map,BrainBoard.map BrainBoard.o -lprintf_flt -lm
BrainBoard.o : BrainBoard.c 
	$(CC) $(CFLAGS) -Os -c BrainBoard.c
#------------------
pre: BrainBoard.hex
	cp BrainBoard.hex BrainBoard_pre.hex
#------------------
load: BrainBoard.hex
	$(LOADCMD) -P usb $(LOADARG)BrainBoard.hex
# here is a pre-compiled version in case you have trouble with
# your development environment
load_pre: BrainBoard_pre.hex
	$(LOADCMD) -P usb $(LOADARG)BrainBoard_pre.hex
#
#-------------------
# fuse byte settings:
#  Atmel AVR ATmega8 
#  Fuse Low Byte      = 0xe1 (1MHz internal), 0xe3 (4MHz internal), 0xe4 (8MHz internal)
#                       0xee external crystal
#  Fuse High Byte     = 0xd9 
#  Factory default is 0xe1 for low byte and 0xd9 for high byte
# Check this with make rdfuses
#

#### TODO:
####   Need to tune these for atmega328p chip.
rdfuses:
	avrdude -P usb -p $(DUDECPUTYPE) -c $(PROGRAMER) -v -q
# use atmega8 internal RC oscillator 1 Mhz
wrfuse1mhz:
	avrdude -P usb -p  $(DUDECPUTYPE) -c $(PROGRAMER) -u -v -U lfuse:w:0x62:m
# use internal RC oscillator 4 Mhz
wrfuse4mhz:
	avrdude -P usb -p  $(DUDECPUTYPE) -c $(PROGRAMER) -u -v -U lfuse:w:0x62:m
wrfuse8mhz:
	avrdude -P usb -p  $(DUDECPUTYPE) -c $(PROGRAMER) -u -v -U lfuse:w:0xe2:m
# use external 3-8 Mhz crystal
# Warning: you can not reset this to intenal unless you connect a crystal!!
wrfusecrystal:
	@echo "Warning: The external crystal setting can not be changed back without a working crystal"
	@echo "         You have 3 seconds to abort this with crtl-c"
	@sleep 3
	avrdude -P usb -p  $(DUDECPUTYPE) -c $(PROGRAMER) -u -v -U lfuse:w:0xe6:m
#-------------------
erase:
	avrdude -P usb -p $(DUDECPUTYPE) -c $(PROGRAMER) -v -e
#-------------------
clean:
	rm -f *.o *.map *.out BrainBoard.hex
#-------------------
