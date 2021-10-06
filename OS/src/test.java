public class test extends Thread {
 
    public void run() {
        System.out.println("My name is: " + getName());
    }
 String[]storage;
    public static void main(String[] args) {
        test t1 = new test();
        t1.start();
 
        System.out.println("My name is: " + Thread.currentThread().getName());
           }
 
}