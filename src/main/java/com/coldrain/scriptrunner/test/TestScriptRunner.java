package com.coldrain.scriptrunner.test;

import com.coldrain.scriptrunner.ScriptRunner;

import java.io.File;

public class TestScriptRunner {

    public static void main(String[] args) {
        File exe = new File("E:\\lzx\\etc\\ncmdump-master\\main.exe");
        String[] arguments = {"E:\\lzx\\Discovery\\ColdRain\\Ncm\\水瀬いのり - クリスタライズ.com.coldrain.ncm",
            "E:\\lzx\\Discovery\\ColdRain\\Ncm\\Luciano Pavarotti - Nessun Dorma.com.coldrain.ncm"};
        System.out.println("exit:"+ScriptRunner.run(exe, arguments));

    }

}
