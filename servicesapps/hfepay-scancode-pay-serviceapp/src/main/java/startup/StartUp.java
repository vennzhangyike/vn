package startup;

import java.io.IOException;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 启动jar文件
 *
 */
public class StartUp {
	
	private static GenericXmlApplicationContext ctx;

	public static void main(String args[]) throws IOException{
	     ctx = new GenericXmlApplicationContext();  
	     ctx.getEnvironment().setActiveProfiles("production");  
	     ctx.load("classpath:applicationContext.xml","classpath:applicationContext-services.xml");  
	     ctx.refresh();
	     ctx.start();
	   
	     Object lock = new Object();
         synchronized (lock) {
             try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
         }
	}

}
