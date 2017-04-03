JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

clean :
	rm -f *.class
	rm -f encoded.bin decoded.txt code_table.txt

%.class : %.java
	$(JAVAC) $<
