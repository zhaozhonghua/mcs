package com.fjsmu.tools;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {

	/**
	 * �ļ�ת��Ϊ�ֽ�����
	 * @param filepath �ļ�·��
	 * @return byte[]
	 */
	public static byte[] fileToBytes(String filePath){ 
		byte[] buffer = null;  
		FileInputStream fis =null;
		ByteArrayOutputStream bos =null;
		try {  
			File file = new File(filePath);
			if(!file.exists())
				return null;
			fis = new FileInputStream(file);  
			bos = new ByteArrayOutputStream(1000);  
			byte[] b = new byte[1024];  
			int n;  
			while ((n = fis.read(b)) != -1) {  
				bos.write(b, 0, n);  
			}  
			buffer = bos.toByteArray();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}finally{
			try {
				if(fis!=null)
					fis.close();  
				if(bos!=null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		return buffer;  
	} 


	/** 
	 * ���byte���飬����ļ� 
	 * @param bfile
	 * @param filePath �ļ�����Ŀ¼
	 * @param fileName �ļ���
	 */
	public static void getFile(byte[] bfile, String filePath,String fileName) {  
		BufferedOutputStream bos = null;  
		FileOutputStream fos = null;  
		File file = null;  
		try {  
			File dir = new File(filePath);  
			if(!dir.exists()&& !dir.isDirectory()){//�ж��ļ�Ŀ¼�Ƿ����  
				dir.mkdirs();  
			}  
			file = new File(filePath+"/"+fileName);  
			fos = new FileOutputStream(file);  
			bos = new BufferedOutputStream(fos);  
			bos.write(bfile);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			if (bos != null) {  
				try {  
					bos.close();  
				} catch (IOException e1) {  
					e1.printStackTrace();  
				}  
			}  
			if (fos != null) {  
				try {  
					fos.close();  
				} catch (IOException e1) {  
					e1.printStackTrace();  
				}  
			}  
		}  
	}

	public static ByteBuffer getByteBufferFromFile(String filePath) throws IOException {
		byte[] bytes = fileToBytes(filePath);
		ByteBuffer byteBuf = ByteBuffer.wrap(bytes, 0, bytes.length);
		return byteBuf;
	}
	
	public static byte[] getByteBuffer2Bytes(ByteBuffer byteBuffer) throws IOException {
		
		return byteBuffer.array();
	}

	/**
	 * ��ȡ��������Ϊ�ļ����ַ�����<br>
	 * @return
	 */
	public static String getFolderByDate() {
		int y, m, d;
		Calendar cal = Calendar.getInstance();
		y = cal.get(Calendar.YEAR);
		m = (cal.get(Calendar.MONTH) + 1);
		d = cal.get(Calendar.DAY_OF_MONTH);
		cal = null;
		String folder = "/" + y + "/" + m + "/" + d + "/";
		return folder;
	}

	/**
	 * ���Ƶ����ļ������Ŀ���ļ����ڣ��򲻸���
	 * @param srcFileName ���Ƶ��ļ���
	 * @param descFileName Ŀ���ļ���
	 * @return ����Ƴɹ����򷵻�true�����򷵻�false
	 */
	public static boolean copyFile(String srcFileName, String descFileName) {
		return FileUtils.copyFileCover(srcFileName, descFileName, false);
	}

	/**
	 * ���Ƶ����ļ�
	 * @param srcFileName ���Ƶ��ļ���
	 * @param descFileName Ŀ���ļ���
	 * @param coverlay ���Ŀ���ļ��Ѵ��ڣ��Ƿ񸲸�
	 * @return ����Ƴɹ����򷵻�true�����򷵻�false
	 */
	public static boolean copyFileCover(String srcFileName,
			String descFileName, boolean coverlay) {
		File srcFile = new File(srcFileName);
		// �ж�Դ�ļ��Ƿ����
		if (!srcFile.exists()) {
			return false;
		}
		// �ж�Դ�ļ��Ƿ��ǺϷ����ļ�
		else if (!srcFile.isFile()) {
			return false;
		}
		File descFile = new File(descFileName);
		// �ж�Ŀ���ļ��Ƿ����
		if (descFile.exists()) {
			// ���Ŀ���ļ����ڣ��������?��
			if (coverlay) {
				if (!FileUtils.delFile(descFileName)) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if (!descFile.getParentFile().exists()) {
				// ���Ŀ���ļ����ڵ�Ŀ¼�����ڣ��򴴽�Ŀ¼
				// ����Ŀ���ļ����ڵ�Ŀ¼
				if (!descFile.getParentFile().mkdirs()) {
					return false;
				}
			}
		}

		// ׼�������ļ�
		// ��ȡ��λ��
		int readByte = 0;
		InputStream ins = null;
		OutputStream outs = null;
		try {
			// ��Դ�ļ�
			ins = new FileInputStream(srcFile);
			// ��Ŀ���ļ��������
			outs = new FileOutputStream(descFile);
			byte[] buf = new byte[1024];
			// һ�ζ�ȡ1024���ֽڣ���readByteΪ-1ʱ��ʾ�ļ��Ѿ���ȡ���
			while ((readByte = ins.read(buf)) != -1) {
				// ����ȡ���ֽ���д�뵽�����
				outs.write(buf, 0, readByte);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			// �ر���������������ȹر��������Ȼ���ٹر�������
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException oute) {
					oute.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ine) {
					ine.printStackTrace();
				}
			}
		}
	}

	/**
	 * �������Ŀ¼�����ݣ����Ŀ��Ŀ¼���ڣ��򲻸���
	 * @param srcDirName ԴĿ¼��
	 * @param descDirName Ŀ��Ŀ¼��
	 * @return ����Ƴɹ�����true�����򷵻�false
	 */
	public static boolean copyDirectory(String srcDirName, String descDirName) {
		return FileUtils.copyDirectoryCover(srcDirName, descDirName,
				false);
	}

	/**
	 * �������Ŀ¼������ 
	 * @param srcDirName ԴĿ¼��
	 * @param descDirName Ŀ��Ŀ¼��
	 * @param coverlay ���Ŀ��Ŀ¼���ڣ��Ƿ񸲸�
	 * @return ����Ƴɹ�����true�����򷵻�false
	 */
	public static boolean copyDirectoryCover(String srcDirName,
			String descDirName, boolean coverlay) {
		File srcDir = new File(srcDirName);
		// �ж�ԴĿ¼�Ƿ����
		if (!srcDir.exists()) {
			return false;
		}
		// �ж�ԴĿ¼�Ƿ���Ŀ¼
		else if (!srcDir.isDirectory()) {
			return false;
		}
		// ���Ŀ���ļ��������ļ��ָ����β���Զ�����ļ��ָ���
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		// ���Ŀ���ļ��д���
		if (descDir.exists()) {
			if (coverlay) {
				// ���?��Ŀ��Ŀ¼
				if (!FileUtils.delFile(descDirNames)) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			// ����Ŀ��Ŀ¼
			if (!descDir.mkdirs()) {
				return false;
			}

		}

		boolean flag = true;
		// �г�ԴĿ¼�µ������ļ������Ŀ¼��
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// �����һ�������ļ�����ֱ�Ӹ���
			if (files[i].isFile()) {
				flag = FileUtils.copyFile(files[i].getAbsolutePath(),
						descDirName + files[i].getName());
				// ����ļ�ʧ�ܣ����˳�ѭ��
				if (!flag) {
					break;
				}
			}
			// �������Ŀ¼���������Ŀ¼
			if (files[i].isDirectory()) {
				flag = FileUtils.copyDirectory(files[i]
						.getAbsolutePath(), descDirName + files[i].getName());
				// ���Ŀ¼ʧ�ܣ����˳�ѭ��
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			return false;
		}
		return true;

	}

	/**
	 * 
	 * ɾ���ļ�������ɾ����ļ����ļ���
	 * 
	 * @param fileName ��ɾ����ļ���
	 * @return ���ɾ��ɹ����򷵻�true�����Ƿ���false
	 */
	public static boolean delFile(String fileName) {
 		File file = new File(fileName);
		if (!file.exists()) {
			return true;
		} else {
			if (file.isFile()) {
				return FileUtils.deleteFile(fileName);
			} else {
				return FileUtils.deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 
	 * ɾ����ļ�
	 * 
	 * @param fileName ��ɾ����ļ���
	 * @return ���ɾ��ɹ����򷵻�true�����򷵻�false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 
	 * ɾ��Ŀ¼��Ŀ¼�µ��ļ�
	 * 
	 * @param dirName ��ɾ���Ŀ¼���ڵ��ļ�·��
	 * @return ���Ŀ¼ɾ��ɹ����򷵻�true�����򷵻�false
	 */
	public static boolean deleteDirectory(String dirName) {
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return true;
		}
		boolean flag = true;
		// �г�ȫ���ļ�����Ŀ¼
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = FileUtils.deleteFile(files[i].getAbsolutePath());
				// ���ɾ���ļ�ʧ�ܣ����˳�ѭ��
				if (!flag) {
					break;
				}
			}
			// ɾ����Ŀ¼
			else if (files[i].isDirectory()) {
				flag = FileUtils.deleteDirectory(files[i]
						.getAbsolutePath());
				// ���ɾ����Ŀ¼ʧ�ܣ����˳�ѭ��
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			return false;
		}
		// ɾ��ǰĿ¼
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * ���������ļ�
	 * @param descFileName �ļ����·��
	 * @return ����ɹ����򷵻�true�����򷵻�false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			return false;
		}
		if (!file.getParentFile().exists()) {
			// ����ļ����ڵ�Ŀ¼�����ڣ��򴴽�Ŀ¼
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}

		// �����ļ�
		try {
			if (file.createNewFile()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ����Ŀ¼
	 * @param descDirName Ŀ¼��,��·��
	 * @return ����ɹ����򷵻�true�����򷵻�false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			return false;
		}
		// ����Ŀ¼
		if (descDir.mkdirs()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * ѹ���ļ���Ŀ¼
	 * @param srcDirName ѹ���ĸ�Ŀ¼
	 * @param fileName ��Ŀ¼�µĴ�ѹ�����ļ�����ļ���������*��""��ʾ��Ŀ¼�µ�ȫ���ļ�
	 * @param descFileName Ŀ��zip�ļ�
	 */
	public static void zipFiles(String srcDirName, String fileName,
			String descFileName) {
		// �ж�Ŀ¼�Ƿ����
		if (srcDirName == null) {
			return;
		}
		File fileDir = new File(srcDirName);
		if (!fileDir.exists() || !fileDir.isDirectory()) {
			return;
		}
		String dirPath = fileDir.getAbsolutePath();
		File descFile = new File(descFileName);
		try {
			ZipOutputStream zouts = new ZipOutputStream(new FileOutputStream(
					descFile));
			if ("*".equals(fileName) || "".equals(fileName)) {
				FileUtils.zipDirectoryToZipFile(dirPath, fileDir, zouts);
			} else {
				File file = new File(fileDir, fileName);
				if (file.isFile()) {
					FileUtils.zipFilesToZipFile(dirPath, file, zouts);
				} else {
					FileUtils
							.zipDirectoryToZipFile(dirPath, file, zouts);
				}
			}
			zouts.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ѹ��ZIP�ļ�����ZIP�ļ�������ݽ�ѹ��descFileNameĿ¼��
	 * @param zipFileName ��Ҫ��ѹ��ZIP�ļ�
	 * @param descFileName Ŀ���ļ�
	 */
	public static boolean unZipFiles(String zipFileName, String descFileName) {
		String descFileNames = descFileName;
		if (!descFileNames.endsWith(File.separator)) {
			descFileNames = descFileNames + File.separator;
		}		
        try {
			// ���ZIP�ļ�����ZipFile����
			ZipFile zipFile = new ZipFile(zipFileName);
			ZipEntry entry = null;
			String entryName = null;
			String descFileDir = null;
			byte[] buf = new byte[4096];
			int readByte = 0;
			// ��ȡZIP�ļ������е�entry
			@SuppressWarnings("rawtypes")
			Enumeration enums = zipFile.entries();
			// ��������entry
			while (enums.hasMoreElements()) {
				entry = (ZipEntry) enums.nextElement();
				// ���entry������
				entryName = entry.getName();
				descFileDir = descFileNames + entryName;
				if (entry.isDirectory()) {
					// ���entry��һ��Ŀ¼���򴴽�Ŀ¼
					new File(descFileDir).mkdirs();
					continue;
				} else {
					// ���entry��һ���ļ����򴴽���Ŀ¼
					new File(descFileDir).getParentFile().mkdirs();
				}
				File file = new File(descFileDir);
				// ���ļ������
				OutputStream os = new FileOutputStream(file);
				// ��ZipFile�����д�entry��������
		        InputStream is = zipFile.getInputStream(entry);
				while ((readByte = is.read(buf)) != -1) {
					os.write(buf, 0, readByte);
				}
				os.close();
				is.close();
			}
			zipFile.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ��Ŀ¼ѹ����ZIP�����
	 * @param dirPath Ŀ¼·��
	 * @param fileDir �ļ���Ϣ
	 * @param zouts �����
	 */
	public static void zipDirectoryToZipFile(String dirPath, File fileDir,
			ZipOutputStream zouts) {
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			// �յ��ļ���
			if (files.length == 0) {
				// Ŀ¼��Ϣ
				ZipEntry entry = new ZipEntry(getEntryName(dirPath, fileDir));
				try {
					zouts.putNextEntry(entry);
					zouts.closeEntry();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					// ������ļ���������ļ�ѹ������
					FileUtils
							.zipFilesToZipFile(dirPath, files[i], zouts);
				} else {
					// �����Ŀ¼����ݹ����
					FileUtils.zipDirectoryToZipFile(dirPath, files[i],
							zouts);
				}
			}

		}

	}

	/**
	 * ���ļ�ѹ����ZIP�����
	 * @param dirPath Ŀ¼·��
	 * @param file �ļ�
	 * @param zouts �����
	 */
	public static void zipFilesToZipFile(String dirPath, File file,
			ZipOutputStream zouts) {
		FileInputStream fin = null;
		ZipEntry entry = null;
		// �������ƻ�����
		byte[] buf = new byte[4096];
		int readByte = 0;
		if (file.isFile()) {
			try {
				// ����һ���ļ�������
				fin = new FileInputStream(file);
				// ����һ��ZipEntry
				entry = new ZipEntry(getEntryName(dirPath, file));
				// �洢��Ϣ��ѹ���ļ�
				zouts.putNextEntry(entry);
				// �����ֽڵ�ѹ���ļ�
				while ((readByte = fin.read(buf)) != -1) {
					zouts.write(buf, 0, readByte);
				}
				zouts.closeEntry();
				fin.close();
				System.out
						.println("����ļ� " + file.getAbsolutePath() + " ��zip�ļ���!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ��ȡ��ѹ���ļ���ZIP�ļ���entry�����֣�������ڸ�Ŀ¼�����·����
	 * @param dirPat Ŀ¼��
	 * @param file entry�ļ���
	 * @return
	 */
	private static String getEntryName(String dirPath, File file) {
		String dirPaths = dirPath;
		if (!dirPaths.endsWith(File.separator)) {
			dirPaths = dirPaths + File.separator;
		}
		String filePath = file.getAbsolutePath();
		// ����Ŀ¼��������entry���ֺ������"/"����ʾ����Ŀ¼��洢
		if (file.isDirectory()) {
			filePath += "/";
		}
		int index = filePath.indexOf(dirPaths);

		return filePath.substring(index + dirPaths.length());
	}
	
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
	
	/**
	 * ��ȡ��������Ϊ�ļ����ַ�����<br>
	 * 
	 * @return
	 */
	public static String getFolderByYearMonth() {
		int y, m, d;
		Calendar cal = Calendar.getInstance();
		y = cal.get(Calendar.YEAR);
		m = (cal.get(Calendar.MONTH) + 1);
		d = cal.get(Calendar.DAY_OF_MONTH);
		cal = null;
		String folder = "/" + y + "/" + m + "/" + d + "/";
		return folder;
	}
	
	/**
	 * ����ȡ�ļ���ǰʱ��+����� ��
	 * @param fileName
	 * @return
	 */
	public static String getRandomFileName(String fileName){
		String fileNewName = "";
		if(PublicUtil.isNotEmpty(fileName)){
			int dot = fileName.lastIndexOf(".");
			String extension = fileName.substring(dot + 1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = sdf.format(Calendar.getInstance().getTime());
			int random = new Random().nextInt(10000);
			fileNewName = time + random + "." + extension;
		}
		return fileNewName;
	}
	
    /**
     * �ַ��ı�����ļ�
     * @param filePath �ļ�·��
     * @return
     */
    public static String string2file(String txt,String filePath){
        if (filePath ==null || filePath.equals("")) {
            return "";
        }
        filePath = filePath.replace("\\", "/").replace("//", "/");
        File file = new File(filePath);
        if (file.exists()) {
            FileUtils.deleteFile(filePath);
        }else{
            String tmpfile = filePath.substring(0, filePath.lastIndexOf("/"));
            File tmpDir = new File(tmpfile);
            if (!file.exists()) {
                tmpDir.mkdirs();
            }
        }
        StringBuffer sb = new StringBuffer("<html><head></head><body>");
        sb.append(txt);
        sb.append("</body></html>");
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath);
            fw.write(sb.toString());
            fw.close();
        }catch(Exception e){
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filePath;
    }
    
    /**
     * ��ȡ�ļ�����
     * @param wordPath word���ļ�·��
     * @return
     * @throws IOException 
     */
    public static String file2string(String filepath) throws IOException{
        StringBuffer sb = new StringBuffer();
        FileReader fr = null;
        BufferedReader buf = null;
        try {
            File file = new File(filepath);
            if (file.exists()) {
                fr = new FileReader(file);
                buf = new BufferedReader(fr);
                String line = null;
                while((line = buf.readLine()) != null){
                    sb.append(line);
                }
            }
        }catch(Exception e){
           e.printStackTrace();
        }finally{
            buf.close();
            fr.close();
        }
        return sb == null ? null : sb.toString();
    }
    
}
