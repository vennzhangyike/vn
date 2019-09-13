package com.hfepay.commons.utils;

import java.io.IOException;  

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FSDataInputStream;  
import org.apache.hadoop.fs.FSDataOutputStream;  
import org.apache.hadoop.fs.FileStatus;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;  
  
  
public class HdfsUtils {  
    public static void main(String[] args) throws Exception  
    {  
        //uploadLocalFile2HDFS("E:/1.txt","/tmp/1.txt");//E盘下文件传到hdfs上  
        //createNewHDFSFile("/tmp/create2", "hello");  
        //String str = new String(readHDFSFile("/tmp/create2"));  
        //System.out.println(str);  
          
        //mkdir("/tmp/testdir");  
        //deleteDir("/tmp/testdir");  
        //listAll("/tmp/");  /swallow/upload/
                getDateNodeHost();  
                listAll("/swallow/upload");
    }  
    //获取HDFS集群上所有节点名称信息  
    public static void getDateNodeHost() throws IOException{  
          
        Configuration conf = getConf();  
  
               FileSystem fs=FileSystem.get(conf);  
               DistributedFileSystem hdfs = (DistributedFileSystem)fs;  
               DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();  
               for(int i=0;i<dataNodeStats.length;i++){  
                  System.out.println("DataNode_"+i+"_Name:"+dataNodeStats[i].getHostName());  
               }  
    }  
    /* 
     * upload the local file to the hds  
     * 路径是全路径 
     */  
    public static void uploadLocalFile2HDFS(String s, String d)   
        throws IOException  
    {  
        Configuration conf = getConf();  
        FileSystem hdfs = FileSystem.get(conf);  
        Path src = new Path(s);  
        Path dst = new Path(d);  
        hdfs.copyFromLocalFile(src, dst);  
        hdfs.close();  
    }  
      
    /* 
     * create a new file in the hdfs. 
     * notice that the toCreateFilePath is the full path 
     * and write the content to the hdfs file. 
     */  
    public static void createNewHDFSFile(String toCreateFilePath, String content) throws IOException  
    {  
        Configuration conf = getConf();  
        FileSystem hdfs = FileSystem.get(conf);  
          
        FSDataOutputStream os = hdfs.create(new Path(toCreateFilePath));  
        os.write(content.getBytes("UTF-8"));  
        os.close();  
        hdfs.close();  
    }  
      
    /* 
     * delete the hdfs file  
     * notice that the dst is the full path name 
     */  
    public static boolean deleteHDFSFile(String dst) throws IOException  
    {  
        Configuration conf = getConf();  
        FileSystem hdfs = FileSystem.get(conf);  
          
        Path path = new Path(dst);  
        boolean isDeleted = hdfs.delete(path);  
        hdfs.close();  
        return isDeleted;  
    }  
      
      
    /* 
     * read the hdfs file content 
     * notice that the dst is the full path name 
     */  
    public static byte[] readHDFSFile(String dst) throws Exception  
    {  
        Configuration conf = getConf();  
        FileSystem fs = FileSystem.get(conf);  
          
        // check if the file exists  
        Path path = new Path(dst);  
        if ( fs.exists(path) )  
        {  
            FSDataInputStream is = fs.open(path);  
            // get the file info to create the buffer  
            FileStatus stat = fs.getFileStatus(path);  
            // create the buffer  
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];  
            is.readFully(0, buffer);  
              
            is.close();  
            fs.close();  
              
            return buffer;  
        }  
        else  
        {  
            throw new Exception("the file is not found .");  
        }  
    }  
      
      
    /* 
     * make a new dir in the hdfs 
     * the dir may like '/tmp/testdir' 
     */  
    public static void mkdir(String dir) throws IOException  
    {  
        Configuration conf = getConf();  
        FileSystem fs = FileSystem.get(conf);  
        fs.mkdirs(new Path(dir));  
          
        fs.close();  
    }  
      
    /* 
     * delete a dir in the hdfs 
     * dir may like '/tmp/testdir' 
     */  
    public static void deleteDir(String dir) throws IOException  
    {  
        Configuration conf = getConf();  
        FileSystem fs = FileSystem.get(conf);  
        fs.delete(new Path(dir));  
        fs.close();  
    }  
    //文件系统连接到 hdfs的配置信息   
    private static Configuration getConf(){  
        Configuration conf = new Configuration();  
        // 这句话很关键，这些信息就是hadoop配置文件中的信息  
        conf.set("mapred.job.tracker", "172.168.8.210:9001");  
        conf.set("fs.default.name", "hdfs://172.168.8.210:9000");  
        return conf;  
    }  
      
    /** 
    * @Title: listAll  
    * @Description: 列出目录下所有文件  
    * @return void    返回类型  
    * @throws 
     */  
    public static void listAll(String dir) throws IOException  
    {  
        Configuration conf = getConf();  
        FileSystem fs = FileSystem.get(conf);  
        FileStatus[] stats = fs.listStatus(new Path(dir));  
        for(int i = 0; i < stats.length; ++i)  
        {  
            if (!stats[i].isDir())  
            {  
                // regular file  
                System.out.println(stats[i].getPath().toString());  
            }  
            else   
            {  
                // dir  
                System.out.println(stats[i].getPath().toString());  
            }  
//          else if(stats[i].())  
//          {  
//              // is s symlink in linux  
//              System.out.println(stats[i].getPath().toString());  
//          }  
                  
        }  
        fs.close();  
    }  
      
}  
