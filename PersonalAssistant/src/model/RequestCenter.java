package model;

public class RequestCenter{

	public static void main(String[] args) {
	Assistant jarvis = new Assistant("Jarvis","1",25);
    jarvis.setHW("lok", 2);
    jarvis.setHW("mok", 3);
    jarvis.setHW("khb", 2);
    System.out.println(jarvis.getSchedule());
   
	}

}
