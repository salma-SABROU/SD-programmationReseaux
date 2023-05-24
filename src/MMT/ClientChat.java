package MMT;

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

public class ClientChat extends Application {
    PrintWriter pw;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client Chat");
        BorderPane borderPane=new BorderPane();

        Label labelHost=new Label("Host :") ;
        TextField textFieldhost=new TextField("localhost");

        Label labelPort =new Label("port :");
        TextField textFieldPort=new TextField("1234");
        Button buttonConnecter=new Button("Connecter");

        HBox hBox=new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
        hBox.getChildren().addAll(labelHost,textFieldhost,labelPort,textFieldPort,buttonConnecter);

        borderPane.setTop(hBox);
        VBox vBox=new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));


        ObservableList<String> observableList = FXCollections.observableArrayList();

        ListView<String>listView=new ListView<>(observableList);
        vBox.getChildren().add(listView);

        borderPane.setCenter(vBox);

        Label labelMessage = new Label("Message : ");
        TextField textFieldMessage = new TextField();
        textFieldMessage.setPrefSize(400,30);
        Button buttonEnvoyer = new Button("Envoyer");

        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10));
        hBox2.getChildren().addAll(labelMessage,textFieldMessage,buttonEnvoyer);

        borderPane.setBottom(hBox2);


        Scene scene=new Scene(borderPane,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();


        buttonConnecter.setOnAction((evt)->{
            String host=textFieldhost.getText();
            int port=Integer.parseInt(textFieldPort.getText());


            try {
               Socket socket = new Socket(host,port);
                InputStream is=socket.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader bfr=new BufferedReader(isr);

                OutputStream os=socket.getOutputStream();

                pw=new PrintWriter(os,true);

                new Thread( new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                String  rep = bfr.readLine();
                                Platform.runLater(()->{
                                    observableList.add(rep);
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

        buttonEnvoyer.setOnAction((evt)->{
            String message = textFieldMessage.getText();
            pw.println(message);

        });
        }


}
