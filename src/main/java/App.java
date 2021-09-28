import Middleware.ApplyMiddleware;
import Middleware.Router;
import util.ConsoleViewer;
import Processor.*;
import util.Config;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @author suyu
 * @create 2021-09-27-21:59
 */
public class App {
    private Router buildRouter(){
        Router router = new Router();
        router.addProcessors(Arrays.asList(
                new HtmlProcessor(),
                new CssProcessor(),
                new JsProcessor(),
                new ImageProcessor(),
                new FontsProcessor(),
                new VideoProcessor()
        ));
        return router;
    }
    private ApplyMiddleware buildMiddleware(Router router){
        ApplyMiddleware.use((context,next)->{
            ConsoleViewer.getInstance().viewMessage(context.getReq());
            next.run();
        });
        ApplyMiddleware.use((context, next)->{
            Instant start = Instant.now();
            next.run();
            Instant end = Instant.now();
            Duration between = Duration.between(start, end);
            ConsoleViewer.getInstance().viewMessage(context.getRes()+ " "+ between.toMillis() + " ms");
        });
        ApplyMiddleware.use(router);
        ApplyMiddleware.on(e -> {
           ConsoleViewer.getInstance().viewMessage(e.getMessage());
        });
        return ApplyMiddleware.build();
    }
    public void run(){
        try{
            Config.init();
        }catch (Exception e){
            System.out.println("error occurred in config");
            return;
        }
        try(HttpServer server = new HttpServer(Config.PORT,buildMiddleware(buildRouter()))){
            server.run(Config.THREADS);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new App().run();
    }
}
