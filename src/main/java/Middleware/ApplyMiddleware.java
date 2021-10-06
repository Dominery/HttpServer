package Middleware;

import Context.Context;

import java.util.*;

/**
 * @author suyu
 * @create 2021-09-27-21:26
 */
public class ApplyMiddleware {
    private static final List<Middleware> shareMiddlewares = new LinkedList<>();
    private static final Deque<Middleware> lastMiddlewares = new LinkedList<>();
    private static Optional<ErrorHandler> errorHandler;
    private final List<Middleware> middlewares = new ArrayList<Middleware>();
    private ApplyMiddleware(){
        middlewares.addAll(shareMiddlewares);
        middlewares.addAll(lastMiddlewares);
    }
    public void compose(Context context){
        try {
            dispatch(0, context);
        }catch (Exception e){
            errorHandler.ifPresent(handler -> handler.handle(e));
        }
    }
    private void dispatch(int i, Context context){
        if(i == middlewares.size())return;
        Middleware fn = middlewares.get(i);
        fn.go(context,()->{
            dispatch(i+1,context);
        });
    }
    public static void use(Middleware middleware){
        shareMiddlewares.add(middleware);
    }
    public static void use(Middleware middleware,Middleware lastMiddleware){
        shareMiddlewares.add(middleware);
        lastMiddlewares.addFirst(lastMiddleware);
    }
    public static void on(ErrorHandler handler){errorHandler = Optional.ofNullable(handler);}
    public static ApplyMiddleware build(){
        return new ApplyMiddleware();
    }
}
