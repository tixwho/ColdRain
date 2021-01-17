package test;

import java.io.File;
import database.service.AudioDBService;
import exception.DatabaseException;
import local.generic.BaseLocalTestingClass;

public class GeneralTesting extends BaseLocalTestingClass{
    // 用后既删
    public static void main(String[] args) throws DatabaseException {
        GeneralTesting me = new GeneralTesting();
        me.setAllLevel("debug");
//        me.readerCtrl.setLevel("error");
        AudioDBService.fullScanAudioFiles(new File("F:\\Discography"));
        AudioDBService.fullFileModelCleanse();
    }
}
