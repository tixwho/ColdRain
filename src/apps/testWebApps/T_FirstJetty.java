package apps.testWebApps;

import org.eclipse.jetty.server.Server;

public class T_FirstJetty {
    
    public static void main(String[] args) throws Exception {
        Server server = new Server(8989);
        server.setHandler(new T_JettyController());
        server.start();
        server.join();
    }

}
