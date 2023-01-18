package model;

import java.time.LocalDate;
import java.util.*;
public class Assistant  {
	private String name;
	private String id;
	private double age;
	Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);
	public Assistant(String name,String id,double age) {
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
    public String getSchedule() {
    	String x="";
         switch(day) {
         case 2:
        x=("Monday:\r\n"+ "Data Structures - CMPS 347 (LAB)"+" 9:01 am - 12:00 pm");
        break;
         case 3:
        	
        	 x=("Tuesday: \r\n"+ "Discrete Structures II - CMPS 345(LAB)"+"	8:01 am - 11:00 am \r\n"
        	 		+ "Linear Algebra - MATH 341(LEC) "+"11:01 am - 2:00 pm \r\n"
        	 				+ "Global Warming - BIOL 002" +" 2:01 pm - 4:00 pm");
        	 break;
         case 4:
        	 x=("Wednesday:\r\n"+"Computer Organization&Archit. - CMPS 343(LAB) "+"8:01 am - 11:00 am \r\n"
        	 		+ "Discrete Structures II - CMPS 345 (LEC)"+"11:01 am - 1:00 pm \r\n"
        	 				+ "Image Processing - CMPS 327"+" 2:01 pm - 4:00 ");
        	 break;
         case 5:
        	 x=("Thursday Data Structures  CMPS 347(LEC) 8 01 am  10:00 am Image Processing  CMPS 327 LEC 10 01 am  12 00 pm Computer Organization&Archit  CMPS 343LEC 1 01 pm  3 00 pm");
        	 break;
         case 6:
        	 x=("It's the Weekend, you have nothing planned");
        	 break;
        	
         case 7:
        	x= ("It's the Weekend, you have nothing planned");
        	break;
		 case 1:
			x= ("It's the Weekend, you have nothing planned");
			break;
        
         }
		return x;
    } 
    
    Linkedlist Week[] =new Linkedlist[4];
    Linkedlist Monday = new Linkedlist ();
    Linkedlist Tuesday = new Linkedlist();
    Linkedlist Wednesday = new Linkedlist();
    Linkedlist Thursday = new Linkedlist();
    public void  ShowAllHW() {
    	 Week[0]=Monday;
    	 Week[1]=Tuesday;
    	 Week[2]=Wednesday;
    	 Week[3]=Thursday;
    	 for(int i=0;i<4;i++) {
    		 if(i==0) {
    			 System.out.println("Monday: ");
    		 }
    		 if(i==1) {
    			 System.out.println("Tuesday: ");
    		 }
    		 if(i==2) {
    			 System.out.println("Wednesday: ");
    		 }
    		 if(i==2) {
    			 System.out.println("Thursday: ");
    		 }
    		Week[i].display();
    	 }
    			 
    }
    public void ShowHW(int num) {
    	   
        switch(num) {
      
        case 2:
        	System.out.println("Monday: ");
        	Monday.display();
       	 break;
        case 3:
        	System.out.println("Tuesday: ");
        	Tuesday.display();
       	break;
        case 4:
        	System.out.println("Wednesday: ");
        	Wednesday.display();
        	break;
        case 5:
        	System.out.println("Thursday: ");
        	Thursday.display();
        	break;
     
        }
    }
    public void setHW(String value,int num) {
    	
        
        switch(num) {
      
        case 2:
        	Monday.insertAtBack(value);
       	 break;
        case 3:
        	Tuesday.insertAtBack(value);
       	break;
        case 4:
        	Wednesday.insertAtBack(value);
        	break;
        case 5:
      
        	Thursday.insertAtBack(value);
        	break;
        }
		
    }
    public String GetDate() {
    	return calendar.getTime().toString();
    }
    public String GetTime() {
    	 int hour = calendar.get(Calendar.HOUR_OF_DAY);
    	 int minute = calendar.get(Calendar.MINUTE);
    	String Hour = String.valueOf(hour);
    	String min = String.valueOf(minute);
        return Hour +"oclock and " + min +" minutes";
        		
    }




}
