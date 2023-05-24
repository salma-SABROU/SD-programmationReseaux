package ModélServerST;//package ModélServerST;
//
//import java.net.InetSocketAddress;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.ServerSocketChannel;
//import java.util.Iterator;
//import java.util.Set;
//
//public class Test {
//    public static void main(String[] args) throws Exception{
//        Selector selector=Selector.open();
//        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
//        serverSocketChannel.configureBlocking(false);
//        serverSocketChannel.bind(new InetSocketAddress("0.0.0.0",444));
//       // int validOps=serverSocketChannel.validOps();
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//        // event loop
//        while (true){
//            // le nombre de socketchannel
//     int channelCount =selector.select();
//     if (channelCount==0) continue;
//        Set<SelectionKey> selectionkey=selector.selectedKeys();
//       Iterator<SelectionKey> interator=selectionkey.iterator();
//       while (interator.hasNext()){
//          SelectionKey selectionKey= interator.next();
//       }
//
//
//        }
//
//
//    }
//}
