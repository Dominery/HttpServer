package Middleware;

import Context.Context;
import Processor.Processor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-24-21:05
 */
public class StaticRouter implements Middleware{
    private final List<Processor> processors = new LinkedList<>();
    private final String staticDir;
    public StaticRouter(String root,String searchDir){
        staticDir = root + File.separator + searchDir;
    }
    public void go(Context context, Runnable next){
        String path = getLocalPath(context.getUrl());
        if(!new File(path).exists()){
            next.run();
            return;
        }
        Optional<Processor> optionalProcessor = processors.stream()
                .filter(processor -> processor.match(context.getUrl()))
                .findAny();
        if(optionalProcessor.isPresent()){
            Stream<byte[]> body=optionalProcessor.get().process(context, path);
            context.body(body);
        }else{
            ExceptionHttpStatus.serverError(context,next);
            throw new RuntimeException("no processor for "+context.getUrl());
        }
    }
    public StaticRouter addProcessor(Processor processor){
        processors.add(processor);
        return this;
    }
    public void addProcessors(List<Processor> processors){
        this.processors.addAll(processors);
    }
    private String getLocalPath(String url){
        return staticDir + File.separator + url.replace("/",File.separator);
    }
}
