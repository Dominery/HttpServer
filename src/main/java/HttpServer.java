import Context.Context;
import Middleware.ApplyMiddleware;
import util.ClientSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author suyu
 * @create 2021-09-24-10:29
 */
public class HttpServer implements AutoCloseable{
    private final ServerSocket server;
    private final ApplyMiddleware middleware;
    HttpServer(int port, ApplyMiddleware middleware) throws IOException{
        server = new ServerSocket(port);
        this.middleware = middleware;
    }
    private Runnable serve(ClientSocket client){
        return () -> {
            Context context = new Context(client);
            middleware.compose(context);
        };
    }
    public void run(int nThreads){
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        while (true){
            try{
                Socket socket = server.accept();
                pool.submit(serve(new ClientSocket(socket)));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void close() throws IOException {
        server.close();
    }
}
