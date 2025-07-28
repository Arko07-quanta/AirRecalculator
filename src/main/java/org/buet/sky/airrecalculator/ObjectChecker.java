package org.buet.sky.airrecalculator;


import java.util.List;

public class ObjectChecker {
    Command command;
    ObjectChecker(Command command) {
        this.command = command;
    }


    public Boolean addCity(){
        return command.opt.equals(7);
    }
    public Boolean getAllCity(){
        return command.opt.equals(6) ;
    }
    public Boolean isRequire(){
        return command.opt.equals(-1);
    }
    public boolean addPlane(){ return command.opt.equals(8);}
    public boolean getMyPlane(){return command.opt.equals(18);}
    public boolean getAllPlane(){ return command.opt.equals(9);}
    public boolean closeThread(){ return command.opt.equals(-100);}
    public boolean isDijkstra(){return command.opt.equals(15);}


    public boolean isLogin() {
        return command.opt.equals(0);
    }
    public boolean isSignUp() {
        return command.opt.equals(1);
    }
    public boolean getAllCompany(){ return command.opt.equals(1);}
    public boolean isSchedule(){ return  command.opt.equals(20);}



    public Company getAccountObj() {
        return (Company) command.obj;
    }
    public AirPlane getPlaneObj() {
        return (AirPlane) command.obj;
    }
    public City getCityObj(){
        return (City) command.obj;
    }
    public List<Integer> getRequireObj(){
        return (List<Integer>) command.obj;
    }

    public List<AirPlane> getScheduleObj(){
        return (List<AirPlane>) command.obj;
    }
    public List<City> getAllCityObj(){return (List<City>) command.obj;}
    public Integer getCompanyId(){
        return (Integer) command.obj;
    }
    public List<AirPlane> getAllPlaneObj(){return (List<AirPlane>) command.obj;}
    public List<City> getDijkstraObj(){return (List<City>) command.obj;}
}
