package Processor;

import header.Response;
import util.Uri;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-25-10:21
 */
public class FontsProcessor extends Processor{
    public FontsProcessor(){
        regex = Pattern.compile(".(ttf|woff|woff2)$");
    }
    @Override
    public Stream<byte[]> process(Response res, Uri uri) {
        return getBody(new File(uri.getLocalPath()));
    }
}
