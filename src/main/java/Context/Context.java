package Context;

import util.ClientSocket;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-27-20:01
 */
public class Context {
    private final ClientSocket client;
    private final Request req;
    private Response res = new Response(200);
    public Context(ClientSocket client){
        this.client = client;
        req = Request.build(client.recv());
    }
    public String getReq(){
        return new RequestInfo(client.getAddr(), req.getRequest()).toString();
    }
    public String getRes(){return new ResponseInfo(getMethod(),getUrl(),getStatus()).toString();}
    public String getMethod(){
        return req.getMethod();
    }
    public Optional<String> query(String key){
        return req.query(key);
    }
    public String getUrl(){
        return req.getUrl();
    }
    public void body(Stream<byte[]> data){
        Stream.concat(res.getBytes(),data).forEach(client::send);
        client.close();
    }
    public void setStatus(int status){
        res = new Response(status);
    }
    public int getStatus(){return res.getStatus();}
    public void setResHeader(String key,String value){
        res.setHeader(key,value);
    }
}
