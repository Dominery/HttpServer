package util;

import Notice.ConsoleViewer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author suyu
 * @create 2021-09-24-13:51
 */
public class ClientSocket {
    private final Socket client;
    private BufferedReader bis;
    private BufferedOutputStream bos;
    public ClientSocket(Socket socket){
        client = socket;
        try {
            client.setKeepAlive(false);
        } catch (SocketException e) {
            ConsoleViewer.getInstance().viewMessage("socket closed suddenly");
        }
    }
    public void send(byte[] data){
        BufferedOutputStream bos = getOutputStream();
        if(bos != null&&!client.isClosed()){
            try{
                bos.write(data);
                bos.flush();
            }catch (IOException e){
                ConsoleViewer.getInstance().viewMessage("error occurred when send message " +
                        "socket had been closed by browser suddenly");
            }
        }
    }
    public List<String> recv(){
        List<String> result = new LinkedList<>();
        BufferedReader bis = getInputStream();
        if(bis != null){
            String line;
            try {
                while ((line = bis.readLine())!=null && line.length()!=0){
                    result.add(line);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return result;
    }
    public void close(){
        try {
            client.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public InetSocketAddress getAddr(){
        return (InetSocketAddress) client.getRemoteSocketAddress();
    }
    private BufferedReader getInputStream(){
        if(bis == null){
            try{
                bis = new BufferedReader(new InputStreamReader(client.getInputStream()));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return bis;
    }
    private BufferedOutputStream getOutputStream(){
        if(bos == null){
            try{
                bos = new BufferedOutputStream(client.getOutputStream());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return bos;
    }
}
