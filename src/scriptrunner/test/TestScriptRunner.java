package scriptrunner.test;

import java.io.File;
import scriptrunner.ScriptRunner;

public class TestScriptRunner {

    public static void main(String[] args) {
        File exe = new File("E:\\lzx\\etc\\ncmdump-master\\main.exe");
        String[] arguments = {"E:\\lzx\\Discovery\\ColdRain\\Ncm\\水瀬いのり - クリスタライズ.ncm",
            "E:\\lzx\\Discovery\\ColdRain\\Ncm\\Luciano Pavarotti - Nessun Dorma.ncm"};
        System.out.println("exit:"+ScriptRunner.run(exe, arguments));

    }

}
