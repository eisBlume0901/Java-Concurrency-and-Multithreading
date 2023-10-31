package JavaThreadsCreatingStartingStoppingThreads;
/* 4 Ways to start a thread
1. Create a class and extends Thread (Abstraction), override the run method (Polymorphism),
create and instantiate the Thread object and call its method .start()
to execute the program
2. Create a class and implements Runnable (Can be abstraction), override the run method (Polymorphism),
create and instantiate the Thread object, wrap the Runnable object, and call its method .start()
3.
 */
public class ThreadExample
{
    public static void main(String[] args)
    {
        Thread thread = new Thread();
        thread.start(); // But it has no output or any execution done
    }
}

class ThreadExample2
{
    public static class MyThread extends Thread
    {
        @Override
        public void run() {
            System.out.println("MyThread running");
            System.out.println("MyThread finished");
        }
    }

    public static void main(String[] args)
    {
        MyThread myThread = new MyThread();
        myThread.start(); // Run ThreadExample2.main()
        /*
        Output:
        MyThread running
        MyThread finished
         */
    }
}

class ThreadExample3
{
    public static class MyRunnable implements Runnable
    {
        @Override
        public void run() {
            System.out.println("MyRunnable is running");
            System.out.println("MyRunnable finished");
        }

        public static void main(String[] args)
        {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }
}