import Middleware.ApplyMiddleware;
import Middleware.ExceptionHttpStatus;
import Middleware.StaticRouter;
import Processor.*;
import util.Config;
import util.ConsoleViewer;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @author suyu
 * @create 2021-09-27-21:59
 */
public class App {
    private StaticRouter buildRouter(){
        StaticRouter staticRouter = new StaticRouter(Config.ROOT_DIR, Config.SEARCH_DIR);
        staticRouter.addProcessors(Arrays.asList(
                new HtmlProcessor(),
                new CssProcessor(),
                new JsProcessor(),
                new ImageProcessor(),
                new FontsProcessor(),
                new VideoProcessor()
        ));
        return staticRouter;
    }
    private ApplyMiddleware buildMiddleware(StaticRouter staticRouter){
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
        ApplyMiddleware.use(staticRouter, ExceptionHttpStatus::notFound);
        ApplyMiddleware.on(e -> {
           ConsoleViewer.getInstance().err(e.getMessage());
        });
        return ApplyMiddleware.build();
    }
    public void run(){
        try{
            Config.init();
        }catch (Exception e){
            System.err.println("error occurred in config");
            return;
        }
        try(HttpServer server = new HttpServer(Config.IP,Config.PORT,buildMiddleware(buildRouter()))){
            server.run(Config.THREADS);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new App().run();
    }
}
