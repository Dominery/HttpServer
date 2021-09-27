package Middleware;

import Context.Context;
import util.ConsoleViewer;
import Processor.Processor;
import Context.ResponseHeader;

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
        ResponseHeader head;
        Stream<byte[]> body = Stream.empty();
        if(new File(path).exists() && optionalProcessor.isPresent()){
            head = new ResponseHeader(200);
            body=optionalProcessor.get().process(head, context);
            ConsoleViewer.getInstance().viewMessage("success load "+ context.getUrl());
        }else{
            head = new ResponseHeader(404);
            ConsoleViewer.getInstance().viewMessage("not found "+ context.getUrl());
        }
        context.send(Stream.concat(head.getBytes(),body));
    }
    public Router addProcessor(Processor processor){
        processors.add(processor);
        return this;
    }
    public void addProcessors(List<Processor> processors){
        this.processors.addAll(processors);
    }

}
