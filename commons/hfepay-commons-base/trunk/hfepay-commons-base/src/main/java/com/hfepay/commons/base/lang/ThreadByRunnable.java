package com.hfepay.commons.base.lang;

public class ThreadByRunnable implements Runnable {  
    private String name;  
  
    public ThreadByRunnable(String name) {  
        this.name = name;  
    }  
  
    public void run() {  
        for (int i = 0; i < 1; i++) {  
        	String num = GenerateSequenceUtil.getBatchNo();
        	System.out.println(name+":"+num);
        }  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
  
        for (int i = 0; i < 100; i++) {
        	ThreadByRunnable ds1 = new ThreadByRunnable("线程"+i);
        	Thread t1 = new Thread(ds1);  
        	t1.start(); 
		}
        
        /*for (int i = 0; i < 5; i++) {
        	new Thread(new Runnable(){
	            @Override
	            public void run() {
	            	for (int i = 0; i < 5; i++) {  
	                	Long num = GenerateSequenceUtil.getUniqueId();
	                	System.out.println(num);
	                }  
            }
        	}).start();
		}*/
        
    }  
}  
