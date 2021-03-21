package com.spring.articles;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleViewControllerr {
	static int isSiteReloaded=0;
	@RequestMapping(value = "/article", method = RequestMethod.GET)
	  public String printHello(ModelMap model,
			  @RequestParam(value = "a", defaultValue = "")String data,
			  HttpServletRequest request, HttpServletResponse response) {
	      
		
		System.out.println(data);
		  
		try {
			FileInputStream fin=new FileInputStream(data+".ser");
			ObjectInputStream inputStream=new ObjectInputStream(fin);
			article objArticle=(article)inputStream.readObject();
			System.out.println(objArticle.getBody());
			model.addAttribute("title", objArticle.getTitle());
			model.addAttribute("body",objArticle.getBody());
			model.addAttribute("date",objArticle.getDate());
			model.addAttribute("views",objArticle.getSizeOfViewsSet());
			model.addAttribute("objName",data);
			
			
			
			return "articleContent";
		}
		catch (Exception e) {
			return "page404";
		}
		
	  
	
	}
	
	@RequestMapping("/")
	public String homePage() {
		isSiteReloaded=1;
		return "index";
	}
	
	@RequestMapping(value={"/create-article","/preview","/create-article/preview",
			"/show-articles"})
	public String redirectMainPage() {
		return homePage();
	}
	
	
}
