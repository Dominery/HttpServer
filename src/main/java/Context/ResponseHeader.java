package Context;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-24-21:30
 */
public class ResponseHeader {
    private final int status;
    private final Map<String,String> attrs = new HashMap<>();
    public ResponseHeader(int status){
        this.status = status;
    }
    public void addAttr(String key,String value){
        attrs.put(key.toLowerCase(),value);
    }
    public Stream<byte[]> getBytes(){
        List<byte[]> result = new ArrayList<>();
        result.add(getHead());
        attrs.forEach((key,value)->{
            String attr = key + ":" + value + "\r\n";
            result.add(attr.getBytes(StandardCharsets.UTF_8));
        });
        result.add("\r\n".getBytes());
        return result.stream();
    }
    private byte[] getHead(){
        String result = "HTTP/1.2 ";
        if(status>=200&&status<300){
            result += "200 OK";
        }else if(status>=400&&status<500){
            result += "404 NoT Found";
        }
        result += "\r\n";
        return result.getBytes(StandardCharsets.UTF_8);
    }
}
