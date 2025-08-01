package org.buet.sky.airrecalculator;

import java.util.*;

public class ServerListenerThread implements Runnable {
    private SharedObject obj;

    private List<Integer> requireList;
    private Integer clientId;

    public ServerListenerThread(SharedObject obj, Integer clientId) {
        this.obj = obj;
        this.clientId = clientId;
        requireList = new ArrayList<>();
        new Thread(this).start();
    }


    public void writeMap(){
        obj.writerPush(new Command(6, DataBase.getCity()));
    }

    public void run() {
        while(true) {
            ObjectChecker objectChecker = new ObjectChecker(obj.readerPop());

            if(objectChecker.closeThread()){
                for(Integer u : requireList){
                    Server.requireGraph.removeEdge(clientId, u);
                }

                if(obj.account != null)
                    Server.companyClient.removeEdge(clientId, obj.account.getId());
                System.out.println("Client " + clientId + " closed");
                break;
            }

            if(obj.account != null && objectChecker.isSignUp()){
                obj.account = objectChecker.getAccountObj();
                DataBase.modifyCompany(objectChecker.getAccountObj());
            }


            if(objectChecker.isLogOut()){
                if(obj.account != null)
                    Server.companyClient.removeEdge(clientId, obj.account.getId());
                obj.account = null;
                obj.writerPush(new Command(50, null));
            }

            if(objectChecker.isRequire()) {
                for (Integer opt : requireList) {
                    Server.requireGraph.removeEdge(opt, clientId);
                }

                requireList = objectChecker.getRequireObj();
                System.out.println(requireList);
                for (Integer opt : requireList) {
                    Server.requireGraph.addEdge(opt, clientId);
                    obj.readerPush(new Command(opt, null));
                }
            }

            if(objectChecker.getAllCity()){
                writeMap();
            }



            if(objectChecker.isLogin()){
                Company company = objectChecker.getAccountObj();
                if(DataBase.verify(company)){
                    Server.companyClient.addEdge(clientId, company.getId());
                    company = DataBase.getCompanyById(company.getId());
                    System.out.println(company);
                    obj.account = company;
                    obj.writerPush(new Command(0, company));
                }else{
                    obj.writerPush(new Command(0, null));
                }
            }


            if(objectChecker.isSignUp()){
                System.out.println("Client " + clientId + " sign up");
                Company company = objectChecker.getAccountObj();
                if(DataBase.validate(company)){






                    DataBase.addCompany(company);
                    obj.account = company;
                    Server.companyClient.addEdge(clientId, company.getId());

                    obj.writerPush(new Command(0, company));
                }else{
                    obj.writerPush(new Command(0, null));
                }
            }


            if(objectChecker.addCity()){
                if(obj.account.getName().equals("admin"))
                    DataBase.addCity(objectChecker.getCityObj());
            }



            if(objectChecker.addPlane()){
                if(obj.account != null){
                    AirPlane plane = objectChecker.getPlaneObj();
                    plane.setCompanyId(obj.account.getId());
                    DataBase.addAirPlane(plane);
                }else{
                    AirPlane newPlane = objectChecker.getPlaneObj();
                    AirPlane prevPlane = DataBase.getAirplaneById(newPlane.getId());

                    if(prevPlane.getTicket() != newPlane.getTicket()){
                        prevPlane.setTicket(newPlane.getTicket());
                        DataBase.modifyTicket(prevPlane);
                    }
                }
            }



            if(objectChecker.getMyPlane()){
                if(obj.account != null){
                    obj.writerPush(new Command(18, DataBase.getAirplane(obj.account.getId())));
                }
            }

            if(objectChecker.getAllPlane()){
                obj.writerPush(new Command(9, DataBase.getAirplane()));
            }


        }
    }

}
