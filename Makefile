src=src/centipede/*.java
src+=src/centipede/graphics/*.java
src+=src/centipede/Input/*.java
src+=src/centipede/objects/*.java
src+=src/centipede/gamecore/*.java

pckg=centipede.jar
main=centipede/Game


all:
	mkdir -p bin
	javac -Xlint:unchecked ${src} -d bin/


doc:
	javadoc ${src} -d doc/


centipede: all
	java -cp bin/ ${main}

clean:
	rm -rf bin
	rm -rf doc
	rm -f ${pckg}