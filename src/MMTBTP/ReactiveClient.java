package MMTBTP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ReactiveClient {
    public static void main(String[] args) throws IOException {
        System.out.println("le client est connecté");
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.01",899));
        Scanner scanner=new Scanner(System.in);
        new Thread(()->{
            while (true){
                ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                try {
                    socketChannel.read(byteBuffer);
                    String response=new String(byteBuffer.array()).trim();
                    if(response.length()>0){
                        System.out.println("Response => "+response);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        while(true){
            String request = scanner.nextLine();
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            byteBuffer.put(request.getBytes());
            byteBuffer.flip();
            int bytesWritten = socketChannel.write(byteBuffer);
            System.out.println(String.format("sending %s bytes",bytesWritten));
        }
    }
}
