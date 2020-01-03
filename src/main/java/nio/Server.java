package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wwy
 */
public class Server {
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);//调整缓存的大小可以看到打印输出的变化
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);//调整缓存的大小可以看到打印输出的变化
    String str;

    public void start() throws IOException {
        // 打开服务器套接字通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        ssc.configureBlocking(false);
        // 进行服务的绑定,对哪个端口进行监听
        ssc.bind(new InetSocketAddress("localhost", 8001));
        // 通过open()方法找到Selector
        selector = Selector.open();
        // 注册到selector，等待连接
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        //循环
        while (!Thread.currentThread().isInterrupted()) {
            //阻塞方法，有事件就绪才返回
            selector.select();
            //获取就绪的SelectionKey
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
                keyIterator.remove(); //该事件已经处理，可以丢弃
            }
        }
    }

    private void write(SelectionKey key) throws IOException, ClosedChannelException {
        SocketChannel channel = (SocketChannel) key.channel();
        System.out.println("write:"+str);

        sendBuffer.clear();
        sendBuffer.put(str.getBytes());
        sendBuffer.flip();
        channel.write(sendBuffer);
        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 处理读事件
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // Clear out our read buffer so it's ready for new data
        this.readBuffer.clear();
//        readBuffer.flip();
        int numRead;
        try {
            numRead = socketChannel.read(this.readBuffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        str = new String(readBuffer.array(), 0, numRead);
        System.out.println("read:"+str);
//        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    /**
     * 处理客户端连接事件
     * @param key
     */
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = ssc.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println(clientChannel.getRemoteAddress()+"客户端建立连接！");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("服务端启动！");
        new Server().start();
    }
}
