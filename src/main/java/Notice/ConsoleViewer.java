package Notice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author suyu
 * @create 2021-09-25-15:07
 */
public class ConsoleViewer {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss");
    private static ConsoleViewer viewer = null;
    public synchronized void viewMessage(String message) {
        String time = formatter.format(LocalDateTime.now());
        System.out.println(time+"-"+message);
    }
    public static ConsoleViewer getInstance(){
        if(viewer ==null){
            synchronized (ConsoleViewer.class){
                if (viewer==null){
                    viewer = new ConsoleViewer();
                }
            }
        }
        return viewer;
    }
}
