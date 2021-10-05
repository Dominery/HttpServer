package Processor;

import Context.Context;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-25-12:00
 */
public class VideoProcessor extends Processor{
    public VideoProcessor(){
        regex = Pattern.compile(".(mp4)$");
    }
    @Override
    public Stream<byte[]> process(Context context, String localPath) {
        return getBody(new File(localPath));
    }
}
