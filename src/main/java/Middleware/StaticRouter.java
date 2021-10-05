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
        Optional<Processor> optionalProcessor = processors.stream()
                .filter(processor -> processor.match(context.getUrl()))
                .findAny();
        String path = getLocalPath(context.getUrl());
        Stream<byte[]> body = Stream.empty();
        if(new File(path).exists() && optionalProcessor.isPresent()){
            body=optionalProcessor.get().process(context, path);
        }else{
            context.setStatus(404);
        }
        context.body(body);
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
