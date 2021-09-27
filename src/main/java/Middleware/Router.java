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
public class Router implements Middleware{
    private final List<Processor> processors = new LinkedList<>();
    public void go(Context context, Runnable next){
        Optional<Processor> optionalProcessor = processors.stream()
                .filter(processor -> processor.match(context.getUrl()))
                .findAny();
        String path = context.getLocalPath();
        Stream<byte[]> body = Stream.empty();
        if(new File(path).exists() && optionalProcessor.isPresent()){
            body=optionalProcessor.get().process(context);
        }else{
            context.setStatus(404);
        }
        context.body(body);
    }
    public Router addProcessor(Processor processor){
        processors.add(processor);
        return this;
    }
    public void addProcessors(List<Processor> processors){
        this.processors.addAll(processors);
    }

}
