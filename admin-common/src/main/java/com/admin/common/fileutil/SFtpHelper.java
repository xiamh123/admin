package com.admin.common.fileutil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP
 * @author xiamh
 *
 */
public class SFtpHelper {
	
	
	private static Logger log = LoggerFactory.getLogger(SFtpHelper.class);
	
	/**
	 * 连接sftp服务器
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public static ChannelSftp connect(String ip, int port, String username, String password, String path) throws Exception {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			log.debug("连接FTP服务器：ip='" + ip + "',port='" + port + "'");
			jsch.getSession(username, ip, port);
			Session sshSession = jsch.getSession(username, ip, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			sftp.cd(path);
		} catch (Exception e) {
			log.error("连接SFTP服务器发生异常。ip='" + ip + "',port='" + port + "'", e);
			try {
				if (sftp != null && sftp.isConnected()) {
					sftp.disconnect();
					sftp = null;
				}
			} catch (Exception ex) {
				throw new Exception("连接SFTP服务器发生异常。ip='" + ip + "',port='" + port + "'", e);
			}
			throw new Exception(e.getMessage());
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public static void upload(ChannelSftp sftp, String directory, String uploadFile) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			log.error("上传文件发生异常,{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 * @throws SftpException 
	 * @throws FileNotFoundException 
	 */
	public static void download(ChannelSftp sftp, String downloadFile, File saveFile) throws FileNotFoundException, SftpException {
		long start = System.currentTimeMillis();
//		try {
			sftp.get(downloadFile, new FileOutputStream(saveFile));
//		} catch (Exception e) {
//			log.error("下载文件发生异常,{}", e.getMessage());
//			e.printStackTrace();
//		}
		log.info("********** 文件{}下载耗时{}: ",downloadFile,String.valueOf(System.currentTimeMillis() - start));
	}
	
	/**
	 * 服务器上指定文件是否存在
	 * @param sftp			
	 * @param directory		目录
	 * @param fileName		文件名
	 * @return	true-存在／false-不存在
	 */
	@SuppressWarnings("unchecked")
	public static boolean isFileExist(ChannelSftp sftp, String directory, final String fileName) {
		Vector<LsEntry> enties;
		boolean isExist = false;
		try {
			enties = sftp.ls(directory);
			for(LsEntry entry : enties) {
				if(entry.getFilename().equals(fileName)) {
					isExist = true;
				}
			}
			return isExist;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取服务器上指定路径的所有文件名
	 * @param sftp
	 * @param directory
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getFileList(ChannelSftp sftp, String directory){
		List<String> fileList = new LinkedList<String>();
		
		Vector<LsEntry> enties;
		try {
			enties = sftp.ls(directory);
			for(LsEntry entry : enties) {
				fileList.add(entry.getFilename());
			}
			return fileList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 断开连接
	 * @param sftp
	 */
	public static void disconnect(ChannelSftp sftp){
		if(null != sftp) {
			sftp.disconnect();
			try {
				if(null != sftp.getSession()) {
					sftp.getSession().disconnect();
				}
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		
		ChannelSftp sftp = SFtpHelper.connect("10.7.2.3", 22, "root", "Kayak2003", "/home/kpmdp/ljh/data/");
		
//		System.out.println(SFtpHelper.getFileList(sftp, "/home/dev/sta-test/").toString());
		
//		System.out.println(SFtpHelper.isFileExist(sftp, "/home/dev/sta-test/", "test.txt"));
		
		//java代码
		//无线上传：389M，274132ms －> 274s －> 4.5min
		//有线上传：389M，36156ms －> 36s －> 0.5min
		long start = System.currentTimeMillis();
//		SFtpHelper.upload(sftp, "/home/dev/sta-test/", "/Users/xiamh/Desktop/netty.mp4");
//		System.out.println(System.currentTimeMillis() - start);
		
		//java代码
		//无线下载：389M，299726ms －> 300s －> 5min
		//有限下载：389M，38303ms －> 38s －> 0.5min
		//有限下载：1.48G，168719ms －> 168s －> 3min
//		start = System.currentTimeMillis();
		SFtpHelper.download(sftp, "deposit2000.txt", new File("/Users/xiamh/Desktop/load/deposit2000.txt"));
		System.out.println(System.currentTimeMillis() - start);
		
		//直接使用命令连接有线上传：389M，360001ms －> 36s －> 0.5min，基本上与java代码下载速度一致
		//直接使用命令连接有线下载：389M，38080ms －> 38s －> 0.5min，基本上与java代码下载速度一致
		
		//使用shell脚本调用sftp需要额外安装lftp，之前中移动是使用lua脚本使用sftp
		
		disconnect(sftp);
		
	}
	
}