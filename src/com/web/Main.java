package com.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class Main{

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
