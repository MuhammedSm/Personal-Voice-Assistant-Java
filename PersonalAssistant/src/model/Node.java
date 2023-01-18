package model;


public class Node {
String data;
Node next;
public Node(String a) {
	data = a;
	next=null;
}
public Node(String a, Node q) {
	data=a;
	next=q;
	
}
public Node() {
	data="";
	next=null;
}
}
