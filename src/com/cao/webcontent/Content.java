package com.cao.webcontent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Content {
public String webContent(String str) throws Exception{
	String result="";
	URL url = new URL(str);
	HttpURLConnection  huc  =  (HttpURLConnection) url.openConnection();
	huc.setConnectTimeout(1000000);
	huc.setRequestMethod("GET");
	huc.setDoInput(true);
	huc.setDoOutput(true);
	huc.setUseCaches(false);
	int responseCode = huc.getResponseCode();
	if(responseCode == 200){
		System.out.println("网络连接成功！");
		InputStream  is =  huc.getInputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		String line;
		StringBuffer  sb = new StringBuffer();
		while((line = br.readLine())!=null){
			sb.append(line);
		}
		is.close();
		result = sb.toString();
		
	}
	return  result;
}
}
