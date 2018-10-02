package JavaNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client implements Runnable{
    private static int PORT = 8080;

    public static void main(String[] args)
    {

    }

    public void run(){
        SocketChannel socketChannel = null;
        Selector selector = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost",PORT));
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            while(true) {
                selector.select();
                Iterator ite = selector.selectedKeys().iterator();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (ite.hasNext()) {
                    SelectionKey key = (SelectionKey) ite.next();
                    ite.remove();

                    if (key.isConnectable()) {
                        if (socketChannel.isConnectionPending()) {
                            if (socketChannel.finishConnect()) {
                                //只有当连接成功后才能注册OP_READ事件
                                key.interestOps(SelectionKey.OP_READ);

                                byteBuffer.put((byte) 1);
                                socketChannel.write(byteBuffer);
                            } else {
                                key.cancel();
                            }
                        }
                    } else if (key.isReadable()) {
                        socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        System.out.println(Thread.currentThread().getId() + "---" + byteBuffer.toString());
                    }
                }
            }
    } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
