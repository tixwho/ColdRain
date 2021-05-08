package com.coldrain;

import com.coldrain.database.bo.FileBo;
import com.coldrain.database.models.FileModel;
import com.coldrain.exception.MetaIOException;
import com.coldrain.playlist.generic.MetaSong;
import com.coldrain.test.TestSpringService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class FirstSpringApp {

    private TestSpringService testSpringService;

    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(FirstSpringApp.class);
        TestSpringService testSpringService = context.getBean(TestSpringService.class);
        //testSpringService.testNcmInside();
        testSpringService.testNcmTransaction();
        FileBo fileBo = (FileBo) context.getBean("FileBo");
        MetaSong theSong = null;
        try {
            theSong = new MetaSong("F:\\CloudMusic\\电台节目\\初弦__ - 「遗尘漫步」PV曲.mp3");
        } catch (MetaIOException e) {
            e.printStackTrace();
        }
        FileModel theFM = new FileModel(theSong);
        FileModel thatFM;
        fileBo.save(theFM);
        thatFM = fileBo.findBySrc(theSong.getSrc());
        fileBo.delete(theFM);
        thatFM = fileBo.findBySrc(theSong.getSrc());
    }

}
