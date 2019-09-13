package com.hfepay.commons.base.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Sys;
import com.hfepay.commons.base.lang.Throwables;


/**
 * 文件类型工具类，根据文件头(俗称魔数)进行判断文件是什么类型.
 * 注：不是所有文件都有魔数的，没有订数的文件将不能用来进行检测。
 * @author HonwellHsueh@csdn.net 感谢此位网友提供的代码
 * @author Sam 
 *
 */
public class FileType {

	public static FileType of(String path) {
		return of(new File(path));
	}

	/**
	 * 创建一个文件类型
	 * 
	 * @param File
	 * @return
	 */
	public static FileType of(File file) {
		return of(Streams.fileIn(file));
	}
	
	/**
	 * 创建一个文件类型
	 * @param ins
	 * @return
	 */
	public static FileType of(InputStream ins) {
		if (ins != null) {
			byte[] fileHeader = new byte[10];
			try {
				ins.read(fileHeader, 0, fileHeader.length);
				return of(fileHeader);
			} catch (IOException e) {
				throw Throwables.wrapThrow(e);
			} finally {
				Streams.safeClose(ins);
			}
		}
		return null;
	}

	/**
	 * 创建一个文件类型
	 * @param fileHeader
	 * @return
	 */
	public static FileType of(byte[] fileHeader) {

		if (fileHeader == null || fileHeader.length <= 0) {
			return null;
		}
		
		byte[] fh = fileHeader;
		
		if (fileHeader.length > 10 ) {
			fh = new byte[10];
			System.arraycopy(fileHeader, 0, fh, 0, 10);
		}

		String typeCode  = "";
		String magicCode = "";
		String fileCode  =  bytesToHexString(fh) ;

		Iterator<String> keyIter = FILE_MAGIC_HEADERS.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			if (key.toLowerCase().startsWith(fileCode.toLowerCase())
					|| fileCode.toLowerCase().startsWith(key.toLowerCase())) {
				typeCode = FILE_MAGIC_HEADERS.get(key);
				magicCode = key;
				break;
			}
		}
		return new FileType(typeCode, magicCode);
	}
	
	
	/**
	 * 文件类型，其实就是文件的扩展名
	 */
	private String fileTypeName;

	/**
	 * 文件头信息，俗称魔术码
	 */
	private String fileMagicCode;

	protected FileType(String fileTypeCode, String fileMagicCode) {
		super();
		this.fileTypeName = fileTypeCode;
		this.fileMagicCode = fileMagicCode;
	}

	/**
	 * 文件类型名称，通常是文件扩展名
	 * @return
	 */
	public String getFileTypeName() {
		return fileTypeName;
	}

	/**
	 * 返回文件魔术码
	 * @return
	 */
	public String getFileMagicCode() {
		return fileMagicCode;
	}
	
	/**
	 * 是否图片文件
	 * @return
	 */
	public boolean isImage() {
		return fileTypeName.matches(IMAGE_REGEX);
	}
	
	/**
	 * 是否视频文件
	 * @return
	 */
	public boolean isVideo() {
		return fileTypeName.matches(VIDEO_REGEX);
	}
	
	/**
	 * 是否压缩文件
	 * @return
	 */
	public boolean isCompression() {
		return fileTypeName.matches(COMPRESSION_REGEX);
	}
	
	/**
	 * 是否音频文件
	 * @return
	 */
	public boolean isAudio() {
		return fileTypeName.matches(AUDIO_REGEX);
	}
	
	/**
	 * 是否MS的办公文件，一般泛指word,excel,ppt这些文件
	 * @return
	 */
	public boolean isMSOffice() {
		return fileTypeName.matches(MSOFFICE_REGEX);
	}
	
	public boolean isEXE() {
		return Strings.equals(this.fileTypeName, "exe");
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**
	 * 将文件头进行hex
	 * 
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (null == src || src.length <= 0) {
			return "";
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	private final static String VIDEO_REGEX = "wmv|asf|rm|rmvb|mov|mp4|avi|3gp|mkv|flv|f4v|mpeg|mpg|dat|swf|ani";
	
	private final static String IMAGE_REGEX = "jpg|jpeg|gif|png|pcx|psd|tiff|bmp|ico|ppm|tif";
	
	private final static String COMPRESSION_REGEX = "zip|gz|7z|rar|bz|bz2|cab|gzip|jar|war|zipx|zz";
	
	private final static String AUDIO_REGEX = "wav|mp3|m4a|aac|m4p|midi";
	
	private final static String MSOFFICE_REGEX = "doc|docx";

	private final static Map<String, String> FILE_MAGIC_HEADERS = Maps.newMap();

	static {
		FILE_MAGIC_HEADERS.put("ffd8ff", "jpg");
		FILE_MAGIC_HEADERS.put("89504e47", "png");
		FILE_MAGIC_HEADERS.put("47494638", "gif");
		FILE_MAGIC_HEADERS.put("49492a00", "tif");
		FILE_MAGIC_HEADERS.put("424d", "bmp"); // 16色位图(bmp)		
		FILE_MAGIC_HEADERS.put("41433130313500000000", "dwg");
		FILE_MAGIC_HEADERS.put("3c21444f435459504520", "html");
		FILE_MAGIC_HEADERS.put("3c21646f637479706520", "htm");
		FILE_MAGIC_HEADERS.put("48544d4c207b0d0a0942", "css");
		FILE_MAGIC_HEADERS.put("696b2e71623d696b2e71", "js");
		FILE_MAGIC_HEADERS.put("7b5c727466", "rtf");
		FILE_MAGIC_HEADERS.put("38425053", "psd");
		FILE_MAGIC_HEADERS.put("46726f6d3a203d3f6762", "eml");
		FILE_MAGIC_HEADERS.put("d0cf11e0a1b11ae10000", "doc"); // MS
																// Excel、Word、Msi
		FILE_MAGIC_HEADERS.put("d0cf11e0a1b11ae10000", "vsd");
		FILE_MAGIC_HEADERS.put("5374616E64617264204A", "mdb");
		FILE_MAGIC_HEADERS.put("255044462d312e", "pdf");
		FILE_MAGIC_HEADERS.put("2e524d46000000120001", "rmvb"); // rmvb、rm
		FILE_MAGIC_HEADERS.put("464c5601050000000900", "flv"); // flv、f4v
		FILE_MAGIC_HEADERS.put("00000020667479706d70", "mp4");
		FILE_MAGIC_HEADERS.put("49443303000000002176", "mp3");
		FILE_MAGIC_HEADERS.put("000001ba210001000180", "mpg");
		FILE_MAGIC_HEADERS.put("3026b2758e66cf11a6d9", "wmv"); // wmv、asf
		FILE_MAGIC_HEADERS.put("52494646e27807005741", "wav");
		FILE_MAGIC_HEADERS.put("52494646d07d60074156", "avi");
		FILE_MAGIC_HEADERS.put("4d546864000000060001", "mid");
		FILE_MAGIC_HEADERS.put("504b0304", "zip");
		FILE_MAGIC_HEADERS.put("526172211", "rar");
		FILE_MAGIC_HEADERS.put("235468697320636f6e66", "ini");
		FILE_MAGIC_HEADERS.put("504b03040a0000000000", "jar");
		FILE_MAGIC_HEADERS.put("4d5a9000030000000400", "exe");
		FILE_MAGIC_HEADERS.put("3c25402070616765206c", "jsp");
		FILE_MAGIC_HEADERS.put("4d616e69666573742d56", "mf");
		FILE_MAGIC_HEADERS.put("3c3f786d6c", "xml");
		FILE_MAGIC_HEADERS.put("494e5345525420494e54", "sql");
		FILE_MAGIC_HEADERS.put("7061636b616765207765", "java");
		FILE_MAGIC_HEADERS.put("406563686f206f66660d", "bat");
		FILE_MAGIC_HEADERS.put("1f8b0800000000000000", "gz");
		FILE_MAGIC_HEADERS.put("6c6f67346a2e726f6f74", "properties");
		FILE_MAGIC_HEADERS.put("cafebabe0000002e0041", "class");
		FILE_MAGIC_HEADERS.put("49545346030000006000", "chm");
		FILE_MAGIC_HEADERS.put("04000000010000001300", "mxp");
		FILE_MAGIC_HEADERS.put("504b0304140006000800", "docx");
		FILE_MAGIC_HEADERS.put("d0cf11e0a1b11ae10000", "wps");// WPS(wps、et、dps)
		FILE_MAGIC_HEADERS.put("6431303a637265617465", "torrent");
		FILE_MAGIC_HEADERS.put("6D6F6F76", "mov");
		FILE_MAGIC_HEADERS.put("FF575043", "wpd");
		FILE_MAGIC_HEADERS.put("CFAD12FEC5FD746F", "dbx");
		FILE_MAGIC_HEADERS.put("2142444E", "pst");
		FILE_MAGIC_HEADERS.put("AC9EBD8F", "qdf");
		FILE_MAGIC_HEADERS.put("E3828596", "pwl");
		FILE_MAGIC_HEADERS.put("2E7261FD", "ram");
	}
	
	public static void main(String[] args) throws IOException {
		String path = "c:\\users.properties";
				//"C:\\Users\\Administrator\\Pictures\\images\\btnbg.jpg";
		
		FileType ft = FileType.of(Streams.readBytesAndClose(Streams.fileIn(path)));
		Sys.println(ft);
	}
}
