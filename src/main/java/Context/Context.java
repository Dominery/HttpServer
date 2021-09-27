package Context;

import util.ClientSocket;
import util.Config;

import java.io.File;
import java.util.Optional;

/**
 * @author suyu
 * @create 2021-09-27-20:01
 */
public class Context {
    private final ClientSocket client;
    private final Request request;
    public Context(ClientSocket client){
        this.client = client;
        request = Request.build(client.recv());
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
        return Config.SEARCH_DIR + getUrl().replace("/", File.separator);
    }
}
