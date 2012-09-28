package cn.edu.pku.ss.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;
import cn.edu.pku.ss.util.WebSocket;

public class DIAController {
	URI uri;
	WebSocket ws;
	String username;
	String password;
	boolean conn = false;
	
	public DIAController(String s,String username,String password) {
		try {
			uri = new URI(s);
			ws = new WebSocket(uri);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.username = username;
		this.password = password;

	}
	
	public void close() throws IOException {
		if(!conn)
			return;
		else {
			ws.close();
			conn = false;
		}
			
	}
	
	public void init() throws LoginFailure {
		try {
			ws.connect();
			ws.send("connect:"+username+" "+password);
			byte[] bs = ws.recv();
			String s = new String(bs);
			if(!s.equals("successful")) {
				ws.close();
				throw new LoginFailure();
			}
			conn =true;
			System.out.println("Login Successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean sendCommand(String sensorID,String command) throws LoginFailure,MessageFormatError {
		if(!conn)
			throw new LoginFailure();
		try {
			ws.send("control:topic=Command"+"&sensorID="+sensorID+"&command="+command);
			byte[] bs = ws.recv();
			String ss = new String(bs);
			if(ss.equals("Wrong message")) {
				//ws.close();
				throw new MessageFormatError();
			}
			else if(ss.equals("Successful")) {
				System.out.println("Successful");
				return true;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
