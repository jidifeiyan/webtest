package com.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Client {
	
	protected static Log LOG = LogFactory.getLog(Client.class);
	
	public static void main(String[] args) {
		LOG.info("==dsdf");
			//start();	
	}
	
	public static byte[] intToBytes(int num) {
		byte[] data = new byte[4];
		data[0] = (byte) ((num >> 24) & 0xFF);
		data[1] = (byte) ((num >> 16) & 0xFF);
		data[2] = (byte) ((num >> 8) & 0xFF);
		data[3] = (byte) (num & 0xFF);
		return data;
	}
	
	public static void start() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			SocketAddress address = new InetSocketAddress("localhost", 55555);
			SocketChannel client = SocketChannel.open(address);
			client.configureBlocking(false);
			String a = "asdasdasdasddffasfasadfdf";
			ByteBuffer buffer = ByteBuffer.allocate(a.length()+6);
			System.out.println(a.length());
			buffer.put(intToBytes(a.length()));
			buffer.put(a.getBytes());
			buffer.clear();
			int len = client.write(buffer);
			System.out.println("发送数据: " + new String(buffer.array()));
			while (true) {
				buffer.flip();
				int i = client.read(buffer);
				if (i > 0) {
					byte[] b = buffer.array();
					System.out.println("接收数据: " + new String(b));
					client.close();
					System.out.println("连接关闭!");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}