package xyz.wongs.weathertop.java.base.io;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName ReadFileDemo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/28 11:16
 * @Version 1.0.0
 */
public class ReadFileDemo {

    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+ File.separator+"Doc"+File.separator+"file.log";
//        fileInputStream(path);
//        fileReader(path);
//        create();
//        write();
        list();
    }


    /**
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 14:55
     */
    public static void list(){
        String path = System.getProperty("user.dir")+ File.separator+"Doc";
        File file = new File(path);
        try {
            if(file.isDirectory()){
                File[] files = file.listFiles();
//                File[] files = file.listFiles(new ImageFilter());
                for (File temp : files) {
                    System.out.print("[文件名]: "+temp.getName());
                    System.out.print(" \t [文件大小]: "+(temp.length()/1024) +" Kb");
                    System.out.print(" \t [文件]: "+(temp.isFile()?"文件":"不是文件"));
                    System.out.print(" \t [目录]: "+(temp.isDirectory()?"文件夹":"不是文件夹"));
                    System.out.print(" \t [文件修改日期]: "+getDateTime(temp.lastModified()));
                    System.out.println("");
                }
            }
        } finally {
            
        }
    }
    public static LocalDateTime getDateTime(long milli){
        Instant instant = Instant.ofEpochMilli(milli);
        return  LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    /**
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 14:55
     */
    public static void write(){
        String path = System.getProperty("user.dir")+ File.separator+"Doc"+File.separator+"tmp.log";
        File file = new File(path);

        FileWriter fw = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            fw = new FileWriter(file);
            for (int i = 0; i < 3; i++) {
                fw.write("这是第" + i +" 行");
                fw.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=fw){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 创建文件
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 14:55
     */
    public static void create(){
        String path = System.getProperty("user.dir")+ File.separator+"Doc"+File.separator+"tmp.log";
        File file = new File(path);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[文件名]: "+file.getName());
        System.out.println("[文件大小]: "+(file.length()/1024) +" Kb");
        System.out.println("[文件]: "+(file.isFile()?"文件":"不是文件"));
        System.out.println("[目录]: "+(file.isDirectory()?"文件夹":"不是文件夹"));
        System.out.println("[文件修改日期]: "+new Date(file.lastModified()));
    }

    /** 删除文件
     * @Description
     * @param
     * @return void
     * @throws
     * @date 2021/5/28 14:55
     */
    public static void delete(){
        String path = System.getProperty("user.dir")+ File.separator+"Doc"+File.separator+"tmp.log";
        File file = new File(path);
        if(!file.exists()){
            file.delete();
        }
    }

    /** BufferedReader 按行读写 读写
     * @Description
     * @param path
     * @return void
     * @throws
     * @date 2021/5/28 11:33
     */
    public static void fileReader(String path){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            StringBuilder stringBuilder =new StringBuilder();
            while(bufferedReader.read()!=-1){
                stringBuilder.append(bufferedReader.readLine());
            }
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** InputStreamReader + 缓冲区 读写
     * @Description
     * @param path
     * @return void
     * @throws
     * @date 2021/5/28 11:33
     */
    public static void inputStreamReader(String path){
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(path));
            char[] buffer = new char[2048];
            int length = 0;
            StringBuilder stringBuilder =new StringBuilder();
            while((length=isr.read(buffer))!=-1){
                stringBuilder.append(new String(buffer,0,length));
            }
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** FileInputStream + 缓冲区 读写
     * @Description
     * @param path
     * @return void
     * @throws
     * @date 2021/5/28 11:33
     */
    public static void fileInputStream(String path){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            byte[] buffer = new byte[2048];
            int length = 0;
            StringBuilder stringBuilder =new StringBuilder();
            while((length=fileInputStream.read(buffer))!=-1){
                stringBuilder.append(new String(buffer,0,length));
            }
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ImageFilter implements FilenameFilter{
    @Override
    public boolean accept(File dir, String name) {
        // 哪些文件后缀可以通过
        return name.endsWith(".jpg") || name.endsWith(".bmp") || name.endsWith(".jpeg");
    }
}
