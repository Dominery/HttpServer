import Context.RequestInfo;
import Middleware.ApplyMiddleware;
import Middleware.Router;
import Notice.ConsoleViewer;
import Processor.*;
import util.Config;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author suyu
 * @create 2021-09-27-21:59
 */
public class App {
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
        ApplyMiddleware.use((context,next)->{
            ConsoleViewer.getInstance().viewMessage(new RequestInfo(context.getAddr(),context.getRequest()).toString());
            next.run();
        });
        ApplyMiddleware.use(router);
        try(HttpServer server = new HttpServer(Config.PORT,ApplyMiddleware.build())){
            server.run(Config.THREADS);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
