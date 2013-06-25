package com.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * 解析xml的工具包
 * 
 * @author Administrator
 * 
 */
public class ParseXmlUtil {
	/**
	 * 发送xml数据获取xml结果
	 * 
	 * @param xml
	 *            xml字符串
	 * @param urlAddress
	 *            接口地址
	 * @return
	 */
	public String sendXmlMsgGetResult(String xml, String urlAddress) {
		try {
			String ipAddress = "";
			URL url = new URL(ipAddress + urlAddress);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setRequestProperty("Content-Type",
					"application/xml");
			httpURLConnection.setRequestProperty("Content-Encoding", "gzip");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			
			/*String cookie = UserMap.getCookie();
			if(cookie !=null){
				httpURLConnection.setRequestProperty("Cookie", cookie);
			}*/
			// httpURLConnection.setRequestProperty("Connection", "close");
			httpURLConnection.setConnectTimeout(30000);
			httpURLConnection.setReadTimeout(30000);
			httpURLConnection.connect();
			byte[] data = xml.getBytes("UTF-8");
			OutputStream out = httpURLConnection.getOutputStream();
			GZIPOutputStream gop = new GZIPOutputStream(out);
			gop.write(data);
			gop.flush();
			gop.close();

			InputStream is = httpURLConnection.getInputStream();
			GZIPInputStream gin = new GZIPInputStream(is);
			byte[] compressed = new byte[640];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len = 0;
			while ((len = gin.read(compressed)) > -1) {
				bos.write(compressed, 0, len);
			}
			/*if(cookie == null && urlAddress.indexOf("login") !=-1){
				UserMap.setCookie(httpURLConnection.getHeaderField("Set-Cookie"));
			}*/
			gin.close();
			String resultXml = new String(bos.toByteArray(), "UTF-8");
			return resultXml;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
