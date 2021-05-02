package test;

import toolkit.SourceReader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

// 尝试调用toolkit内方法
public class TestReadFIle2 {
    public static void main(String[] args) throws IOException {

        SourceReader source = new SourceReader("F:\\ASUS\\Music\\Playlists\\ORBIS TERRARVM.zpl",
            "F:\\ASUS\\Music\\TestOutput\\test.zpl");
        DataOutputStream out = source.getOutStream();
        // 创建dataOutputStream, 并用FileOutputStream创建文件 （再次运行会覆盖原文件）
        BufferedReader d = source.getReader();
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
                continue; // 手动调用了一次write，直接跳过读下一行

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
