package Middleware;

import Context.Context;

/**
 * @author suyu
 * @create 2021-09-27-20:23
 */
public interface  Middleware {
    void go(Context context,Runnable next);
}
