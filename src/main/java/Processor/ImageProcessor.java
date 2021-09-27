package Processor;

import Context.Context;
import Context.ResponseHeader;
import util.Config;

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
    public Stream<byte[]> process(ResponseHeader res, Context context) {
        res.addAttr(Config.CONTENT_TYPE, String.format(Config.IMAGE_TYPE,getType(context.getLocalPath())));
        return getBody(new File(context.getLocalPath()));
    }

    private String getType(String path){
        Matcher matcher = regex.matcher(path);
        String type = "";
        if(matcher.find())type = matcher.group(1);
        return type;
    }
}
