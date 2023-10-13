package com.lt.hdfs;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.stereotype.Service;

import com.lt.hdfs.util.HdfsUtil;

@Service
public class Hdfs {
	
	public FileSystem getFS() {
		return HdfsUtil.getFS();
	}
}
