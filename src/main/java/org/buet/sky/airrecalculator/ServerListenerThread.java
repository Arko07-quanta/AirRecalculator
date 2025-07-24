package org.buet.sky.airrecalculator;

import java.util.*;

public class ServerListenerThread implements Runnable {
    private SharedObject obj;

    private List<Integer> requireList;
    private Company company;
    private Integer clientId;

    public ServerListenerThread(SharedObject obj, Integer clientId) {
        this.obj = obj;
        this.clientId = clientId;
        company = new Company();
        requireList = new ArrayList<>();
    }

    public void addEdge(int u, int v){
        if(Server.requireGraph.get(u) == null) Server.requireGraph.put(u, new HashSet<>());
        Server.requireGraph.get(u).add(v);
    }

    public void removeEdge(int u, int v){
        if(Server.requireGraph.get(u) != null){
            Server.requireGraph.get(u).remove(v);
            if(Server.requireGraph.get(u).isEmpty()) Server.requireGraph.remove(u);
        }
    }




    public void writeMap(){
        obj.writerPush(new Command(6, DataBase.getCity()));
    }






    public void run() {

        while(true) {
            ObjectChecker objectChecker = new ObjectChecker(obj.readerPop());

            if(objectChecker.isRequire()) {
                for (Integer opt : requireList) {
                    removeEdge(opt, clientId);
                }

                requireList = objectChecker.getRequire();
                for (Integer opt : requireList) {
                    addEdge(opt, clientId);
                }
            }

            if(objectChecker.isMap()){
                writeMap();
            }


            if(objectChecker.isLogin()){
                Company company = objectChecker.getAccount();
                if(DataBase.verify(company)){
                    obj.account = company;
                    obj.writerPush(new Command(0, company));
                }else{
                    obj.writerPush(new Command(0, null));
                }
            }

            if(objectChecker.isSignUp()){
                Company company = objectChecker.getAccount();
                if(Company.validate(company) && DataBase.validate(company)){
                    DataBase.addCompany(company);
                    obj.account = company;
                    obj.writerPush(new Command(0, company));
                }else{
                    obj.writerPush(new Command(0, null));
                }
            }








        }


    }

}
