clear

echo Starting tests for TwoThreeTree.java

javac TwoThreeTree.java
java TwoThreeTree > actual.txt
diff actual.txt expected.txt