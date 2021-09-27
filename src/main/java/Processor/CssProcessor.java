package Processor;

import Context.Context;
import Context.ResponseHeader;
import util.Config;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-25-10:15
 */
public class CssProcessor extends Processor {
    public CssProcessor(){
        regex = Pattern.compile(".css$");
    }
    @Override
    public Stream<byte[]> process(ResponseHeader res, Context context) {
        res.addAttr(Config.CONTENT_TYPE,"text/css");
        return getBody(new File(context.getLocalPath()));
    }
}
