SQL=./mysql-connector-java-8.0.18.jar

all: compile run
compile:
	javac -cp $(SQL):output:. Main.java
run:
	java -cp $(SQL):output:. Main
clean:
	rm *.class

