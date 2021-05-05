package com.coldrain.ncm;

import com.coldrain.exception.ColdRainException;
import com.coldrain.ncm.models.NcmAudioInfoComp;
import com.coldrain.ncm.utils.NcmPropertiesUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import proto.KarylTestBase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NcmServiceTest extends KarylTestBase {

    static NcmService service = null;
    static Class<NcmService> serviceClazz;
    static Method databaseInitMethod;
    static Method databaseCloseMethod;
    static List<NcmAudioInfoComp> djOfflineInfoList = new ArrayList<>();
    
    @BeforeAll
    static void initService() throws NoSuchMethodException {
        String propStr =
            "E:\\lzx\\Discovery\\NeteaseMusicDBExport-master\\NeteaseMusicDBExport-master\\com.coldrain.ncm.properties";
        NcmPropertiesUtil.readNcmProperties(new File(propStr)); //initialize DB location
        service = new NcmService();
        serviceClazz= NcmService.class;
        databaseInitMethod= serviceClazz.getDeclaredMethod("initDB");
        databaseInitMethod.setAccessible(true);
        databaseCloseMethod = serviceClazz.getDeclaredMethod("closeDB");
        databaseCloseMethod.setAccessible(true);

    }

    @Test
    //com.coldrain.test retrieving Dj Programs
    void test1() throws ColdRainException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        databaseInitMethod.invoke(service);
        Method privateRetrieveDJMethod = serviceClazz.getDeclaredMethod("collectAllDJProgram_offlineOnly");
        privateRetrieveDJMethod.setAccessible(true);
        djOfflineInfoList = (List<NcmAudioInfoComp>) privateRetrieveDJMethod.invoke(service); //hardcast
        for(NcmAudioInfoComp aComp: djOfflineInfoList){
            System.out.println(aComp);
            System.out.println("ImgUrl:"+aComp.getDj_coverUrl());
        }
        databaseCloseMethod.invoke(service);
    }

    @Test
    void test2() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method privateImgRecoverMethod = serviceClazz.getDeclaredMethod("updateDJAlbumArt",NcmAudioInfoComp.class);
        privateImgRecoverMethod.setAccessible(true);
        for(NcmAudioInfoComp aComp : djOfflineInfoList){
            if(aComp.getDj_coverUrl()==null){
                continue;
            }
            privateImgRecoverMethod.invoke(service,aComp);
        }
    }
    
}
