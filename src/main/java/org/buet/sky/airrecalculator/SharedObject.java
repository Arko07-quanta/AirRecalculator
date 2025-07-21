package org.buet.sky.airrecalculator;

import java.util.LinkedList;
import java.util.Queue;

public class SharedObject{
    public Queue<Integer> queue;

    SharedObject(){
        queue = new LinkedList<Integer>();
    }


    public synchronized void push(int val){
        if(val >= 100) {
            queue.add(val);
            notifyAll();
        }
    }



    public synchronized int pop(){
        while(queue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return queue.poll();
    }
}
