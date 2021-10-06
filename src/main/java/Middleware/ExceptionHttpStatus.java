package Middleware;

import Context.Context;

import java.util.stream.Stream;

/**
 * @author suyu
 * @create 2021-10-06-14:18
 */
public class ExceptionHttpStatus {
    public static void notFound(Context context, Runnable next){
        respond(context, 400);
    }
    public static void serverError(Context context,Runnable next){
        respond(context, 500);
    }
    private static void respond(Context context, int status){
        context.setStatus(status);
        context.body(Stream.empty());
    }
}
