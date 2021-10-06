package util;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author suyu
 * @create 2021-09-25-15:07
 */
public class ConsoleViewer {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd hh:mm:ss");
    private static ConsoleViewer viewer = null;
    public void viewMessage(String message) {
        show(System.out,message);
    }
    public void err(String message) {
        show(System.err,message);
    }
    private synchronized void show(PrintStream print, String message){
        String time = formatter.format(LocalDateTime.now());
        print.println(time+"--"+message);
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
