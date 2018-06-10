package com.admin.common.fileutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP工具类
 * @author xiamh
 *
 */
public class FtpHelper {
	
	private static Logger log = LoggerFactory.getLogger(FtpHelper.class);
	
	/**
	 * 上传文件
	 * @param client
	 * @param fileName
	 * @param inputStream
	 * @return	true－上传成功／false－上传失败
	 */
	public static boolean putFile(FTPClient client, String fileName, InputStream inputStream) {
		boolean flag = false;
		if (client == null) {
			log.error("FTP连接为空，上传失败。");
			return false;
		}
		if (inputStream == null) {
			log.error("上传文件的内容为空，上传失败。");
			return false;
		}
		if (fileName == null || fileName.isEmpty()) {
			log.error("指定的上传文件名为空，上传失败。");
			return false;
		}
//		InputStream in = null;
		OutputStream out = null;
		try {
			log.debug("向FTP服务器上传文件：'" + fileName );
//			in = new FileInputStream(file);
			client.setFileType(FTP.BINARY_FILE_TYPE);	//二进制上传文件
			out = client.storeFileStream(fileName);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, c);
			}
			out.close();
			boolean isDo = client.completePendingCommand();
			if (!isDo) {
				log.error("FTP文件上传未完成，返回失败。");
				return false;
			}
			log.info("FTP文件上传完成：" + fileName );
			flag = true;
		} catch (Exception e) {
			log.error(
					"上传文件时发生异常，FTP文件名：'" + fileName , e);
			return false;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				//忽略关闭异常
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				//忽略关闭异常
			}
		}
		return flag;
	}
	
	/**
	 * 下载文件
	 * @param client
	 * @param recLocalPath
	 * @param fileName
	 * @return	true－下载成功，false－下载失败
	 */
	public static boolean getFile(FTPClient client, String recLocalPath, String fileName) {
		boolean flag = false;
		if (client == null) {
			log.error("FTP连接为空，下载失败。");
			return false;
		}
		if (fileName == null || fileName.isEmpty()) {
			log.error("指定的下载文件名为空，下载失败。");
			return false;
		}
		
		try {
			
			log.info("下载 [{}] 文件开始...");
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean isFileExist = FtpHelper.isFileExist(client, ".", fileName);
			if(isFileExist) {
				File file = new File(recLocalPath);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				try(FileOutputStream fos = new FileOutputStream(file)){
					client.retrieveFile(fileName, fos);
				}
				log.info("下载 [{}] 文件成功.");
				flag = true;
			} else {
				log.error("下载 [{}] 文件不存在!");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error("下载失败，错误信息：{}",e.getMessage());
			
		} finally {
			if(client != null) {
				try {
					client.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 创建一个FTP连接，上传、下载文件前先创建连接
	 * @param ip
	 * @param port
	 * @param user
	 * @param pwd
	 * @param path
	 * @return
	 */
	public static FTPClient createFTPClient(String ip, int port,
			String user, String pwd, String path) {
		FTPClient client = null;
		try {
			// 创建FTP连接
			client = FtpHelper.getFtpClient(ip, port, user,
					pwd, path);
		} catch (Exception e) {
			log.error("获取FTP连接失败。", e);
		}
		return client;
	}

	/**
	 * 获取一个FTP连接
	 * @param ip
	 * @param port
	 * @param user
	 * @param pwd
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static FTPClient getFtpClient(String ip, int port, String user, String pwd, String path) throws Exception {
		FTPClient client = null;
		try {
			client = new FTPClient();		
			client.connect(ip, port);
			log.debug("连接FTP服务器：ip='" + ip + "',port='" + port + "'");
			System.out.println("连接FTP服务器：ip='" + ip + "',port='" + port + "'");
			int reply = client.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				log.error("FTP服务器拒绝连接");
				throw new Exception("FTP服务器拒绝连接");
			}
			boolean isLogin = client.login(user, pwd);
			boolean isDirExists = client.makeDirectory(path);
			if(isDirExists){
				log.info("路径不存在，创建路径成功："+path);
			}else{
				log.info("路径已存在，不用创建！");
			}
			boolean isChangPath = client.changeWorkingDirectory(path);

			if (!isLogin) {
				log.warn("FTP服务器登录失败。ip='" + ip + "',user='" + user
						+ "',password='" + pwd + "'");
				client.disconnect();
				throw new Exception("FTP服务器登录失败。ip='" + ip + "',user='" + user
						+ "',password='" + pwd + "'");
			}
			if (!isChangPath) {
				log.warn("FTP服务器切换工作目录失败。path='" + path + "'");
				client.disconnect();
				throw new Exception("FTP服务器切换工作目录失败。path='" + path + "'");
			}

		} catch (Exception e) {
			log.error("连接FTP服务器发生异常。ip='" + ip + "',port='" + port + "'", e);
			try {
				if (client != null && client.isConnected()) {
					client.disconnect();
					client = null;
				}
			} catch (Exception ex) {
				// 忽略异常
				throw new Exception("连接FTP服务器发生异常。ip='" + ip + "',port='" + port + "'", e);
			}
			throw new Exception(e.getMessage());
		}
		return client;
	}
	
	/**
	 * 判断远程目录文件是否存在
	 * @param client
	 * @param path
	 * @param file
	 * @return	true－存在／false－失败
	 */
	public static boolean isFileExist(FTPClient client, String path, final String filename) {
		FTPFile[] files;
		try {
			files = client.listFiles(path, new FTPFileFilter() {
				@Override
				public boolean accept(FTPFile arg0) {
					return arg0.getName().equals(filename);
				}
			});
			if(files.length > 0) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		FTPClient client = FtpHelper.createFTPClient("10.7.2.3", 21, "kpmdp", "kpmdp", "/home/kpmdp/hpeng/hb_ods/data/data/ok");
//		FTPFile[] files = client.listFiles("/home/kpmdp/hpeng/hb_ods/data/data/ok", new FTPFileFilter() {
//				@Override
//				public boolean accept(FTPFile arg0) {
//					return arg0.getName().equals("ba_ccy_code20180104.ok");
//				}
//			});
		FTPFile[] files = client.listFiles();
		System.out.println(files);
	
	}
	

}