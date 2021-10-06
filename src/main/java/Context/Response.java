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
class Response {
    private final int status;
    private static final Map<Integer,String> statusInfo = new HashMap<>();
    static {
        statusInfo.put(200,"OK");
        statusInfo.put(404,"Not Found");
        statusInfo.put(500,"Internal Server Error");
    }
    private final Map<String,String> attrs = new HashMap<>();
    public Response(int status){
        this.status = status;
    }
    public void setHeader(String key, String value){
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
    public int getStatus(){
        return status;
    }
    private byte[] getHead(){
        String result = "HTTP/1.2 "+ status + statusInfo.getOrDefault(status,"");
        result += "\r\n";
        return result.getBytes(StandardCharsets.UTF_8);
    }
}
