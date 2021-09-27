package util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-27-19:46
 */
public class Utils {
    public static Map<String,String> toMap(Stream<String> stringStream, String mark){
        HashMap<String, String> result = new HashMap<>();
        stringStream
                .map(query -> query.split(mark))
                .filter(strs->strs.length>1)
                .forEach(strs -> result.put(strs[0].toLowerCase(),strs[1]));
        return result;
    }
}
