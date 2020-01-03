package nio;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wwy
 */
public class TestNIO {
    /**
     * 测试Buffer的使用方法和属性
     */
    @Test
    public void testBuffer(){
        CharBuffer buffer = CharBuffer.allocate(16);
        System.out.println("position="+buffer.position());
        System.out.println("limit="+buffer.limit());
        System.out.println("capacity="+buffer.capacity());

        buffer.put("reentrantLockhello!".toCharArray());
        System.out.println("position="+buffer.position());
        System.out.println("limit="+buffer.limit());
        System.out.println("capacity="+buffer.capacity());

        buffer.flip();
        System.out.println("position="+buffer.position());
        System.out.println("limit="+buffer.limit());
        System.out.println("capacity="+buffer.capacity());
        char[] chars = new char[buffer.limit()-1];
        buffer.get(chars);

        System.out.println("position="+buffer.position());
        System.out.println("limit="+buffer.limit());
        System.out.println("capacity="+buffer.capacity());
        System.out.println(new String(chars));

        buffer.compact();
        System.out.println("position="+buffer.position());
        System.out.println("limit="+buffer.limit());
        System.out.println("capacity="+buffer.capacity());

        System.out.println(buffer.get(0));
    }

    @Test
    public void testFileChannel() throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("/data.txt"));
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int num = fileChannel.read(buffer);
        fileChannel.position();
    }
    @Test
    public void testSocketChannel() throws IOException {
        //读操作
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("https://www.javadoop.com", 80));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);

        //写操作
        // 读取数据
        socketChannel.read(buffer);
        // 写入数据到网络连接中
        while(buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
    }

    @Test
    public void testServerSocketChannel() throws Exception{
        // 实例化
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 监听 8080 端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        while (true) {
            // 一旦有一个 TCP 连接进来，就对应创建一个 SocketChannel 进行处理
            SocketChannel socketChannel = serverSocketChannel.accept();
        }
    }

    @Test
    public void testSelector() throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 将通道设置为非阻塞模式，因为默认都是阻塞模式的
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("localhost",8088));

        Selector selector = Selector.open();
        // 注册
        SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            // 判断是否有事件准备好
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;

            // 遍历
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();

                if(selectionKey.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (selectionKey.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (selectionKey.isReadable()) {
                    // a channel is ready for reading
                } else if (selectionKey.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }
}
