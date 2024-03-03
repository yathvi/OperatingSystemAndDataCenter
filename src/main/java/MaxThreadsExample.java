
import java.util.HashMap;
import java.util.Map;

public class MaxThreadsExample {
    static int count =0;
    static int loop=0;
    static Map<String,Integer> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
        Thread.sleep(5000);
        System.out.println();
        System.out.println("Thread Count");
        map.forEach((k,v) -> {
            System.out.println("Thread " + k+" -> "+v);
        });
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            map.put(String.valueOf(Thread.currentThread().getId()),0);


            while(true)
            {
                loop++;count++;
                int i = map.get(String.valueOf(Thread.currentThread().getId()));
                map.put(String.valueOf(Thread.currentThread().getId()),++i);
                System.out.println(System.currentTimeMillis() + " -> Thread " + Thread.currentThread().getId() + " is running. -> count = " + count);
                if(loop>1000000000)
                {
                    break;
                }
            }
        }
    }
}
