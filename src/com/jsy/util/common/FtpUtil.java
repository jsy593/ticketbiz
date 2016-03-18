package com.jsy.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.jsy.develop.web.CachePool;

/**
 * FTP 上传下载
 * @author	schoff
 */
public class FtpUtil {
	private static Log log = LogFactory.getLog(FtpUtil.class);
	
	private static  String FTP_IP = null; // ip地址
	private static  Integer FTP_PORT = null; // 端口
	private static  String FTP_USERNAME = null; // 用户名
	private static  String FTP_PASSWORD = null; // 密码
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE; // 文件类型
	
	private static FTPClient ftpClient = null;
	private OutputStream outputStream = null;
	private InputStream inputStream = null;
	
	{
		FTP_IP=CachePool.baseProp.get("ftpIp");
		FTP_PORT=Integer.parseInt(CachePool.baseProp.get("ftpPort"));
		FTP_USERNAME=CachePool.baseProp.get("ftpUserName");
		FTP_PASSWORD=CachePool.baseProp.get("ftpPassword");
	}
	
	
	
	/**
	 * 连接服务器
	 * @author	schoff
	 * @return boolean
	 */
	public boolean connectServer() {
		ftpClient = new FTPClient();
		try {
			log.debug("正在连接。。。");
			ftpClient.connect(FTP_IP, FTP_PORT);
			if (ftpClient.login(FTP_USERNAME, FTP_PASSWORD)) {
				/*设置编码格式*/
				//ftpClient.setControlEncoding("UTF-8");
				FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
				conf.setServerLanguageCode("zh");
				/*设置连接时间(毫秒数)*/
				ftpClient.setConnectTimeout(3600000);
				/*设置数据超时(毫秒数)*/
				ftpClient.setDataTimeout(3600000);
				ftpClient.setFileType(BINARY_FILE_TYPE); 
				log.debug("登陆成功");
				return true;
			}else {
				log.info("登陆失败，用户名密码错误");
				return false;
			}
		} catch (IOException e) {
			log.error("登录FTP服务器[" + FTP_IP + "]失败，FTP服务器无法打开！");
		}
		return false;
	}
	
