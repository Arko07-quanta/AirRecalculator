package org.buet.sky.airrecalculator;

import java.util.LinkedList;
import java.util.Queue;

public class SharedObject{
    public Queue<Object> reader, writer;

    SharedObject(){
        reader = new LinkedList<Object>();
        writer = new LinkedList<Object>();
    }


    public synchronized void readerPush(Object obj){
        reader.add(obj);
        notifyAll();
    }

    public synchronized Object readerPop(){
        while(reader.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return reader.poll();
    }


    public synchronized void writerPush(Object obj){
        writer.add(obj);
        notifyAll();
    }

    public synchronized Object writerPop(){
        while(writer.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return writer.poll();
    }

}
