package com.web;

/**
 * ��ǰ�߳�·��������������ȡclasspath�µ�����
 * @author dengxl
 *
 */
public class ThreadPath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
				System.out.println(path);
	}

}
