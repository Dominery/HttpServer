package Context;

import util.Utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author suyu
 * @create 2021-09-24-17:23
 */
class Request {
    private final Map<String,String> attrs;
    private final String request;
    private URL url;
    private String method;
    private final Pattern headRegex = Pattern.compile("([^/]+)(/[^ ]*)");
    private Request(Map<String,String> attrs,String header){
        this.attrs = attrs;
        this.request = header;
        parseHead();
    }
    public Optional<String> getAttr(String key){
        return Optional.of(attrs.get(key.toLowerCase()));
    }
    private void parseHead(){
        Matcher matcher = headRegex.matcher(request);
        if(matcher.find()) {
            method = matcher.group(1).trim();
            url = new URL(matcher.group(2));
        }
    }
    public String getUrl(){
        return url.getUriPath();
    }
    public Optional<String> query(String key){
        return url.getValue(key);
    }
    public String getMethod(){
        return method;
    }
    public String getRequest(){
        return request;
    }
    public static Request build(List<String> data){
        String header = data.remove(0);
        Map<String,String> attrs = Utils.toMap(data.stream(),":");
        return new Request(attrs,header);
    }
}
