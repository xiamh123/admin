package com.admin.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.admin.common.shell.Result;
import com.admin.common.shell.Shell;

/**
 * 封装调用shell脚本工具类
 * @author xiamh
 *
 */
public class ShellUtils {
	
	static Logger logger = LoggerFactory.getLogger(ShellUtils.class);
	
	/**
	 * 执行shell脚本
	 * @param cmd 	需要执行的操作系统命令（如cp、pwd、sh等）
	 * @param cmds	相关参数
	 * @return	
	 */
	public static Result exec(String cmd,String... cmds) {
			try {
				return Shell.build(cmd, cmds)
				    .onProcess((line, helper) -> {	//打印执行日志
				    		logger.info(line);
				    		
				    })
				    .onError((line,helper)->{		//打印错误日志
				    		logger.error(line);
				    })
				    .exec();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	/**
	 * 统计文件行数
	 * @param cmd
	 * @param cmds
	 * @return
	 */
	public static Result totalFileNum(String filePathName) {
		String[] str = new String[1];
		String filePath = "";
		try {
			filePath = ResourceUtils.getFile("classpath:shell/wc.sh").getPath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			Result result = Shell.build("sh",filePath,filePathName)
				    .onProcess((line, helper) -> {	//打印执行日志
				    		logger.info(line);
				    		str[0] = line;
				    })
				    .onError((line,helper)->{		//打印错误日志
				    		logger.error(line);
				    })
				    .exec();
			result.setRetmsg(str[0]);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 按行切割文件
	 * @param splitFilePathName
	 * @param num
	 * @param fileFormat
	 * @return
	 */
	public static Result splitFile (String splitFilePathName,String num,String fileFormat) {

		String filePath = "";
		try {
			filePath = ResourceUtils.getFile("classpath:shell/split.sh").getPath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			Result result = Shell.build("sh",filePath,splitFilePathName,num,fileFormat)
					.onProcess((line, helper) -> {	//打印执行日志
						logger.info(line);
					})
					.onError((line,helper)->{		//打印错误日志
						logger.error(line);
					})
					.exec();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	public static void main(String[] args) throws IOException {
		
//		/Users/xiamh/夏苗华/绩效－河北农信数据/ba_ext_branch_xref20180104
//		System.out.println(ShellUtils.exec("wc","-l", "/Users/xiamh/夏苗华/绩效－河北农信数据/ba_ext_branch_xref20180104"));
		String[] str = new String[1];
		
//		Result result = Shell.build("wc","-l", "/Users/xiamh/夏苗华/绩效－河北农信数据/ba_ext_branch_xref20180104","|","awk","{print","$1}")
//	    .onProcess((line, helper) -> {	//打印执行日志
//	    		logger.info(line);
//	    		str[0] = line;
//	    })
//	    .onError((line,helper)->{		//打印错误日志
//	    		logger.error(line);
//	    })
//	    .exec();
//		
////		| awk '{print $1}'
//		
//		System.out.println(result);
//		System.out.println(str[0]);
		
//		String[] str = new String[1];
//		String filePath = ResourceUtils.getFile("classpath:shell/wc.sh").getPath();
//		Result result = Shell.build("sh",filePath,"/Users/xiamh/夏苗华/绩效－河北农信数据/ba_ext_branch_xref20180104")
//			    .onProcess((line, helper) -> {	//打印执行日志
//			    		logger.info(line);
//			    		str[0] = line;
//			    })
//			    .onError((line,helper)->{		//打印错误日志
//			    		logger.error(line);
//			    })
//			    .exec();
//		System.out.println(result);
//		System.out.println(str[0]);


		String filePath = ResourceUtils.getFile("classpath:shell/split.sh").getPath();
		Result result = Shell.build("sh",filePath,"/Users/xiamh/夏苗华/绩效－河北农信数据/tmp/20180104/ln_arrears_table20180104","1000000","ln_arrears_table20180104_")
				.onProcess((line, helper) -> {	//打印执行日志
					logger.info(line);
				})
				.onError((line,helper)->{		//打印错误日志
					logger.error(line);
				})
				.exec();

	
	
	
	}
	
}
