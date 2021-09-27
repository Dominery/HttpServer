package Middleware;

import Context.Context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author suyu
 * @create 2021-09-27-21:26
 */
public class ApplyMiddleware {
    private static final List<Middleware> shareMiddlewares = new LinkedList<>();
    private final List<Middleware> middlewares = new ArrayList<Middleware>();
    private ApplyMiddleware(){
        middlewares.addAll(shareMiddlewares);
    }
    public void compose(Context context){
        dispatch(0,context);
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
    public static ApplyMiddleware build(){
        return new ApplyMiddleware();
    }
}
