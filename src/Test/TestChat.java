package Test;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class TestChat extends Application {
    PrintWriter pw;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("App Chat");
        BorderPane borderPane=new BorderPane();
        Scene scene=new Scene(borderPane,500,400);

        Label labelHost=new Label("adress :");
        TextField textFieldhost=new TextField("localhost");
        Label labelPort=new Label("port :");
        TextField textFielPort=new TextField("8080");
        Button buttonConnecter=new Button("connecter");
        HBox hBox=new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));

        hBox.getChildren().addAll(labelHost,textFieldhost,labelPort,textFielPort,buttonConnecter);
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));

        Label labelReq=new Label("message");
        TextField textFieldMessage=new TextField(); textFieldMessage.setPrefSize(400,30);
        Button buttonEnvoyer=new Button("envoyer");
        HBox hBox1=new HBox();
        hBox1.getChildren().addAll(labelReq,textFieldMessage,buttonEnvoyer);
        borderPane.setBottom(hBox1);

        VBox vBox=new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        ObservableList<String>observableList=FXCollections.observableArrayList();
        ListView<String>listView=new ListView<>(observableList);
        vBox.getChildren().add(listView);

        borderPane.setCenter(vBox);
        borderPane.setTop(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();


 buttonConnecter.setOnAction((event)->{
     String host=textFieldhost.getText();
     int port =Integer.parseInt(textFielPort.getText());

     try {
         Socket socket=new Socket(host,port);
         InputStream is =socket.getInputStream();
         InputStreamReader isr=new InputStreamReader(is);
         BufferedReader bfr=new BufferedReader(isr);

         OutputStream os=socket.getOutputStream();
          pw=new PrintWriter(os,true);

         new Thread(new Runnable() {
             @Override
             public void run() {
                 while (true) {
                     try {
                         String reponse = bfr.readLine();
                         Platform.runLater(()->{
                             observableList.add(reponse);
                         });

                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
         }).start();
     } catch (IOException e) {
         throw new RuntimeException(e);
     }

 });
   buttonEnvoyer.setOnAction((event )-> {

    String message=textFieldMessage.getText();
    pw.println(message);

});

    }

}