	/**
	 * 断开连接
	 * @author	schoff
	 * @return boolean
	 */
	public boolean closeServer() {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (ftpClient != null) {
				ftpClient.logout(); // 用户退出
				ftpClient.disconnect(); // 关闭FTP连接
			}
			log.debug("已从服务断开");
			return true;
		} catch (IOException e) {
			log.error("连接关闭异常");
		}
		return false;
	}
	
	/**
	 * 进入指定目录
	 * @author	schoff
	 * @param path 指定目录
	 * @return boolean
	 */
	public boolean changDirectory(String path) {
		boolean flag;
		try {
			flag = ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			flag = false;
			log.error("目录[" + path + "]目录不存在");
		}
		return flag; 
	}
	
	/**
	 * 创建目录
	 * @author	schoff
	 * @param path 目录名称,支持不存在目录结构
	 * @return boolean
	 */
	public boolean createDirectory(String path) {
		boolean flag = false;
		String beforPath = getDirectory();
		String dirctory = path.substring(0, path.lastIndexOf("/")+1); // 去除最后的文件
		if (!changDirectory(dirctory)) {
			String temp = "";
			for (String str : dirctory.split("/")) {
				temp += str + "/";
				if (!changDirectory(temp)) {
					try {
						flag = ftpClient.makeDirectory(temp);
						if (!flag) {
							log.debug("目录[" + path + "]创建失败");
							break;
						}
					} catch (IOException e) {
						log.error("目录[" + path + "]创建失败");
					}
				}
			}
		}
		/**
		 * 返回原路径
		 */
		changDirectory(beforPath);
		return flag;
	}
	
	/**
	 * 删除目录
	 * @author	schoff
	 * @param pathName 目录名称
	 * @return boolean
	 */
	public boolean deleteDirectory(String pathName) {
		boolean flag = false;
		try {
			flag = ftpClient.removeDirectory(pathName);
			if (!flag) {
				log.debug("目录[" + pathName + "]删除失败");
			}
		} catch (IOException e) {
			log.error("目录[" + pathName + "]删除失败");
		}
		return flag;
	}
	
	/**
	 * 删除目录和文件
	 * @author	schoff
	 * @param path 目录或者文件名称
	 * @return boolean
	 */
	public boolean deleteDirectory(String path,boolean isAll) {
		if (!isAll) {
			return deleteDirectory(path);
		}
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
			if (ftpFiles == null || ftpFiles.length == 0) {
				return deleteDirectory(path);
			}
		} catch (IOException e1) {
			log.error("获取[" + path + "]下文件列表失败");
			e1.printStackTrace();
		}
		
		for (FTPFile ftpFile : ftpFiles) {
			String name = ftpFile.getName();
			if (ftpFile.isFile()) {
                deleteFile(path + "/" + name);
            } else if (ftpFile.isDirectory()) {
            	// 循环调用
            	deleteDirectory(path + "/" + name, true);  
            } else if (ftpFile.isSymbolicLink()) {
  
            } else if (ftpFile.isUnknown()) {
  
            }  
		}
		
		/**
		 * 如果是目录则删除目录，文件已被删除
		 */
		return deleteDirectory(path);
	}
	
	/**
	 * 检查目录是否存在
	 * @author	schoff
	 * @param path 目录
	 * @return boolean
	 */
	public boolean isDirExist(String path) {
		boolean flag = false;
		String beforPath = getDirectory();
		try {
			flag = ftpClient.changeWorkingDirectory(path);
			/**
			 * 返回测试之前的目录
			 */
			changDirectory(beforPath);
		} catch (IOException e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 得到目录下的文件不包含文件夹
	 * @author	schoff
	 * @param path 目录路径
	 * @return List<String>
	 */
	public List<String> getFileList(String path) {
		if (!isDirExist(path)) {
			return new ArrayList<String>();
		}
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			log.error("目录名格式错误");
		}
		List<String> list = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return list;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				list.add(ftpFile.getName());
			}
		}
		return list;
	}
	
	/**
	 * 删除服务器上文件
	 * @author	schoff
	 * @param pathName 文件名字
	 * @return boolean
	 */
	public boolean deleteFile(String pathName) {
		boolean flag = false;
		try {
			flag = ftpClient.deleteFile(pathName);
			if (!flag) {
				log.info("文件[ " + pathName + "]不存在");
			}
		} catch (IOException e1) {
			flag = false;
			log.error("文件[" + pathName + "]删除失败");
		}
		return flag;
	}
	
	/**
	 * 获取当前目录
	 * @author	schoff
	 * @return String
	 */
	public String getDirectory() {
		try {
			return ftpClient.printWorkingDirectory();
		} catch (IOException e) {
			log.error("获取当前目录失败");
		}
		return "";
	}
	
	/**
	 * 文件上传
	 * @author	schoff
	 * @param file 需要上传的文件
	 * @param remoteFile 服务器文件绝对地址[必须指定文件名]
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean uploadFile(File file, String remoteFile) {
		if (!file.exists()) {
			log.error("文件[" + file.getAbsolutePath() + "]不存在");
			return false;
		}
		
		/**
		 * 创建需要的目录
		 */
		String remoteName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
		if (!changDirectory(remoteFile.replace(remoteName, ""))) {
			if (!createDirectory(remoteFile.replace(remoteName, ""))) {
				return false;
			}
		}
		
		/**
		 * 显示进度的上传
		 */
		long step = file.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		
		/**
		 * 写入信息流
		 */
		try {
			inputStream = new FileInputStream(file);
			remoteFile = new String(remoteFile.getBytes("GBK"),"ISO-8859-1");
			outputStream = ftpClient.appendFileStream(remoteFile);
			step = step == 0 ? 1 : step; // 防止除0错误
			byte[] bs = new byte[1024];
			int c;
			while ((c = inputStream.read(bs)) != -1) {
				outputStream.write(bs, 0, c);
				localreadbytes += c;
	            if (localreadbytes / step != process) {
	                process = localreadbytes / step;
	                if (process > 0 && process % 10 == 0) {
	                    if (process == 100) {
	                        log.debug(process + "%");
	                        log.debug(" 上传进度:" + process + "%");
	                    } else{
	                    	log.debug(process + "%");
	                    }
	                }
	            }
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
			boolean result = ftpClient.completePendingCommand();
			if (result) {
				log.info("文件[" + file.getAbsolutePath() + "]上传完成");
				return true;
			} else {
				log.error("文件[" + file.getAbsolutePath() + "]上传失败");
				return false;
			}
		} catch (FileNotFoundException e) {
			log.error("文件[" + file.getAbsolutePath() + "]未找到");
			return false;
		} catch (UnsupportedEncodingException e) {
			log.error("字符编码转换错误");
			return false;
		} catch (IOException e) {
			log.error("文件流写入错误");
			return false;
		}
	}
	
	/**
	 * 文件下载
	 * @author	schoff
	 * @param remoteFile 下载的文件绝对地址
	 * @param loacalFile 本地文件绝对地址
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean download(String remoteFile, String loacalFile) throws IOException {
		if ("/".equals(loacalFile.substring(loacalFile.length()-1))) {
			log.error("本地文件未命名");
			return false;
		}
		/**
		 * 创建本地需要目录
		 */
		String loacalFileName = loacalFile.substring(loacalFile.lastIndexOf("/")+1);
		File fileDir = new  File(loacalFile.replace(loacalFileName, ""));
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		/**
		 * 查看服务器文件是否存在
		 */
		String remoteFileName = remoteFile.substring(remoteFile.lastIndexOf("/")+1);
		if (!changDirectory(remoteFile.replace(remoteFileName, ""))){
			log.info("文件[" + remoteFile + "]不存在");
			return false;
		}
		
		/**
		 * 写入信息流
		 */
		try {
			String temp = new String(remoteFile.getBytes("GBK"),"ISO-8859-1");
			inputStream = ftpClient.retrieveFileStream(temp);
			outputStream = new FileOutputStream(loacalFile);
			byte[] bs = new byte[1024];
			int c;
			while ((c = inputStream.read(bs)) != -1) {
				outputStream.write(bs, 0, c);
				/*if (localreadbytes / step != process) {
					process = localreadbytes / step;
					if (process > 0 && process % 10 == 0) {
						if (process == 100) {
							log.debug(process + "%");
	                        log.debug(" 下载进度:" + process + "%");
						}else {
							log.debug(process + "%");
						}
					}
				}*/
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
			boolean result = ftpClient.completePendingCommand();
			if (result) {
				log.info("文件[" + remoteFile + "]下载完成");
				return true;
			} else {
				log.error("文件[" + remoteFile + "]下载失败");
				return false;
			}
		} catch (FileNotFoundException e) {
			log.error("文件[" + remoteFile+ "] 或者 [" + loacalFile + "]未找到");
			return false;
		} catch (UnsupportedEncodingException e) {
			log.error("字符编码转换错误");
			return false;
		} catch (IOException e) {
			log.error("文件流写入错误");
			return false;
		}
		
	}
	
	/**
	 * 使用默认传输
	 * 
	 * @return
	 */
	public static int connectFtp() {
		int reply = 0;
		
		
		try {
			
			if (!(CommonUtil.isEmpty(FTP_IP) && CommonUtil.isEmpty(FTP_PORT) 
					&& CommonUtil.isEmpty(FTP_USERNAME)&& CommonUtil.isEmpty(FTP_PASSWORD))) {
				ftpClient = new FTPClient();
				ftpClient.setControlEncoding("utf-8");// 避免中文乱码,gbk不通过
				ftpClient.connect(FTP_IP, FTP_PORT);
				ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
				
				reply = ftpClient.getReplyCode();
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 二進制传输，避免文件失真
			}else{
				log.error("字典表中没有配置，请注意查看");
			}
		} catch (Exception e) {
			log.error("连接FTP失败", e);
		} finally {

		}

		return reply;
	}
	
	
	/**
	 * 创建目录
	 * 
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public static boolean createDirecroty(String directory) throws IOException {
		boolean success = true;
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory))) {

			if (directory.startsWith("/")) {
				directory = directory.substring(1, directory.length());
			}

			if (directory.endsWith("/")) {
				directory = directory.substring(0, directory.length() - 1);
			}

			String[] dirArr = directory.split("/");

			int dirArrLen = dirArr.length;

			for (int i = 0; i < dirArrLen; i++) {
				String currDir = dirArr[i];
				// 切换
				boolean changeWorkingDirectory = ftpClient
						.changeWorkingDirectory(currDir);
				if (!changeWorkingDirectory) {
					boolean makeDirectory = ftpClient.makeDirectory(currDir);
					if (makeDirectory) {
						ftpClient.changeWorkingDirectory(currDir);
					} else {
						log.error("创建目录失败");
						success = false;
						return success;
					}
				}
			}
		}
		return success;
	}
	
	/**
	 * 该方法对图片的压缩已经注释
	 * 
	 * @param url
	 * @param is
	 * @return
	 */
	public static boolean uploadSingleImage(String url, InputStream is) {
		String filedir = url.substring(0, url.lastIndexOf("/"));
		String filename = url.substring(url.lastIndexOf("/") + 1);

		boolean flag = false;
		int reply = connectFtp();
		OutputStream os = null;
		InputStream is1 = null;
		File tempfile = null;
		File tempimg = null;
		try {
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
			} else {
				boolean createDirecroty = createDirecroty(filedir);
				if (!createDirecroty) {
					log.error("FTP创建多层文件夹失败,文件将被上传到根目录下["
							+ filedir
							+ "]"
							+ DateUtil.date2String(new Date(),
									DateUtil.YYYY_MM_DD_HH_MM_SS));
				}
				ftpClient.changeWorkingDirectory(filedir);
			}

			// 临时路径
			String path = FtpUtil.class.getClassLoader().getResource("/")
					.getPath();
			String decodePath = URLDecoder.decode(path, "utf-8");
			int indexOf = decodePath.indexOf("/classes");
			String tmpPath = decodePath.substring(0, indexOf);
			tmpPath += File.separator + "res" + File.separator + "ftptemp";

			tempfile = new File(tmpPath + File.separator + filename);

			// boolean mkdirs = tempfile.mkdirs();
			File parentFile = tempfile.getParentFile();
			if (!parentFile.exists()) {
				boolean mkdirs = parentFile.mkdirs();
				if (!mkdirs) {
					log.error("创建FTP临时目录失败,可能没有权限");
				}
			}

			if (!tempfile.exists()) {
				tempfile.createNewFile();
			}

			os = new FileOutputStream(tempfile);
			if (is != null && os != null) {
				byte[] b = new byte[1024];
				while ((is.read(b)) > 0) {
					os.write(b);
				}
			}

			tempimg = new File(tmpPath + File.separator + filename);
			is1 = new FileInputStream(tempimg);
			flag = ftpClient.storeFile(filename, is1);
		} catch (Exception e) {
			log.error("上传文件失败" + FtpUtil.class.getName(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}

				if (os != null) {
					os.close();
				}

				if (is1 != null) {
					is1.close();
				}

			} catch (IOException e) {
				log.error("清除FTP上传IO失败");
			}
			tempfile.delete();
			tempimg.delete();
		}

		return flag;
	}
	
	public static void main(String[] args) throws IOException {
		FtpUtil ftpUtil = new FtpUtil();
		boolean flag = false;
		if(ftpUtil.connectServer()){
			String loacalFile = "F:/s/副本.rar";
			String remoteFile = "/upload/s/丰富的.rar";
			File file = new File("F:/副本.rar");
			flag = ftpUtil.uploadFile(file, remoteFile);
			flag = ftpUtil.download(remoteFile, loacalFile);
		}
		System.out.println("-------------"+flag);
	}
	
}
