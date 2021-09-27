package header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author suyu
 * @create 2021-09-24-17:23
 */
public class Request {
    private final Map<String,String> attrs;
    private final String header;
    private final Pattern headRegex = Pattern.compile("[^/]+(/[^ ]*)");
    private Request(Map<String,String> attrs,String header){
        this.attrs = attrs;
        this.header = header;
    }
    public Optional<String> getAttr(String key){
        return Optional.of(attrs.get(key.toLowerCase()));
    }
    public String getUri(){
        String result = "/";
        Matcher matcher = headRegex.matcher(header);
        if(matcher.find()) result =matcher.group(1);
        return result;
    }
    public String getHeader(){
        return header;
    }
    public static Request build(List<String> data){
        String header = data.remove(0);
        Map<String,String> attrs = new HashMap<>();
        data.stream()
                .map(str -> str.split(":"))
                .filter(attr -> attr.length >1)
                .forEach(attr -> {
                    attrs.put(attr[0].toLowerCase(), attr[1]);
                });
        return new Request(attrs,header);
    }
}
