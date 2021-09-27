package Context;

import util.ClientSocket;
import util.Config;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-27-20:01
 */
public class Context {
    private final ClientSocket client;
    private final Request request;
    private final String localPath;
    public Context(ClientSocket client){
        this.client = client;
        request = Request.build(client.recv());
        localPath = parseLocalPath();
    }
    public String getRequest(){
        return request.getRequest();
    }
    public String getMethod(){
        return request.getMethod();
    }
    public Optional<String> query(String key){
        return request.query(key);
    }
    public String getUrl(){
        return request.getUrl();
    }
    public String getLocalPath(){
        return localPath;
    }
    public void send(Stream<byte[]> data){
        data.forEach(client::send);
        client.close();
    }
    public InetSocketAddress getAddr(){
        return client.getAddr();
    }
    private String parseLocalPath() {return Config.SEARCH_DIR + getUrl().replace("/", File.separator);}
}
