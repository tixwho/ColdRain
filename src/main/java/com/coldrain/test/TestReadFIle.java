package com.coldrain.test;

import java.io.*;

//初次尝试fileInput与fileOutput
public class TestReadFIle {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(
            new FileInputStream("F:\\ASUS\\Music\\Playlists\\ORBIS TERRARVM.zpl"));
        // 创建dataInputStream, 并用FileInputStream读取文件
        DataOutputStream out =
            new DataOutputStream(new FileOutputStream("F:\\ASUS\\Music\\TestOutput\\com.coldrain.test.zpl"));
        // 创建dataOutputStream, 并用FileOutputStream创建文件 （再次运行会覆盖原文件）
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        // BufferedReader是用InputStreamReader创建的，使用InputStream
        String count;
        boolean isContent = false;
        String indent = "";
        while ((count = d.readLine()) != null) {
            String u = count.trim();
            if (u.contains("<seq>")) {
                isContent = !isContent;
                indent = " ";
                System.out.println(u);
                out.writeBytes(u + "\r\n"); // "/r/n" 可以实现换行
                continue; //手动调用了一次write，直接跳过读下一行
                
            }
            if (u.contains("</seq>")) {
                System.out.println(u);
                out.writeBytes(u + "\r\n"); 
                isContent = !isContent;
            }
            if (isContent) {
                System.out.println(u);
                out.writeBytes(indent + u + "\r\n");
            }
        }
        d.close();
        out.close();
    }


}
