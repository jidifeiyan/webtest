package com.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Channel {

	 private static final int port = 30008;  
	 private static final int TIMEOUT = 3000;  
	 private static byte end = '\n'; 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void accept(SelectionKey key) throws Exception {  
         SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();  
         socketChannel.configureBlocking(false);  
         socketChannel.register(key.selector(), SelectionKey.OP_READ);  
         // 获取连接后,开始准备接受client的write,即当前socket的read.  
         System.out.println("Server accept...");  
     } 
	
	public static void read(SelectionKey key) throws Exception {  
        SocketChannel socketChannel = (SocketChannel) key.channel();  
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);  
        while(socketChannel.read(byteBuffer) != -1){  
            byte last = byteBuffer.get(byteBuffer.position()-1);  
            if(last == end){  
                break;  
            }  
        }  
        byteBuffer.flip();  
        Charset charset = Charset.defaultCharset();  
        CharBuffer charBuffer = charset.decode(byteBuffer);  
        System.out.println("Client read: " + charBuffer.toString());  
        key.interestOps(SelectionKey.OP_WRITE);  
    }  

	 public static void write(SelectionKey key) throws Exception {  
         SocketChannel socketChannel = (SocketChannel) key.channel();  
         Charset charset = Charset.defaultCharset();  
         String data = "S : " + System.currentTimeMillis();  
         ByteBuffer byteBuffer = charset.encode(data);  
         int limit = byteBuffer.limit();  
         byteBuffer.clear();  
         byteBuffer.position(limit);  
         byteBuffer.put(end);  
         byteBuffer.flip();  
         while(byteBuffer.hasRemaining()){  
             socketChannel.write(byteBuffer);  
         }  
         key.interestOps(SelectionKey.OP_READ);  
     }  
	 
}
