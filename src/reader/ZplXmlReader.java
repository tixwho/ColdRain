package reader;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

//ZplXmlReader 每次应该return的是一个Element,其中包含了所有的<media>（a.k.a <seq>)
public class ZplXmlReader {
    SAXReader reader = new SAXReader();
    Element sequence;
    
    public ZplXmlReader(String addr) throws DocumentException{
    Document document = reader.read(new File(addr));
    // 首先获取整个xml（zpl）的rootElement //必须一层一层往下，不能急
    Element rootElement = document.getRootElement();
    System.out.println(rootElement);
    Element body = rootElement.element("body");
    System.out.println(body);
    sequence = body.element("seq");
    
    }
    //
        
    public Element getSequence() {
        return sequence;
    }
    
    

}
