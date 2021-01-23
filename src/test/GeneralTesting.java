package test;

import javax.swing.filechooser.FileSystemView;
import exception.ColdRainException;
import local.generic.BaseLocalTestingClass;
import ncm.NcmService;

public class GeneralTesting extends BaseLocalTestingClass{
    // 用后既删
    public static void main(String[] args) throws ColdRainException {
        GeneralTesting me = new GeneralTesting();
        me.setAllLevel("debug");
//        me.readerCtrl.setLevel("error");
        /*
        AudioDBService adbs = new AudioDBService();
        adbs.fullScanAudioFiles(new File("F:\\Discography"));
        adbs.fullFileModelCleanse();
        */
        FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
        System.out.println(fsv.getHomeDirectory());
        NcmService ncmS = new NcmService();
        ncmS.test();
    }
}
