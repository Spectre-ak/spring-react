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
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;



/**
 * 
 * RESTful API controller that returns the URL of the article on success and also a 
 * list of available articles
 * 
*/
@RestController
public class ArticleController{
	public static int cont=0;
	/**
	 * API end point for receiving the requests
	 * @param title
	 * @param body
	 * @param date
	 * @return
	 */
	@PostMapping("/uploadArticle")
	//@CrossOrigin(origins = "http://localhost:3000")
	public HashMap<Integer,String> sendResponse(@RequestParam(value="title",defaultValue = "null")String title,
			@RequestParam(value = "body",defaultValue = "null")String body,
			@RequestParam(value = "date", defaultValue = "null")String date) {
		
		if(title.equals("null") || body.equals("null")  || date.equals("null") ) {
			return new HashMap<>();
		}
		
		
		
		System.out.println("request received "+title+" , "+body+" and "+date);
		
		article articleObj=new article();
		articleObj.setBody(body);
		articleObj.setDate(date);
		articleObj.setTitle(title);
		//ascii codes of characters which cannot be present in the url of the article
		int arr[]= {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 34, 42, 47, 60, 62, 63, 92, 124};
		
		String newTitle="";
		for(int i=0;i<title.length();i++) {
			int ascii=(int)title.charAt(i);
			if(Arrays.binarySearch(arr,ascii)>=0) {
				continue;
			}
			newTitle+=title.charAt(i);
		}
		title=newTitle;
		
		while(true) {
			try {
				String treeObjectPath="ArticlesTree.ser";
				Path path=Paths.get(treeObjectPath);
				Path path2=Paths.get("treeEditProgress.ser");
				
				FileChannel channel=FileChannel.open(path, StandardOpenOption.WRITE,StandardOpenOption.APPEND);
				FileChannel channel2=FileChannel.open(path2, StandardOpenOption.WRITE,StandardOpenOption.APPEND);
				
				FileLock lock=channel.tryLock();
				FileLock lock2=channel2.tryLock();
				
				if(lock2==null) {
					if(lock!=null) {
						channel.close();
					}
					Thread.sleep(100);
					continue;
				}
				if(lock==null) {
					if(lock2!=null) {
						channel2.close();
					}
					Thread.sleep(100);
					continue;
				}
				
				channel.close();
				FileInputStream gin=new FileInputStream("ArticlesTree.ser");
				ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
				RedBlackTreeX<String> ar =(RedBlackTreeX<String>)oiObjectInputStream.readObject();
				
				String articleURL=title.replaceAll(" ","-");
				if(ar.add(articleURL,articleObj.getTitle())) {
					String objName=articleURL+".ser";
					//writing the object
					FileOutputStream fout=new FileOutputStream(objName);
					ObjectOutputStream out=new ObjectOutputStream(fout);
					out.writeObject(articleObj);
					
					//below method will create static page for this article
					//saveArticleHTML(articleURL, articleObj);
					
					//writing the tree object
					fout=new FileOutputStream("ArticlesTree.ser");
					out=new ObjectOutputStream(fout);
					
					out.writeObject(ar);
					channel2.close();
					
					HashMap<Integer,String> hm=new HashMap<>();
					hm.put(1,"https://sharearticles.azurewebsites.net/article?a="+articleURL);
					cont++;
					return hm;
				}
				else {
					
					int ind=1;
					//articleURL+=1+"";
					while(!ar.add(articleURL+""+ind,articleObj.getTitle())) {
						ind++;
						//articleURL=articleURL.substring(0,articleURL.length()-1)+ind+"";
					}
					articleURL+=ind+"";
					String objName=articleURL+".ser";
					//writing the article object
					FileOutputStream fout=new FileOutputStream(objName);
					ObjectOutputStream out=new ObjectOutputStream(fout);
					out.writeObject(articleObj);
					
					//below method will create static page for this article
					//saveArticleHTML(articleURL, articleObj);
					
					//writing the updated tree object
					fout=new FileOutputStream("ArticlesTree.ser");
					out=new ObjectOutputStream(fout);
					out.writeObject(ar);
					channel2.close();
					
					HashMap<Integer,String> hm=new HashMap<>();
					hm.put(1,"https://sharearticles.azurewebsites.net/article?a="+articleURL);
					cont++;
					return hm;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				HashMap<Integer,String> hm=new HashMap<>();
				hm.put(1,"error");
				return hm;
			}
			
		}
		
		
	}
	
	
	/**
	 * returns articles list 
	 * @return
	 */
	@PostMapping("/getArticles")
	//@CrossOrigin(origins = "http://localhost:3000")
	public List<HashMap<String,String>> sendArticlesList() {
		
		System.out.println("sendArticlesList request");
		
		while(true) {
			try {
				FileInputStream gin=new FileInputStream("ArticlesTree.ser");
				ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
				@SuppressWarnings("unchecked")
				RedBlackTreeX<String> ar =(RedBlackTreeX<String>)oiObjectInputStream.readObject();
				List<HashMap<String,String>> listInorderList=ar.getTreeContents();
				oiObjectInputStream.close();
				System.out.println(listInorderList);
				
				return listInorderList;
				
			}
			catch (Exception e) {
				e.printStackTrace();
				
				try {
					Thread.sleep(1000);
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
	}
	
	@GetMapping("getStatus")
	public String getStatus() {
		String result="";
		try {
			FileInputStream gin=new FileInputStream("ArticlesTree.ser");
			ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
			@SuppressWarnings("unchecked")
			RedBlackTreeX<String> ar =(RedBlackTreeX<String>)oiObjectInputStream.readObject();
			List<HashMap<String,String>> listInorderList=ar.getTreeContents();
			oiObjectInputStream.close();
			System.out.println(listInorderList);
			result+=listInorderList.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			result+=e.getMessage();
			try {
				Thread.sleep(1000);
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return result+"<br>";
	}
	
	@GetMapping("/create-servlet-cookie")
    public String setCookie(HttpServletRequest request, HttpServletResponse response) {
      
		Cookie jwtTokenCookie = new Cookie("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t");

	      jwtTokenCookie.setMaxAge(1123424);
	      jwtTokenCookie.setSecure(true);
	      jwtTokenCookie.setHttpOnly(true);
	      jwtTokenCookie.setPath("/");
	      jwtTokenCookie.setDomain("sharearticles.azurewebsites.net");
	      response.addCookie(jwtTokenCookie);

	      Cookie jwtTokenCookie2 = new Cookie("asdad", "addsw324444444444444sdaffffffffffffff");

	      jwtTokenCookie2.setMaxAge(86400);
	      jwtTokenCookie2.setSecure(true);
	      jwtTokenCookie2.setHttpOnly(true);
	      jwtTokenCookie2.setPath("/");
	      jwtTokenCookie2.setDomain("sharearticles.azurewebsites.net");
	      response.addCookie(jwtTokenCookie2);
	      
        return ("Cookie with name  and valuwas create");
    }
	@GetMapping("/getCookies")
	public String getCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] ar=request.getCookies();
		String cookieString="";
		for(Cookie c:ar) {
			System.out.println(c.getName()+" "+c.getValue());
			cookieString+=c.getName()+" "+c.getValue()+"<br>";
		}
		
		return cookieString;
	}

	/**
	 * Mapping for checking cookie availability
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/checkCookieSettings")
	//@CrossOrigin(origins = "http://localhost:3000")
	public String checkCookieSettings(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] ar=request.getCookies();
		System.out.println("cookie received "+Arrays.toString(ar));
		if(ar!=null)
			for(Cookie cookie:ar) {
				if(cookie.getName().equals("userID"))
					return "cookie present";
			}
		//means there is no cookie for userID
		
		Cookie cookie=new Cookie("userID",UUID.randomUUID().toString());
		cookie.setMaxAge(Integer.MAX_VALUE);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain("sharearticles.azurewebsites.net");
		response.addCookie(cookie);
		return "cookie added";
	}
	
	@PostMapping("/addViewOfTheObject")
	//@CrossOrigin(origins = "http://localhost:3000")
	public String addViewOfTheObject(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "obj",defaultValue = "")String objectName,
			@RequestParam(value = "exInfos",defaultValue = "")String extraInfos) {
		System.out.println(objectName+" "+extraInfos);
		
		if(extraInfos.equals(""))
			return "empty extraInfos";
		if(objectName.equals("")) 
			return "empty object name";
		
		String userID="";
		Cookie ar[]=request.getCookies();
		for(Cookie cookie:ar) {
			if(cookie.getName().endsWith("userID")) {
				userID=cookie.getValue();
			}
		}
		if(userID=="")
			return "user id is stil null --> no cookie stored yet";
		
		while(true) {
			try {
				String ObjectPath=objectName+".ser";
				Path path=Paths.get(ObjectPath);
				
				FileChannel channel=FileChannel.open(path, StandardOpenOption.WRITE,StandardOpenOption.APPEND);
				
				FileLock lock=channel.tryLock();
				
				if(lock==null) {
					Thread.sleep(100);
					continue;
				}
				
				channel.close();
				FileInputStream gin=new FileInputStream(ObjectPath);
				ObjectInputStream oiObjectInputStream=new ObjectInputStream(gin);
				article articleObj=(article)oiObjectInputStream.readObject();
				oiObjectInputStream.close();
				if(articleObj.addViewID(userID)) {
					if(articleObj.checkIfDataIsPresentInHashMap(extraInfos)) {
						articleObj.distinctViewsCookieUUIDsSet.remove(userID);
						FileOutputStream fout=new FileOutputStream(ObjectPath);
						ObjectOutputStream outputStream=new ObjectOutputStream(fout);
						outputStream.writeObject(articleObj);
						outputStream.close();
						return "extraInfos is already present so no view";
					}
					else {
						articleObj.addExtraInfo(extraInfos);
						FileOutputStream fout=new FileOutputStream(ObjectPath);
						ObjectOutputStream outputStream=new ObjectOutputStream(fout);
						outputStream.writeObject(articleObj);
						outputStream.close();
						return "extraInfos is unique so added view";
					}
				}
				else
					return "userID is already present so no view";
				
				
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error occured on while loop ";
			}
			
		}
		
		
	
	}
	
	
	
	/**
	 * method for creating dynamic pages for articles
	 * @param title
	 * @param objArticle
	 */
	@SuppressWarnings("unused")
	private static void saveArticleHTML(String title,article objArticle) {
		try {
			File file=new File("src/main/resources/static/"+title+"/");
			file.mkdir();
			FileWriter fwFileWriter=new FileWriter(new File("src/main/resources/static/"+title+"/index.html"));
			fwFileWriter.write("<!DOCTYPE HTML>\r\n"
					+ "<head> \r\n"
					+ "    <title>"+objArticle.getTitle()+"</title>\r\n"
					+ "    <!-- Required meta tags -->\r\n"
					+ "    <meta charset=\"utf-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
					+ "\r\n"
					+ "    <!-- Bootstrap CSS -->\r\n"
					+ "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\r\n"
					+ "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">\r\n"
					+ "    <link rel=\"shortcut icon\" href=\"logo.png\">\r\n"
					+ "</head>\r\n"
					+ "<body style=\"background-color: #131316;color: white;\">\r\n"
					+ "	\r\n"
					+ "    <div class=\"container\"><br/>\r\n"
					+ "		<div class=\"container\" align=\"center\">\r\n"
					+ "			<br/>\r\n"
					+ "			<div class=\"card\" id=\"1\" style=\"background-color:#131316\" >\r\n"
					+ "			  <div class=\"card-body\" id=\"11\"> \r\n"
					+ "			  	<h4>"+objArticle.getTitle()+"</h4>\r\n"
					+ "			  </div>\r\n"
					+ "			</div>\r\n"
					+ "			<br/><br/>\r\n"
					+ "\r\n"
					+ "		</div>\r\n"
					+ "		<p>"+objArticle.getDate()+"</p>\r\n"
					+ "		<div th:text=\"${article.body}\" style=\"color:white,whiteSpace:pre-wrap\" id=\"body\">\r\n"
					+ "		"+objArticle.getBody()+"	\r\n"
					+ "		</div>\r\n"
					+ "	</div>\r\n"
					+ "\r\n"
					+ "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\r\n"
					+ "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\r\n"
					+ "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\r\n"
					+ "</body>\r\n"
					+ "</html>\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "");
			fwFileWriter.close();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
