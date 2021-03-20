package com.spring.articles;



import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ArticlesApplication {
	public static void main(String[] args) {
		try {
			File f=new File("ArticlesTree.ser");
			if(!f.exists()) {
				RedBlackTreeX<String> redBlackTreeX=new RedBlackTreeX<>();
				redBlackTreeX.setRoot("HowUDoing");
				redBlackTreeX.add("test","test");
				System.out.println(redBlackTreeX.getTreeAsList());
				FileOutputStream fout=new FileOutputStream("ArticlesTree.ser");
				ObjectOutputStream obgOut=new ObjectOutputStream(fout);
				obgOut.writeObject(redBlackTreeX);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		try {
			File f=new File("treeEditProgress.ser");
			if(!f.exists()) {
				RedBlackTreeX<String> redBlackTreeX=new RedBlackTreeX<>();
				redBlackTreeX.setRoot("HowUDoing");
				redBlackTreeX.add("test","test");
				System.out.println(redBlackTreeX.getTreeAsList());
				FileOutputStream fout=new FileOutputStream("treeEditProgress.ser");
				ObjectOutputStream obgOut=new ObjectOutputStream(fout);
				obgOut.writeObject(redBlackTreeX);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		SpringApplication.run(ArticlesApplication.class, args);

	}

}
