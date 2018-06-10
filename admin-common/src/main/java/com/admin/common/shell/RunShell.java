package com.admin.common.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用shell脚本
 * 
 * @author xiamh
 *
 */
public class RunShell {
	
	public static Logger logger = LoggerFactory.getLogger(RunShell.class);

	/**
	 * 调用shell脚本程序
	 * @param shellPath
	 * @return 返回－1表示脚本执行失败，否则返回脚本执行结果
	 */
	public static String runShell(String... cmd) {
		
		Process ps;
		try {
			ps = Runtime.getRuntime().exec(cmd);
			int ret = ps.waitFor();
			if(ret != 0) {
				logger.error("#####EXCEPTION##### 脚本执行失败");
				return "-1";
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String result = sb.toString();
			logger.info("********** 脚本/命令[{}]执行结果:\n[\n{}]",cmd,result);
			
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("#####EXCEPTION##### shell命令/脚本[{}]执行失败：{}",cmd,e.getMessage());
			return "-1";
		}
	}
	
	/**
	 * 脚本权限修改
	 * @param scriptPath
	 * @return
	 */
	public static boolean chmodModifly(String scriptPath) {
		//解决脚本没有执行权限
		ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755",scriptPath);
		Process process;
		try {
			process = builder.start();
			process.waitFor();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String[] comms = new String[] {"ls","/Users/xiamh/Downloads"};
		String[] commons = new String[] {"cp","/Users/xiamh/Desktop/t_user.sql","/Users/xiamh/Desktop/t_user_tmp.sql"};
		String[] commonss = new String[] {"ls /Users/xiamh/Downloads"};
		
//		String result = RunShell.runShell(commons);
//		String result = RunShell.runShell("/Users/xiamh/work/workspace98/kpmdp/kpmdp-sta/src/main/resources/shell/mysql_load.sh");
//		String result = RunShell.runShell("/Users/xiamh/work/workspace98/kpmdp/kpmdp-sta/src/main/resources/shell/mysql_load.sh");
//		System.out.println(result);
//		System.out.println(System.currentTimeMillis() - start);
		
		
		Process ps;
		try {
			ps = Runtime.getRuntime().exec(new String[] {"ping","www.baidu.com"});
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			
			int i = 0;
			while ((line = br.readLine()) != null) {
				if(i > 10) {
					ps.destroy();
//					ps.destroyForcibly();
					break;
				}
				
//				sb.append(line).append("\n");
				System.out.println(line);
				i++;
				
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
