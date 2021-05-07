package com.coldrain.test;

import com.coldrain.exception.ColdRainException;
import com.coldrain.ncm.NcmService;
import com.coldrain.ncm.jsonSupp.PlayListBean;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TestSpringService {

    private final NcmService ncmService;

    public TestSpringService(NcmService ncmService) {
        this.ncmService = ncmService;
    }


    public void testNcmInside(){
        try {
            ncmService.test();
        } catch (ColdRainException e) {
            e.printStackTrace();
        }
    }

    public void testNcmTransaction(){
        List<PlayListBean> plbList = ncmService.getPlaylists_publicTest();
        for(PlayListBean aBean: plbList){
            System.out.println(aBean);
        }
    }
}
