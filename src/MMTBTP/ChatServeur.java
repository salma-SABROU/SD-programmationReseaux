package MMTBTP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class ChatServeur extends  Thread{
    private  boolean isActive=true;
    private int numclient ;
    List<Conversation> conversationList=new ArrayList<>();
    public static void main(String[] args) {

        new ChatServeur().start();
    }

    @Override
    public void run(){
        try {
            // telnet localhost 500
            ServerSocket serverSocket=new ServerSocket(7777);
            System.out.println("le serveur est demarer");
            while(isActive){

                Socket socket=serverSocket.accept();
                ++numclient;
                Conversation conversation =new Conversation(numclient,socket);
                conversationList.add(conversation);
                conversation.start();


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class Conversation extends Thread{
        private int ClientId;
        private Socket socketClient;

        public Conversation(int clientId, Socket socketClient) {
            ClientId = clientId;
            this.socketClient = socketClient;
        }

        @Override
        public void run(){
            try {
                String reqs;
                InputStream is =socketClient.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader bfr=new BufferedReader(isr);

                OutputStream os=socketClient.getOutputStream();
                PrintWriter pw=new PrintWriter(os,true);

                pw.println("Bonjour saisir votre prenom");

                reqs= bfr.readLine();
                pw.println("Bien venue  "+reqs+ " vous etes le client numero  " +numclient+" vous pouvez faire un chat les autres ");


                String ipClient =socketClient.getRemoteSocketAddress().toString();
                System.out.println(" le client avec ipAdresse "+ipClient);



                while(true){
                    String req;
                    req= bfr.readLine();

                    if(req.equals("quit")){
                        inform(socketClient,reqs);
                        conversationList.remove(this);
                        socketClient.close();
                        break;
                    } else if(req.contains("=>")){
                        String[] arrOfStr=req.split("=>");
                        int id= Integer.parseInt(arrOfStr[0]);
                        String msg=arrOfStr[1];
                        diffClientById(reqs,id,msg);
                    }
                    else{


                        diffuserMessage(req,socketClient,reqs);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void diffuserMessage(String message,Socket socketCurrant,String reqs) throws IOException {
        for (Conversation c:conversationList){
            if(c.socketClient!=socketCurrant){
                OutputStream os =c.socketClient.getOutputStream();
                PrintWriter pw=new PrintWriter(os,true);
                pw.println(reqs+" :"+message);

            }

        }

    }
    public  void inform(Socket socketCurrant,String reqs) throws IOException {
        for (Conversation c:conversationList){
            if(c.socketClient!=socketCurrant){
                OutputStream os =c.socketClient.getOutputStream();
                PrintWriter pw=new PrintWriter(os,true);
                pw.println(reqs+": a quitté la conversation");

            }

        }



    }
    public void diffClientById(String reqs,int idClient,String msgClient) throws IOException {
        for (Conversation c:conversationList){
            PrintWriter pw=new PrintWriter(c.socketClient.getOutputStream(),true);
            if (idClient==c.ClientId){
                pw.println(reqs+" :"+msgClient);
            }
        }
    }
}