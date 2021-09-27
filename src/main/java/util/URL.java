package util;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author suyu
 * @create 2021-09-25-10:25
 */
public class URL {
    private final Map<String,String> queries = new HashMap<>();
    private String path = "/";
    public URL(String rawUri){
        Pattern pathRegex = Pattern.compile("^([^?]+)");
        path = match(rawUri, pathRegex,"/");
        Pattern queryRegex = Pattern.compile("\\?([^ ]+)");
        generateQuery(match(rawUri, queryRegex,""));
    }
    public Optional<String> getValue(String value){
        return Optional.of(queries.get(value));
    }
    public String getUriPath(){
        return path;
    }
    public String getLocalPath(){
        return Config.SEARCH_DIR + getUriPath().replace("/", File.separator);
    }
    private String match(String rawString,Pattern regex,String defaultResult){
        String result = defaultResult;
        Matcher matcher = regex.matcher(rawString);
        if(matcher.find())result = matcher.group(1);
        return result;
    }
    private void generateQuery(String queryString){
        Arrays.stream(queryString.split("&"))
                .map(query -> query.split("="))
                .filter(strs->strs.length>1)
                .forEach(strs -> queries.put(strs[0].toLowerCase(),strs[1]));
    }
}
