javacc -OUTPUT_DIRECTORY:generated ComplParser.jj
javac -cp .:generated *.java
java -cp .:generated ComplMain -D test.src Program9.vm
java VMMain Program9.vm
