package ncm;

import ncm.utils.NcmPropertiesUtil;
import org.junit.jupiter.api.BeforeAll;
import proto.KarylTestBase;

import java.io.File;

public class NcmServiceTest extends KarylTestBase {

    NcmService service = null;
    
    @BeforeAll
    void initService() {
        String propStr =
            "E:\\lzx\\Discovery\\NeteaseMusicDBExport-master\\NeteaseMusicDBExport-master\\ncm.properties";
        NcmPropertiesUtil.readNcmProperties(new File(propStr)); //initialize DB location
        service = new NcmService();
    }
    
}
