package org.buet.sky.airrecalculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SharedObject{
    public BlockingQueue<Command> reader, writer;
    public Company account;

    SharedObject(){
        reader = new LinkedBlockingQueue<>();
        writer = new LinkedBlockingQueue<>();
    }


    public synchronized void readerPush(Command obj){
        reader.add(obj);
        notifyAll();
    }

    public synchronized Command readerPop(){
        while(reader.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return reader.poll();
    }


    public synchronized void writerPush(Command obj){
        writer.add(obj);
        notifyAll();
    }

    public synchronized Command writerPop(){
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
