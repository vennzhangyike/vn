package com.hfepay.pay.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUtil {

	public static boolean writeFile(byte[] buffer, String folderPath, String fileName) {
		boolean writeSucc = false;

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		System.out.println(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}
}
