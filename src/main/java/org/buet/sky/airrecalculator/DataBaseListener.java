package org.buet.sky.airrecalculator;

public class DataBaseListener  implements Runnable{
    DataBaseListener(){
        new Thread(this).start();
    }

    public void run(){
        while(true){
            Command cmd = Server.dataBaseListener.writerPop();
            ObjectChecker objectChecker = new ObjectChecker(cmd);

            if(objectChecker.getAllCity() || objectChecker.getAllCompany() || objectChecker.getAllPlane()){
                if(Server.requireGraph.requireGraph.containsKey(cmd.opt) == false) continue;

                for(Integer client_id: Server.requireGraph.get(cmd.opt)){
                    System.out.println(client_id);
                    System.out.println(cmd.opt);

                    SharedObject obj = Server.clientObject.get(client_id);
                    obj.readerPush(new Command(cmd.opt, null));
                }
            }

            if(objectChecker.getMyPlane()){
                for(Integer client_id: Server.companyClient.get(objectChecker.getCompanyId())){
                    SharedObject obj = Server.clientObject.get(client_id);
                    obj.readerPush(new Command(cmd.opt, null));
                }
            }


            if(objectChecker.isLogOut()){
                for(Integer client_id: Server.companyClient.get(objectChecker.getAccountObj().getId())){
                    SharedObject obj = Server.clientObject.get(client_id);
                    if(obj.account.equals(objectChecker.getAccountObj()) == false){
                        System.out.println("logning out someone");
                        obj.readerPush(new Command(cmd.opt, null));
                    }
                }
            }

        }
    }
}
