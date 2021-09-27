import Context.Context;
import Context.RequestInfo;
import Notice.ConsoleViewer;
import Processor.*;
import util.ClientSocket;
import util.Config;
import util.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author suyu
 * @create 2021-09-24-10:29
 */
public class HttpServer implements AutoCloseable{
    private final ServerSocket server;
    private final Router router;
    private HttpServer(int port,Router router) throws IOException{
        server = new ServerSocket(port);
        this.router = router;
    }
    private Runnable serve(ClientSocket client){
        return () -> {
            Context context = new Context(client);
            ConsoleViewer.getInstance().viewMessage(new RequestInfo(client.getAddr(),context.getRequest()).toString());
            router.push(context).forEach(client::send);
            client.close();
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
    public static void main(String[] args) {
        try{
            Config.init();
        }catch (Exception e){
            System.out.println("error occurred in config");
            return;
        }
        Router router = new Router();
        router.addProcessors(Arrays.asList(
                new HtmlProcessor(),
                new CssProcessor(),
                new JsProcessor(),
                new ImageProcessor(),
                new FontsProcessor(),
                new VideoProcessor()
        ));
        try(HttpServer server = new HttpServer(Config.PORT,router)){
            server.run(Config.THREADS);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
