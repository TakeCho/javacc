javacc -OUTPUT_DIRECTORY:generated ComplParser.jj
javac -cp .:generated *.java
java -cp .:generated ComplMain -D Program9.src Program9.vm
java VMMain Program9.vm
