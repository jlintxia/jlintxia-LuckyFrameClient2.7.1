package luckyclient.caserun.exappium;

import java.util.Random;

public class RandomTest {
	
    public  String bootstrap() {
        int max=60000;
        int min=10;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        String s1=s+"";
        System.out.println(s1);
        return s1;
    }
    
     
    public  int port() {
        int max=60000;
        int min=10;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s+"");
        return s;
    }
    public  String shuzi() {
        int max=60000;
        int min=10;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        String s1=s+"";
        System.out.println(s1);
        return s1;
    }
    public  String zimu() {
    	String chars = "abcdefghijklmnopqrstuvwxyz";
        String s1=chars.charAt((int)(Math.random() * 26))+"";
        System.out.println(s1);
        return s1;
    }
   
    
}