
package MMT;

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
            ServerSocket serverSocket=new ServerSocket(500);
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
            String req;
            InputStream is =socketClient.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader bfr=new BufferedReader(isr);

           OutputStream os=socketClient.getOutputStream();
           PrintWriter pw=new PrintWriter(os,true);
           String ipClient =socketClient.getRemoteSocketAddress().toString();
           System.out.println(" le client avec ipAdresse "+ipClient);
           pw.println("Bonjour saisissez votre prenom");

          req= bfr.readLine();
          pw.println("Bien venue  "+req+ " vous etes le client numero  " +numclient+" vous pouvez maintenant discuter avec d'autres ");

                while(true){
            if ((req= bfr.readLine())!=null){
                if(req.contains("=>")){
                    String[] arrOfStr=req.split("=>");
                    int id= Integer.parseInt(arrOfStr[0]);
                    String msg=arrOfStr[1];
                    diffClientById(id,msg);

                }
                else{

                    diffuserMessage(req,socketClient);
                }
            }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void diffuserMessage(String message,Socket socketCurrant) throws IOException {
        for (Conversation c:conversationList){
            if(c.socketClient!=socketCurrant){
                OutputStream os =c.socketClient.getOutputStream();
                PrintWriter pw=new PrintWriter(os,true);
                pw.println(message);

            }

        }

    }
    public void diffClientById(int idClient,String msgClient) throws IOException {
        for (Conversation c:conversationList){
            PrintWriter pw=new PrintWriter(c.socketClient.getOutputStream(),true);
            if (idClient==c.ClientId){
                pw.println(msgClient);
            }
        }
    }
}
