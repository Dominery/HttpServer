package util;

import Context.Context;
import Notice.ConsoleViewer;
import Processor.Processor;
import Context.Response;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-09-24-21:05
 */
public class Router {
    private final List<Processor> processors = new LinkedList<>();
    public Stream<byte[]> push(Context context){
        Optional<Processor> optionalProcessor = processors.stream()
                .filter(processor -> processor.match(context.getUrl()))
                .findAny();
        String path = context.getLocalPath();
        Response head;
        Stream<byte[]> body = Stream.empty();
        if(new File(path).exists() && optionalProcessor.isPresent()){
            head = new Response(200);
            body=optionalProcessor.get().process(head, context);
            ConsoleViewer.getInstance().viewMessage("success load "+ context.getUrl());
        }else{
            head = new Response(404);
            ConsoleViewer.getInstance().viewMessage("not found "+ context.getUrl());
        }
        return Stream.concat(head.getBytes(),body);
    }
    public Router addProcessor(Processor processor){
        processors.add(processor);
        return this;
    }
    public void addProcessors(List<Processor> processors){
        this.processors.addAll(processors);
    }

}
