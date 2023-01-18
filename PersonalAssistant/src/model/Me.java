package model;


public class Me {
private String name;
private String id;
private double age;
public Me(String name,String id,double age) {
	this.name=name;
	this.age=age;
	this.id=id;
}

public String getName() {
	return name;
}
public double getAge() {
	return age;
}
public String getId() {
	return id;
}
}
