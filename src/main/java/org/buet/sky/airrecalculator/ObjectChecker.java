package org.buet.sky.airrecalculator;

// 1-> AirPlane
// 2-> City
// 3-> Company
// 6-> Map

import java.util.List;

public class ObjectChecker {
    Command command;
    ObjectChecker(Command command) {
        this.command = command;
    }


    public Boolean isMap(){
        return command.opt.equals(6) && (command.obj instanceof List<?>);
    }

    public List<City> getMap(){
        return (List<City>) command.obj;
    }


    public Boolean isRequire(){
        return command.opt.equals(-1) && (command.obj instanceof List<?>);
    }
    public List<Integer> getRequire(){
        return (List<Integer>) command.obj;
    }


    public boolean isLogin() {
        return command.opt.equals(0) && (command.obj instanceof Company);
    }

    public boolean isSignUp() {
        return command.opt.equals(1) && (command.obj instanceof Company);
    }

    public Company getAccount() {
        return (Company) command.obj;
    }
}
