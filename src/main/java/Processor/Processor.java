package Processor;

import Context.Context;
import util.ByteStream;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-24-21:06
 */
public abstract class Processor {
    protected Pattern regex = Pattern.compile("");
    public abstract Stream<byte[]> process(Context context, String localPath);
    public boolean match(String uriFile){
        return regex.matcher(uriFile).find();
    }
    protected Stream<byte[]> getBody(File file){
        try {
            return new ByteStream().bytes(file);
        } catch (IOException e) {
            throw new RuntimeException("error occurred when open file "+file.getName());
        }
    }
}
