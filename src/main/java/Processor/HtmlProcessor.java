package Processor;

import Context.Response;
import util.Config;
import util.URL;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-25-10:07
 */
public class HtmlProcessor extends Processor {
    public HtmlProcessor(){
        regex = Pattern.compile("/$|.html$");
    }
    @Override
    public Stream<byte[]> process(Response res, URL uri) {
        res.addAttr(Config.CONTENT_TYPE,"text/html;charset=utf-8");
        String path = uri.getLocalPath();
        File file = path.endsWith(File.separator)?new File(path+Config.INDEX_PAGE):new File(path);
        return getBody(file);
    }

}
