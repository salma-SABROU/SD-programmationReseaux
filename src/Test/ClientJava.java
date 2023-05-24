package Test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientJava {

    public static void main(String[] args) {


        try {
         Socket  socket = new Socket("localhost",500);
            InputStream is=socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader bfr=new BufferedReader(isr);

            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os,true);
  new Thread( new Runnable() {
      @Override
      public void run() {
          while (true){

              try {
                 String rep = bfr.readLine();
                  System.out.println(rep);
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }

          }
      }
       }).start();
            Scanner sc =new Scanner(System.in);

            while (true){
                String req=sc.nextLine();
                pw.println(req);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
