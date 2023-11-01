package JavaThreadsCreatingStartingStoppingThreads;

import java.util.Scanner;

import static java.lang.Thread.sleep;

/*
https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html
4 Ways to start a thread
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

3. Anonymous implementation of Runnable by creating a Runnable object, override the run method (Polymorphism),
create and instantiate the Thread object, wrap the Runnable object, and call its method .start()

4. Lambda implementation of Runnable syntax: Runnable runnable = () -> {};

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
            /*
            Output:
            MyRunnable is running
            MyRunnable finished
             */
        }
    }
}
class ThreadExample4
{
    public static void main(String[] args)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable running");
                System.out.println("Runnable finished");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        /*
        Output:
        Runnable running
        Runnable finished
         */
    }
}

class ThreadExample5
{
    public static void main(String[] args)
    {
        Runnable runnable = () ->
        {
            System.out.println("Runnable through lambda running");
            System.out.println("Runnable through lambda finished");
        };

        Thread thread = new Thread(runnable);
        thread.start();
        /*
        Output:
        Runnable through lambda running
        Runnable through lambda running
         */
    }
}

class ThreadExample6
{
    public static void main(String[] args)
    {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " running");
            System.out.println(threadName + " finished");
        };

        Thread thread = new Thread(runnable, "Name a thread");
        thread.start();
        /*
        Output
        Name a thread running
        Name a thread finished
         */

        Thread thread1 = new Thread(runnable, "Thread 1");
        thread1.start();
        /*
        Sample Output (Since the thread can run at the same time, there is no order
        of thread execution):
        Thread 1 running
        Name a thread running
        Name a thread finished
        Thread 1 finished
         */
    }
}

class ThreadExample7
{
    public static void main(String[] args)
    {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " running");

            // How to sleep a thread
            try
            {
                sleep(2000);
            }
            catch(InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }

            System.out.println(threadName + " finished after 2 seconds");
        };

        Thread thread = new Thread(runnable,"Thread7");
        thread.start();
        /*
        Thread7 running
        Thread7 finished after 2 seconds
         */
    }
}

class ThreadExample8
{
    /*
    Manage to stop the thread yourself rather than using the .stop() method
    of Java.

    Java: Thread.stop() - deprecated, inherently unsafe, causes unlock all
    the monitors that it has locked, which can result in system failures.
     */
    public static class StoppableRunnable implements Runnable
    {
        private boolean stopRequested = false;

        public synchronized void requestStop()
        {
            this.stopRequested = true;
        }
        // This method is available to Runnable interface
        public boolean isStopRequested() {
            return stopRequested;
        }

        private void sleep(long millis)
        {
            try
            {
                Thread.sleep(millis);
            }
            catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("StoppableRunnable running");
            while (!isStopRequested())
            {
                sleep(1000);
                System.out.println("...");
            }

            System.out.println("StoppableRunnable stopped");
        }
    }

    public static void main(String[] args)
    {
        StoppableRunnable stoppableRunnable = new StoppableRunnable();
        Thread thread = new Thread(stoppableRunnable);
        thread.start();

        try
        {
           sleep(5000); // 5 seconds
        }
        catch (InterruptedException interruptedException)
        {
            interruptedException.printStackTrace();
        }

        System.out.println("requesting stop");
        stoppableRunnable.requestStop();
        System.out.println("stop requested");

        /*
        Sample output:
        StoppableRunnable running
        ...
        ...
        ...
        ...
        requesting stop
        stop requested
        ...
        StoppableRunnable stopped
         */
    }
}

class ThreadExample9
{
    public static void main(String[] args)
    {
        Runnable runnable = () -> {
            while (true) // While loop keeps running forever
            {
                sleep(1000);
                System.out.println("Running");
            }
        };

        Thread thread = new Thread(runnable);
        thread.setDaemon(true); // Setting the Daemon true would let all its Daemon thread
        // terminates their execution
        thread.start();
        sleep(3100); // And then waited for 3 seconds
        /*
        The JVM will stay alive as long as there are any remaining threads that are running.

        dameon thread - low priority thread that runs in the background to perform tasks
        such as garbage collection or provide services to user threads.
            When all user threads finish their execution,
            the JVM automatically terminates the daemon thread.

            They are stopped in undefined state so be sure that daemon threads are resting
            in a state or in a path in the code where they are not in the middle of executing
            some important which is then only half finished because that might lead to undesirable
            side effects in the application.
            When to use?
            1.

        Instead of looping, it will only run for 3.1 seconds; thus, the output would be
        Running
        Running
        Running
         */
    }

    public static void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException interruptedException)
        {
            interruptedException.printStackTrace();
        }
    }
}

class ThreadExample10
{
    public static void main(String[] args)
    {
        Runnable runnable = () ->
        {
            for (int i = 0; i < 5; i++)
            {
                sleep(1000);
                System.out.println("Running");
            }
        };

        Thread thread = new Thread(runnable);
        thread.setDaemon(true); // The main thread will terminate immediately
        /*
        If the code is like this:

        Thread thread = new Thread(runnable);
        thread.start();
        thread.setDaemon(true);

        Output:

        Exception in thread "main" java.lang.IllegalThreadStateException
	at java.base/java.lang.Thread.setDaemon(Thread.java:2276)
	at JavaThreadsCreatingStartingStoppingThreads.ThreadExample10.main(ThreadExample.java:335)
        Running
        Running
        Running
        Running
        Running


        Cause of IllegalThreadStateException it is because the JVM calls the .run() method
         */
        thread.start();

//        try
//        {
//            thread.join();
//            /*
//            thread.join() is used to wait for the child daemon thread (since we set it to true)
//            to complete its execution before the main thread terminates.
//
//            If we remove or did not put thread.join(), the main thread will terminate immediately
//            after starting the child thread without waiting for it to complete its execution.
//             */
//        }
//        catch (InterruptedException interruptedException)
//        {
//            interruptedException.printStackTrace();
//        }


    }

    public static void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException interruptedException)
        {
            interruptedException.printStackTrace();
        }
    }
}