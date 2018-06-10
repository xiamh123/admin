package com.admin.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.admin.common.fileutil.FtpHelper;
import com.admin.common.fileutil.SFtpHelper;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.ChannelSftp;

/**
 * 
 * @title 文件处理(读写)
 * @author xzm
 * @date 2016年3月17日
 * @version 1.0
 * @modify xiamh	1.封装通过ftp/sftp进行文件上传/下载功能 ；2.封装获取文件MD5方法
 * @date 2016/3/25
 */
public class FileUtils {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/** 读/解文件的编码方式-UTF-8 */
	public static final String ENCODING_UTF8 = "UTF-8";
	/** 读/解文件的编码方式-GBK */
	public static final String ENCODING_GBK = "GBK";
	
	
	/**
	 * 删除指定文件夹下所有文件
	 * @author qinxj
	 * @date   2016/5/22
	 * @param path
	 * @return
	 * @return boolean
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			// throw new
			// PromptErrorException("文件目录"+file.getAbsolutePath()+file.getName()+"不存在.");
			// return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + tempList[i]);// 先删除文件夹里面的文件
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取单个文件的MD5
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getFileMD5(File file) throws UnsupportedEncodingException{
		char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		if(!file.isFile()){
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte[] buffer = new byte[1024];
		int len;
		try{
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while((len=in.read(buffer,0,1024))!=-1){
				digest.update(buffer,0,len);
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		byte[] bt = digest.digest();
		
		char str[] = new char[16 * 2];
		int k = 0;
		for(int i = 0;i < 16;i++){
			byte byte0 = bt[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		String md5 = new String(str);
		return md5;
	}
	
	
	/**
	 * 通过FTP从文件服务器下载文件到本地
	 * @param	Map<String,String> params
	 *  			ip				服务器IP
	 *  			userName		用户名
	 *  			password		密码
	 *  			farPathDW		远端上传/下载目录
	 *  			localPath		本地上传/下载目录
	 *  			fileName		文件名(本地文件名和远端文件名保持一致)
	 * @throws Exception 
	 */
	public static void ftpGetFile(Map<String,String> params) throws Exception{
		try{
			logger.info(" >>>>> FTP开始下载文件");
			FTPClient ftp = FtpHelper.getFtpClient(params.get("ip"), 21, params.get("userName"), params.get("password"), params.get("farPathDW"));
			boolean flag = FtpHelper.getFile(ftp, params.get("localPath")+params.get("fileName"), params.get("fileName"));
			if(!flag){
				throw new Exception("FTP下载文件失败");
			}
			logger.info(" >>>>> FTP下载文件完成");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("FTP下载文件失败");
		}
	}
	
	/**
	 * 通过FTP上传文件到服务器
	 * @param	Map<String,String> params
	 *  			ip				远端IP
	 *  			userName		用户名
	 *  			password		密码
	 *  			farPathDW		远端上传/下载目录
	 *  			localPath		本地上传/下载目录
	 *  			fileName		文件名(本地文件名和远端文件名保持一致)
	 * @throws Exception 
	 */
	public static void ftpPutFile(Map<String,String> params) throws Exception{
		try{
			logger.info(" >>>>> FTP开始上传文件");
			logger.info(" >>>>> 远程地址："+params.get("ip"));
			logger.info(" >>>>> 远程目录："+params.get("farPathDW"));
			logger.info(" >>>>> 本地路径："+params.get("localPath"));
			logger.info(" >>>>> 文件名："+params.get("fileName"));
			
			File file = new File(params.get("localPath")+params.get("fileName"));
			FTPClient ftp = FtpHelper.getFtpClient(params.get("ip"), 21, params.get("userName"), params.get("password"), params.get("farPathDW"));
			boolean flag = FtpHelper.putFile(ftp, params.get("fileName"), new FileInputStream(file));
			if(!flag){
				throw new Exception("FTP上传文件失败");
			}
			logger.info(" >>>>> FTP上传文件完成");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("FTP上传文件失败");
		}
	}
	
	
	/**
	 * 通过SFTP从文件服务器下载文件到本地
	 * @param	Map<String,String> params
	 *  			ip				服务器IP
	 *  			userName		用户名
	 *  			password		密码
	 *  			farPathDW		远端上传/下载目录
	 *  			localPath		本地上传/下载目录
	 *  			fileName		文件名(本地文件名和远端文件名保持一致)
	 * @throws Exception 
	 */
	public static void sftpGetFile(Map<String,String> params) throws Exception{
		logger.info(" >>>>> SFTP开始下载文件");
		File file = new File(params.get("localPath")+params.get("fileName"));
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			ChannelSftp sftp = SFtpHelper.connect(params.get("ip"), 22, params.get("userName"), params.get("password"), params.get("farPathDW"));
			SFtpHelper.download(sftp, params.get("fileName"), file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//连接服务器（上传文件）失败或超时
			//throw new BatchException(BatchExceptionEnum.B7501C, BatchExceptionEnum.B7501M);
		}
		logger.info(" >>>>> SFTP下载文件完成");
	}
	
	
	/**
	 * 通过SFTP上传文件到服务器
	 * @param	Map<String,String> params
	 *  			ip				远端IP
	 *  			userName		用户名
	 *  			password		密码
	 *  			farPathDW		远端上传/下载目录
	 *  			localPath		本地上传/下载目录
	 *  			fileName		文件名(本地文件名和远端文件名保持一致)
	 * @throws Exception 
	 */
	public static void sftpPutFile(Map<String,String> params) throws Exception{
		logger.info(" >>>>> SFTP开始上传文件");
		try {
			ChannelSftp sftp = SFtpHelper.connect(params.get("ip"), 22, params.get("userName"), params.get("password"), params.get("farPathDW"));
			SFtpHelper.upload(sftp, params.get("farPathDW"), params.get("localPath")+params.get("fileName"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//连接服务器（上传文件）失败或超时
			//throw new BatchException(BatchExceptionEnum.B7504C, BatchExceptionEnum.B7504M);
		}
		logger.info(" >>>>> SFTP上传文件完成");
	}
	
	
	
	public static void main (String[] arg) throws Exception{
		File file = new File("c:/ins/file_dev/upload/20160212/101/PL0120160212INS000000101006.DAT");
		System.out.println("本地文件MD5："+getFileMD5(file));
		
//		d343faf9ff4c2372d456409863a49925
//		
		Map<String,String> params = new HashMap<String,String>();
		params.put("ip", "162.16.1.182");		//	  远端IP                       
		params.put("userName", "mbp");             //   用户名                        
		params.put("password", "mbp");             //   密码                         
		params.put("farPathDW", "/mbp/file/mbp/20160212/");            //   远端下载目录                     
		params.put("localPath", "C:\\101\\");            //   存放本地目录                     
		params.put("fileName", "PL0120160212INS000000101006.DAT");             //   文件名(本地文件名和远端文件名保持一致)    
		ftpGetFile(params);
//		ftpPutFile(params);
//		sftpGetFile(params);
//		sftpPutFile(params);
		
		File file1 = new File("c:/101/PL0120160212INS000000101006.DAT");
//		84095c303a7d4b0f3342b89eb7f4c1e8
		System.out.println("远程文件MD5："+getFileMD5(file1));
		
	}
	
	public static final void writeString2File(String str, String fileName, String encode) throws Exception {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (Writer out = new OutputStreamWriter(new FileOutputStream(fileName), encode)) {
            out.write(str);
            out.flush();
        }
    }
}
