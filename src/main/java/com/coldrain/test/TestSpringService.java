package com.coldrain.test;

import com.coldrain.exception.ColdRainException;
import com.coldrain.ncm.NcmService;
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
}
