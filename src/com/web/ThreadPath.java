package com.web;

/**
 * 当前线程路径，可以用来读取classpath下的配置
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
