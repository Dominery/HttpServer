package Processor;

import Context.Context;
import Context.ResponseHeader;

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
    public Stream<byte[]> process(ResponseHeader res, Context context) {
        return getBody(new File(context.getLocalPath()));
    }
}
