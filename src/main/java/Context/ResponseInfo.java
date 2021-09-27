package Context;

/**
 * @author suyu
 * @create 2021-09-27-23:16
 */
public class ResponseInfo {
    private final String method;
    private final String url;
    private final int status;
    private final String pattern = "-> %s %s %d";

    public ResponseInfo(String method, String url, int status) {
        this.method = method;
        this.url = url;
        this.status = status;
    }

    @Override
    public String toString() {
        return  String.format(pattern,method,url,status);
    }
}
