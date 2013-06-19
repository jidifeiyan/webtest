package com.nio;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Server {
	ServerSocketChannel ssc;

	public static void main(String[] args) {
		String a = "asdasdasdasddffasfasadfdf";
		ByteBuffer echoBuffer = ByteBuffer.allocate(a.length()+4);
		echoBuffer.put(intToBytes(815));
		echoBuffer.put(a.getBytes());
		byte[] data = echoBuffer.array();
		int len = get4BytesToInt(data, 0);
		String str = new String(data);
		System.out.println(str);
		//start();
	}
	
	public static void start() {
		try {
			Selector selector = Selector.open();
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			InetSocketAddress address = new InetSocketAddress(55555);
			ss.bind(address);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("端口注册完毕!");
			while (true) {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectionKeys.iterator();
				ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
				SocketChannel sc;
				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
						ServerSocketChannel subssc = (ServerSocketChannel) key
								.channel();
						sc = subssc.accept();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
						iter.remove();
						System.out.println("有新连接:" + sc);
					} else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						sc = (SocketChannel) key.channel();
						echoBuffer.clear();
						while (true) {
							int a;
							try {
								a = sc.read(echoBuffer);
								System.out.println(echoBuffer.position());
							} catch (Exception e) {
								e.printStackTrace();
								break;
							}
							if (a == -1)
								continue;
							if (a > 0) {
								byte[] b = echoBuffer.array();
								int len = get4BytesToInt(b, 0);
								System.out.println("接收数据: " + new String(b));
								echoBuffer.flip();
								sc.write(echoBuffer);
								System.out.println("返回数据: " + new String(b));
								break;
							}
						}
						sc.close();
						System.out.println("连接结束");
						System.out.println("=============================");
						iter.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int get4BytesToInt(byte[] data, int position) {
		return ((int)(data[position] & 0xFF) << 24)
				| (int)((data[position + 1] & 0xFF) << 16)
				| (int)((data[position + 2] & 0xFF) << 8)
				| (int)(data[position + 3] & 0xFF);
	}
	
	/**
	 * int 转字节
	 * @param num
	 * @return
	 */
	public static byte[] intToBytes(int num) {
		byte[] data = new byte[4];
		data[0] = (byte) ((num >> 24) & 0xFF);
		data[1] = (byte) ((num >> 16) & 0xFF);
		data[2] = (byte) ((num >> 8) & 0xFF);
		data[3] = (byte) (num & 0xFF);
		return data;
	}
	
}