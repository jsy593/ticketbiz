package com.jsy.util.common;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 把多个文件或文件夹打包到一个ZIP
 * 
 * @author Jesun
 * 
 */
public class ZipUtil {

	/**
	 * 创建ZIP文件
	 * 
	 * @param sourcePath
	 *            文件或文件夹路径
	 * @param zipPath
	 *            生成的zip文件存在路径（包括文件名）
	 */
	public void createZip(String sourcePath, String zipPath) {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(zipPath);
			zos = new ZipOutputStream(fos);
			this.writeZip(new File(sourcePath), "", zos);
		} catch (FileNotFoundException e) {

		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {

			}

		}
	}

	private void writeZip(File file, String parentPath, ZipOutputStream zos) {
		if (file.exists()) {
			if (file.isDirectory()) {// 处理文件夹
				parentPath += file.getName() + File.separator;
				File[] files = file.listFiles();
				for (File f : files) {
					this.writeZip(f, parentPath, zos);
				}
			} else {
				FileInputStream fis = null;
				DataInputStream dis = null;
				try {
					fis = new FileInputStream(file);
					dis = new DataInputStream(new BufferedInputStream(fis));
					ZipEntry ze = new ZipEntry(parentPath + file.getName());
					zos.putNextEntry(ze);
					byte[] content = new byte[1024];
					int len;
					while ((len = fis.read(content)) != -1) {
						zos.write(content, 0, len);
						zos.flush();
					}

				} catch (FileNotFoundException e) {

				} catch (IOException e) {
				} finally {
					try {
						if (dis != null) {
							dis.close();
						}
					} catch (IOException e) {

					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ZipUtil zu = new ZipUtil();
		zu.createZip("E:\\test.xml", "e:\\mytest.zip");

	}
}
