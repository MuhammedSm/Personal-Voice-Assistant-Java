package model;


public class Linkedlist {
private Node first;
private String name;


public Linkedlist() {
	this("list");
}
public Linkedlist(String na) {
	name=na;
	first=null;
}
public boolean isEmpty() {
	return (first==null);
}
public void insertAtFront(String value) {
	Node n = new Node(value);
	if(first==null) {
		first=n;
		
	}else {
			n.next=first;
			first=n;
		}
	}
	

public void insertAtBack(String value) {
	Node c= new Node(value);
	if(first== null) {
		first =c;}
	else {
		Node current =first;
		while(current.next != null) {
			current = current.next;
		}
		current.next=c;
	}
}
public void display() {
	Node c=first;
	while(c!=null) {
		System.out.println(c.data);
		c=c.next;
	}
}

public void insertAtPosition(String value,int index){
	Node current=first;
	Node c= new Node(value);
	for(int i =1;i<index-1;i++) {
		current=current.next;
	}
	c.next=current.next;
	current.next=c;
	
}
public void deleteFromFront() {
	first=first.next;
}
public void deleteFromBack() {
	Node current=first;
	
	while(current.next.next!=null) {
		current=current.next;
		
	}
	current.next=null;
}
public void deleteFromIndex(int index) {
	Node current=first;
	
	for(int i =1;i<index-1;i++) {
		current=current.next;
	}
	Node del=current.next;
	current.next=current.next.next;
	del.next=null;
}

public void OccurenceNb(String value) {
	
	int count=0;
	Node c= first;
	while(c.next!=null) {
		if(value==c.data) {
			count++;
		}
		c=c.next;
	}
	System.out.println(count);
}

}


