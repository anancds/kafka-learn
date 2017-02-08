package com.cds.bigdata.parrot.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by chendongsheng5 on 2017/2/8.
 */
public class FileUtils {

  /**
   * 保存为文本文件
   *
   * @param file
   * @param s
   */
  public static void saveStringFile(File file, String s) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(file);
      writer.write(s);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) try {
        writer.flush();
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 删除目录及其子目录
   * @param dir
   * @return
   */
  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
      File[] children = dir.listFiles();
      for (File child : children) {
        boolean success = deleteDir(child);
        if (!success) {
          return false;
        }
      }
    }
    return dir.delete();
  }

  /**
   * 读取文本文件，转换成“UTF8”格式的字符串
   * @param file
   * @return
   */
  public static String readString(File file) {
    StringBuilder sb = new StringBuilder();
    InputStreamReader reader = null;
    try {
      if (file.isFile() && file.exists()) {
        reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
          sb.append(line).append("\n");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sb.toString();
  }

  public static List<String> lists(File file) {
    List<String> files = new ArrayList<>();
    if (file.isDirectory()) {
      File[] children = file.listFiles();
      for (File child : children) {
        files.addAll(lists(child));
      }
    }else{
      files.add(file.getAbsolutePath());
    }
    return files;
  }

  /**
   * 创建zip包
   *
   * @param resources
   * @return
   */
  public static File zip(List<File> resources) throws IOException {
    File zipFile = new File(UUID.randomUUID().toString() + ".zip");
    FileOutputStream zipFileOutputStream = null;
    ZipOutputStream zipOutputStream = null;
    try {
      zipFileOutputStream = new FileOutputStream(zipFile);
      zipOutputStream = new ZipOutputStream(zipFileOutputStream);
      for (File resource : resources) {
        List<String> files = lists(resource);
        for (String file : files) {
          FileInputStream fin = null;
          ZipEntry zipEntry = new ZipEntry(new File(file).getName());
          try {
            zipOutputStream.putNextEntry(zipEntry);
            fin = new FileInputStream(new File(file));
            copy(fin, zipOutputStream);
            zipOutputStream.closeEntry();
          }  finally {
            if (fin != null) {
              try {
                fin.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
    } finally {
      if (zipOutputStream != null) {
        try {
          zipOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (zipFileOutputStream != null) {
        try {
          zipFileOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return zipFile;
  }

  public static long copy(InputStream from, OutputStream to)
      throws IOException {
    if (from == null) {
      throw new NullPointerException();
    }
    if (to == null) {
      throw new NullPointerException();
    }
    byte[] buf = new byte[0x1000];
    long total = 0;
    while (true) {
      int r = from.read(buf);
      if (r == -1) {
        break;
      }
      to.write(buf, 0, r);
      total += r;
    }
    return total;
  }

  /**
   * 解压到给定目录
   *
   * @param localZipFile
   * @param dstDir
   */
  public static void unzip(File localZipFile, File dstDir) throws IOException {
    int bufferSize = 1024;
    ZipFile zipFile = null;
    try {
      zipFile = new ZipFile(localZipFile);
      Enumeration emu = zipFile.entries();
      while (emu.hasMoreElements()) {
        ZipEntry entry = (ZipEntry) emu.nextElement();
        if (entry.isDirectory()) {
          new File(dstDir, entry.getName()).mkdirs();
          continue;
        }
        BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
        File file = new File(dstDir, entry.getName());
        File parent = file.getParentFile();
        if (parent != null && (!parent.exists())) {
          parent.mkdirs();
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
          fos = new FileOutputStream(file);
          bos = new BufferedOutputStream(fos, bufferSize);
          int count;
          byte data[] = new byte[bufferSize];
          while ((count = bis.read(data, 0, bufferSize)) != -1) {
            bos.write(data, 0, count);
          }
          bos.flush();
        } finally {
          if (bos != null) {
            bos.close();
          }
          if (bis != null) {
            bis.close();
          }
          if (fos != null) {
            fos.close();
          }
        }
      }
    } finally {
      if (zipFile != null) {
        zipFile.close();
      }
    }
  }
}
