package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suyu
 * @create 2021-09-24-21:04
 */
public class Config {
    public static String ROOT_DIR = System.getProperty("user.dir");
    public static String SEARCH_DIR = ROOT_DIR+File.separator+"source";
    public static String INDEX_PAGE = "index.html";
    public static String CONFIG_DIR = ROOT_DIR + File.separator + "config" + File.separator + "server.config";
    public static String RESPONSE_CONFIG = CONFIG_DIR + File.separator + "response.config";
    public static String CONTENT_TYPE = "content-type";
    public static String IMAGE_TYPE = "image/%s";
    public static String CONTENT_LENGTH = "content-length";
    public static int PORT;
    public static String IP;
    public static int THREADS;

    public static void init() throws IOException {
        Map<String,String> configs = new HashMap<>();
        Files.lines(new File(CONFIG_DIR).toPath())
                .map(line -> line.substring(0,line.length()-1))
                .map(line -> line.split("="))
                .forEach(arrs -> configs.put(arrs[0].trim(),arrs[1].trim()));
        PORT = Integer.parseInt(configs.get("port"));
        IP = configs.get("ip");
        THREADS = Integer.parseInt(configs.get("threads"));
        SEARCH_DIR = configs.get("searchDirectory");
        assert SEARCH_DIR != null;
    }
}
