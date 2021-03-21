package com.spring.articles;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public class testingTrees {

	public static void main(String[] args)  {
		//mac address for views 
		//main67();
		
		
		
		
		RedBlackTreeX<String> redBlackTreeX=new RedBlackTreeX<>();
		redBlackTreeX.setRoot("HowUDoing");
		redBlackTreeX.add("test","test");
		System.out.println(redBlackTreeX.getTreeAsList());
		
		
		try {
			//FileOutputStream fout=new FileOutputStream("ArticlesTree.ser");
			//ObjectOutputStream obgOut=new ObjectOutputStream(fout);
		//	obgOut.writeObject(redBlackTreeX);
			
			FileInputStream gin=new FileInputStream("ArticlesTree.ser");
			ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
			RedBlackTreeX<String> ar =(RedBlackTreeX<String>)oiObjectInputStream.readObject();
			System.out.println(ar.getTreeAsList());
			System.out.println(ar.getTreeContents());
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		//tets();
		
		/*
		try {
			FileInputStream gin=new FileInputStream("ArticlesTree.ser");
			ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
			RedBlackTreeX<String> ar =(RedBlackTreeX<String>)oiObjectInputStream.readObject();
			ar.inorderDisplay();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("asdasd--------------");
		
		
		try {
			FileInputStream gin=new FileInputStream("as1.ser");
			ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
			article ar =(article)oiObjectInputStream.readObject();
			System.out.println(ar.getTitle());
			System.out.println(ar.getBody());
			System.out.println(ar.getDate());
			
		} 
		catch (Exception e) {
			// TODO: handle exception
		}  */
		
	}
	 public static void main67(){
	        
		    InetAddress ip;
		    try {
		            
		        ip = InetAddress.getLocalHost();
		        System.out.println("Current IP address : " + ip.getHostAddress());
		        
		       // NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		        
		        NetworkInterface network=NetworkInterface.getByInetAddress(ip);
		        
		        byte[] mac = network.getHardwareAddress();
		            
		        System.out.print("Current MAC address : ");
		            
		        StringBuilder sb = new StringBuilder();
		        for (int i = 0; i < mac.length; i++) {
		            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		        }
		        System.out.println(sb.toString());
		            
		    } catch (UnknownHostException e) {
		        
		        e.printStackTrace();
		        
		    } catch (SocketException e){
		            
		        e.printStackTrace();
		            
		    }
		        
		   }
	
	
	
	
	
	static void tets() {
		try {
			File file=new File("asd/");
			file.mkdir();
			FileWriter fwFileWriter=new FileWriter(file);
			fwFileWriter.write("\r\n"
					+ "<!DOCTYPE HTML>\r\n"
					+ "<html>\r\n"
					+ "<head> \r\n"
					+ "    <title>Response page</title>\r\n"
					+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
					+ "\r\n"
					+ "    <meta charset=\"utf-8\">\r\n"
					+ "		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
					+ "\r\n"
					+ "		<!-- Bootstrap CSS -->\r\n"
					+ "		<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\r\n"
					+ "		<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "</head>\r\n"
					+ "\r\n"
					+ "<body style=\"background-color: black;color: white;\">\r\n"
					+ "	<br><br><br>\r\n"
					+ "	<div class=\"container\">\r\n"
					+ "		<!-- {message is received using this way} -->\r\n"
					+ "		<p >Hello Spring MVC Framework!hihaiiahhaaishiashd</p>    \r\n"
					+ "\r\n"
					+ "		<br>\r\n"
					+ "		<a href=\"http://localhost:8080/greetingWithQuery?name=fdsf&id=sfsdrsf544444b444444\">send Data</a>\r\n"
					+ "	</div> \r\n"
					+ "\r\n"
					+ "<script src=\"https://code.jquery.com/jquery-3.1.1.min.js\"></script>\r\n"
					+ "\r\n"
					+ "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\r\n"
					+ "\r\n"
					+ "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "</body>\r\n"
					+ "</html>");
			fwFileWriter.close();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
