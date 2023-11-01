package JavaThreadsCreatingStartingStoppingThreads;
/* 4 Ways to start a thread
1. Create a class and extends Thread (Abstraction), override the run method (Polymorphism),
create and instantiate the Thread object and call its method .start()
to execute the program
2. Create a class and implements Runnable (Can be abstraction), override the run method (Polymorphism),
create and instantiate the Thread object, wrap the Runnable object, and call its method .start()
The Runnable interface is preferred over the Thread class for creating threads in Java 12.
This is because the Runnable interface provides a cleaner separation between your code and
the implementation of threads, making your code more flexible 1.

When you extend the Thread class, you are modifying the thread’s behavior, which can lead to conflicts
when you add more features inside your class 1. On the other hand, when you implement the Runnable interface,
you are simply giving the thread something to run, which means composition is the better way to go 1.

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
class ThreadExample4
{
    public static void main(String[] args)
    {

    }
}