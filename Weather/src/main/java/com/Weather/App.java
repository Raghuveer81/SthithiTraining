package com.Weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.Example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	
    	Scanner s=new Scanner(System.in);
    	String responseBody = null;
    	InputStream contentStream = null;
    	try {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	String firstURl ="http://api.openweathermap.org/data/2.5/weather?zip=";
    	
 
    	String options = "y";     
        
        while(options.equals("y"))
    	
        {
    	System.out.println("Enter your ZipCode for weather:"); 
    	int zipcode= s.nextInt();
    	String secondURL = zipcode+",us";
		HttpGet getRequest = new HttpGet(firstURl+secondURL);
		
		getRequest.addHeader("x-api-key", "abdf24af5c13301fec90c7e6f48a048a");
	
		HttpResponse response = httpClient.execute(getRequest);
		
		HttpEntity responseEntity = response.getEntity ();
		contentStream = responseEntity.getContent ();
		Reader isReader = new InputStreamReader (contentStream);
		int contentSize = (int) responseEntity.getContentLength ();
//		if (contentSize < 0)
//			contentSize = 8*1024;
		StringWriter strWriter = new StringWriter (contentSize);
		char[] buffer = new char[8*1024];
		int n = 0;
		while ((n = isReader.read(buffer)) != -1) {
				strWriter.write(buffer, 0, n);
		}
		responseBody = strWriter.toString ();
		//System.out.println(responseBody);
		ObjectMapper mapper = new ObjectMapper();
		
		Example weather = mapper.readValue(responseBody,Example.class);
		System.out.println("Temperature: "+weather.getMain().getTemp()+" degrees");
		System.out.println("Pressure: "+weather.getMain().getPressure());
		System.out.println("City: "+weather.getName());
		
		System.out.println("Do you still want to continue?? (y/n)");
        options = s.next();
        
        }
        System.out.println("Thank you..!!");
        s.close();
		
			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
