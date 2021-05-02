package scriptrunner.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

// copypasta
public class TestCmd {
    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] cmdStrings =
                {"cmd", "/c", "main.exe", "E:\\lzx\\Discovery\\ColdRain\\Ncm\\水瀬いのり - クリスタライズ.ncm",
                    "E:\\lzx\\Discovery\\ColdRain\\Ncm\\Luciano Pavarotti - Nessun Dorma.ncm"};
            String cmdString =
                "main.exe E:\\\\lzx\\\\Discovery\\\\ColdRain\\\\Ncm\\\\水瀬いのり - クリスタライズ.ncm";
            String[] envp = {"path=E:\\lzx\\etc\\ncmdump-master"};
            File dir = new File("E:\\lzx\\etc\\ncmdump-master");
            Process proc = rt.exec(cmdStrings, envp, dir);
            // Process proc = rt.exec(cmdString,envp,dir);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<error></error>");
            while ((line = br.readLine()) != null)
                System.out.println(line);
            System.out.println();
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
