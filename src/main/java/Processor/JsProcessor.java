package Processor;

import header.Response;
import util.Config;
import util.Uri;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-25-10:16
 */
public class JsProcessor extends Processor{
    public JsProcessor(){
        regex = Pattern.compile(".js$");
    }
    @Override
    public Stream<byte[]> process(Response res, Uri uri) {
        res.addAttr(Config.CONTENT_TYPE,"text/javascript");
        return getBody(new File(uri.getLocalPath()));
    }
}
