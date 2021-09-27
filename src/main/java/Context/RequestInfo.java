package Context;

import java.net.InetSocketAddress;

/**
 * @author suyu
 * @create 2021-09-25-15:18
 */
class RequestInfo {
    private final String host;
    private final int port;
    private final String request;
    public RequestInfo(InetSocketAddress addr, String header){
        host = addr.getHostName();
        port = addr.getPort();
        this.request = header;
    }

    @Override
    public String toString() {
        return  "host='" + host + '\'' +
                ", port=" + port +
                ", header='" + request + '\'' ;
    }
}
