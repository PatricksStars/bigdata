package com.lt.hdfs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;

public class HdfsUtil {
	
	/**
	 * hdfs视频文件位置
	 */
	public static String VIDEO_DIR = "/videos/videos_china/";
	
	/**
	 * hdfs  uri地址
	 */
	public static final String HDFS_URI = "hdfs://localhost:9000";
	
	/**
	 * hdfs用户
	 */
	public static final String HDFS_USER = "ÂÞÌÎ";
	
	/**
	 * 流传输文件大小
	 */
	public static final int IOSIZE = 4096;
	
	/**
	 * hadoop启动命令文件地址
	 */
	public static final String START_ALL_BAT = "D:\\JaveDev\\HADOOP\\hadoop\\hadoop_start_all.bat";
	
	/**
	 * hadoop停止命令文件地址
	 */
	public static final String STOP_ALL_BAT = "D:\\JaveDev\\HADOOP\\hadoop\\hadoop_stop_all.bat";
	
	
	/**
	 * 获取hdfs连接
	 * @return
	 */
	public static FileSystem getFS() {
		Configuration conf=new Configuration();
		try {
			conf.setBoolean("fs.hdfs.impl.disable.cache", true);
			FileSystem fs=FileSystem.get(new URI(HDFS_URI),conf);
			return fs;	
		} catch (IOException | URISyntaxException e) {
			System.out.println(e.getMessage());
			System.out.println("*******************HDFS连接错误*******************");
		}
		throw new RuntimeException("*******************HDFS连接错误*******************");
	}
	
	/**
	 * 上传文件
	 * @param fs
	 * @param localFilePath
	 * @param hdfsFilePath
	 */
	public static void upload(FileSystem fs, String localSrcPath, String hdfsPath) {
		try {
			fs.copyFromLocalFile(new Path(localSrcPath),new Path(hdfsPath));
			System.out.println("*******************上传成功*******************");
		} catch (IllegalArgumentException | IOException e) {
			System.out.println(e.getMessage());
			System.out.println("*******************HDFS上传错误*******************");
		} 
	}
	
	/**
	 * 上传大量文件
	 * @param fs
	 * @param localDirPath
	 * @param hdfsFilePath
	 */
	public static void uploadMore(FileSystem fs, String localSrcPath, String hdfsPath) {
		File files = new File(localSrcPath);
		if(files.isDirectory()) {
			for(File file : files.listFiles()) {
				upload(fs,file.getAbsolutePath(),VIDEO_DIR);
			}
		}else if(files.isFile()) {
			upload(fs,files.getAbsolutePath(),VIDEO_DIR);
		}
		
		
	}
	
	/**
	 * 下载文件
	 * @param fs
	 * @param localDestPath 本地文件地址
	 * @param hdfsPath hdfs文件地址
	 */
	public static void download(FileSystem fs, String localDestPath, String hdfsPath) {
		try {
			InputStream in = fs.open(new Path(hdfsPath));
			// 输出路径
			OutputStream out = new FileOutputStream(localDestPath);
			// 工具类将in中的内容copy到out中，大师级默认都是4096
			IOUtils.copyBytes(in, out, IOSIZE, true);
		} catch (IllegalArgumentException | IOException e) {
			System.out.println(e.getMessage());
			System.out.println("*******************HDFS下载错误*******************");
		}
	}
	
	/**
	 * 查询文件
	 * @param hdfsPath
	 * @param page
	 * @param size
	 * @return
	 */
	public static List<String> list(String hdfsPath) {
		List<String> list = new ArrayList<>();
		try {
			FileSystem fs = getFS();
			RemoteIterator<FileStatus> statusIterator= fs.listStatusIterator(new Path(hdfsPath));
			while(statusIterator.hasNext()) {
				FileStatus status = statusIterator.next();
				list.add(status.getPath().getName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("*******************HDFS查询失败*******************");
		}
		return list;
		
	}
	
	/**
	 * 获取hdfs文件总数
	 * @param hdfsPath
	 * @return
	 */
	public static int getCount (String hdfsPath) {
		int count = 0;
		try {
			FileSystem fs = getFS();
			count = fs.listStatus(new Path(hdfsPath)).length;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("*******************HDFS查询失败*******************");
		}
		return count;
	}
	
	/**
	 * 获取hdfs流
	 * @return
	 */
	public static InputStream getHdfsStream(String hdfsPath) {
		InputStream is = null;
		//判断hdfs资源是否存在
		try {
			FileSystem fs = getFS();
			Path fsPath = new Path(hdfsPath);
			if(!fs.exists(fsPath)) {
				throw new RuntimeException("hdfs资源不存在");
			}
			is = fs.open(fsPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
}
