package com.coldrain.ncm.utils;

import com.coldrain.FirstSpringApp;
import com.coldrain.ncm.NcmService;
import com.coldrain.ncm.jsonSupp.PlayListBean;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
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
    void test2(){
        ncmService = applicationContext.getBean((NcmService.class));
        List<PlayListBean> plbList = ncmService.getPlaylists_publicTest();
        for(PlayListBean aBean: plbList){
            System.out.println(aBean);
        }
    }

}
