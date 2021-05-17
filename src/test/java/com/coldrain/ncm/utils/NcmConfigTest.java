package com.coldrain.ncm.utils;

import com.coldrain.FirstSpringApp;
import com.coldrain.database.bo.ArtistBo;
import com.coldrain.database.bo.FileBo;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.service.AudioDBService;
import com.coldrain.exception.DatabaseException;
import com.coldrain.exception.MetaIOException;
import com.coldrain.ncm.NcmService;
import com.coldrain.ncm.jsonSupp.PlayListBean;
import com.coldrain.playlist.generic.MetaSong;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NcmConfigTest {

    static org.springframework.context.ApplicationContext applicationContext;
    static NcmConfig ncmConfig;
    static NcmService ncmService;
    static AudioDBService audioDBService;
    static File tempFile;
    static ArtistBo artistBo;

    @BeforeAll
    static void initContext(){

        applicationContext = new AnnotationConfigApplicationContext(FirstSpringApp.class);
    }

    @Test
    @Order(1)
    void test1(){
        ncmConfig = applicationContext.getBean(NcmConfig.class);
        System.out.println("ncmConfig:"+ncmConfig);
    }

    @Test
    @Order(2)
    @Disabled
    void test2(){
        ncmService = applicationContext.getBean((NcmService.class));
        List<PlayListBean> plbList = ncmService.getPlaylists_publicTest();
        for(PlayListBean aBean: plbList){
            System.out.println(aBean);
        }
    }

    @Test
    @Order(3)
    void test3() throws MetaIOException {
        audioDBService = (AudioDBService) applicationContext.getBean("AudioDBService");
        System.out.println("AudioDBService RETRIEVED");
        audioDBService.scanSingle(new MetaSong("F:\\CloudMusic\\电台节目\\初弦__ - 「遗尘漫步」PV曲.mp3"));
    }

    @Test
    @Order(4)
    void test4() throws MetaIOException {
        audioDBService.scanSingle(new MetaSong("F:\\CloudMusic\\电台节目\\初弦__ - 「画中人」 夕.mp3"));
    }

    @Test
    @Order(5)
    //manually scan a copy of different src
    void test5() throws IOException {
        File origFile = new File("F:\\CloudMusic\\电台节目\\初弦__ - 「覆潮之下」深海猎人.mp3");
        tempFile = new File ("E:\\lzx\\Discovery\\ColdRain\\temp.mp3");
        FileUtils.copyFile(origFile,tempFile);
    }

    @Test
    @Order(6)
    @Disabled
    void test6() throws MetaIOException {
        audioDBService.scanSingle(new MetaSong(tempFile));
    }

    @Test
    @Order(7)
    void test7(){
        tempFile.delete();
    }

    @Test
    @Order(8)
    void test8() throws MetaIOException {
        audioDBService.invalidFileMFullCleanse();
        audioDBService.invalidMetaCleanup();
    }

    @Test
    @Order(9)
    void test9() throws DatabaseException {
        audioDBService.fullFolderScan(new File("E:\\lzx\\Discovery\\ColdRain\\Discography\\THird"));
        audioDBService.fullFolderScan(new File("E:\\lzx\\etc\\OST\\Discography\\NEGA FRAGMENTS"));
        audioDBService.fullFolderScan(new File("E:\\lzx\\etc\\OST\\Discography\\AD：PIANO VI -Punishment-\\Disc 1"));
    }

    @Test
    @Order(10)
    @Disabled
    void test10(){
        FileBo fileBo = (FileBo) applicationContext.getBean("FileBo");
        artistBo = (ArtistBo) applicationContext.getBean("ArtistBo");
        Assertions.assertNotNull(fileBo.findBySrc("F:\\Discography\\Cossette\\宝石\\FictionJunction - 宝石 (original karaoke).flac"));
        Assertions.assertNotNull(artistBo.findByArtist("FictionJunction"));
        Assertions.assertNotNull(artistBo.findByArtist("梶浦由記"));
    }

    @Test
    @Order(11)
    @Disabled
    void test11(){
        ArtistBo artistBo = (ArtistBo) applicationContext.getBean("ArtistBo");
        artistBo.connectSingleAndMultiArtistM(artistBo.findByArtist("水瀬いのり"),artistBo.findByArtist("瀬戸麻沙美/東山奈央/種崎敦美/内田真礼/久保ユリカ/水瀬いのり"));
    }

    @Test
    @Order(12)
    void test12(){
        ArtistBo artistBo = (ArtistBo) applicationContext.getBean("ArtistBo");
        artistBo.disconnectSingleAndMultiArtistM(artistBo.findByArtist("水瀬いのり"),artistBo.findByArtist("瀬戸麻沙美/東山奈央/種崎敦美/内田真礼/久保ユリカ/水瀬いのり"));
    }

    @Test
    @Order(13)
    @Disabled
    void test13(){
        ArtistBo artistBo = (ArtistBo) applicationContext.getBean("ArtistBo");
        artistBo.registerArtistJacket(artistBo.findByArtist("Feryquitous"),artistBo.findByArtist("An"));
    }

    @Test
    @Order(14)
    void test14(){
        ArtistBo artistBo = (ArtistBo) applicationContext.getBean("ArtistBo");
        artistBo.abandonArtistJacket(artistBo.findByArtist("An"));
        ArtistModel artistM = artistBo.findByArtist("Feryquitous");
        System.out.println("jacketSize:"+artistM.getJacketArtists().size());
    }

}
