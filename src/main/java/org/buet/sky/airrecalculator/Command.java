package org.buet.sky.airrecalculator;

import java.io.Serializable;

public class Command implements Serializable{
    public Integer opt;
    public Object obj;
    Command(Integer opt, Object obj){
        this.opt = opt;
        this.obj = obj;
    }
}
