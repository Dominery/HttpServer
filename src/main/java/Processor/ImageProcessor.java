package Processor;

import Context.Response;
import util.Config;
import util.URL;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-25-6:52
 */
public class ImageProcessor extends Processor {

    public ImageProcessor(){
        regex = Pattern.compile(".(jpg|jpeg|png|gif)$");
    }
    @Override
    public Stream<byte[]> process(Response res, URL uri) {
        res.addAttr(Config.CONTENT_TYPE, String.format(Config.IMAGE_TYPE,getType(uri.getLocalPath())));
        return getBody(new File(uri.getLocalPath()));
    }

    private String getType(String path){
        Matcher matcher = regex.matcher(path);
        String type = "";
        if(matcher.find())type = matcher.group(1);
        return type;
    }
}
