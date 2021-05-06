package com.coldrain.old.readers;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

//ZplOriginalreader 是曾经测试时的reader 现在其中部分功能已经被其他class继承，并无卵用。
public class ZplOriginalReader {
    SAXReader reader = new SAXReader();
    Element sequence;
    
    public ZplOriginalReader(String addr) throws DocumentException{
    Document document = reader.read(new File(addr));
    // 首先获取整个xml（zpl）的rootElement //必须一层一层往下，不能急
    Element rootElement = document.getRootElement();
    System.out.println(rootElement);
    Element body = rootElement.element("body");
    System.out.println(body);
    sequence = body.element("seq");
    
    }
    //
        
    public Element getMedias() {
        return sequence;
    }
    

}
