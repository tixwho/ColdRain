package com.coldrain.ncm.utils;

import com.coldrain.FirstSpringApp;
import com.coldrain.database.service.AudioDBService;
import com.coldrain.exception.MetaIOException;
import com.coldrain.ncm.NcmService;
import com.coldrain.ncm.jsonSupp.PlayListBean;
import com.coldrain.playlist.generic.MetaSong;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class NcmConfigTest {

    static org.springframework.context.ApplicationContext applicationContext;
    static NcmConfig ncmConfig;
    static NcmService ncmService;
    static AudioDBService audioDBService;
    static File tempFile;

    @BeforeAll
    static void initContext(){
        applicationContext = new AnnotationConfigApplicationContext(FirstSpringApp.class);
    }

    @Test
    void test1(){
        ncmConfig = applicationContext.getBean(NcmConfig.class);
        System.out.println("ncmConfig:"+ncmConfig);
    }

    @Test
    @Disabled
    void test2(){
        ncmService = applicationContext.getBean((NcmService.class));
        List<PlayListBean> plbList = ncmService.getPlaylists_publicTest();
        for(PlayListBean aBean: plbList){
            System.out.println(aBean);
        }
    }

    @Test
    void test3() throws MetaIOException {
        audioDBService = (AudioDBService) applicationContext.getBean("AudioDBService");
        System.out.println("AudioDBService RETRIEVED");
        audioDBService.scanSingle(new MetaSong("F:\\CloudMusic\\电台节目\\初弦__ - 「遗尘漫步」PV曲.mp3"));
    }

    @Test
    void test4() throws MetaIOException {
        audioDBService.scanSingle(new MetaSong("F:\\CloudMusic\\电台节目\\初弦__ - 「画中人」 夕.mp3"));
    }

    @Test
    //manually scan a copy of different src
    void test5() throws IOException {
        File origFile = new File("F:\\CloudMusic\\电台节目\\初弦__ - 「覆潮之下」深海猎人.mp3");
        tempFile = new File ("E:\\lzx\\Discovery\\ColdRain\\temp.mp3");
        FileUtils.copyFile(origFile,tempFile);
    }

    @Test
    void test6() throws MetaIOException {
        audioDBService.scanSingle(new MetaSong(tempFile));
    }

    @Test
    void test7(){
        tempFile.delete();
    }

    @Test
    void test8() throws MetaIOException {
        audioDBService.invalidFileMFullCleanse();
        audioDBService.invalidMetaCleanup();
    }

}
