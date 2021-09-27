package util;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author suyu
 * @create 2021-09-25-8:59
 */
public class ByteStream {
    public ByteStream(){
    }

    private Stream<byte[]> bytes(BufferedInputStream bis){
        Iterator<byte[]> iter = new Iterator<byte[]>() {
            byte[] nextData = null;
            final byte[] cathe = new byte[1024*8];

            @Override
            public boolean hasNext() {
                if (nextData != null) {
                    return true;
                } else {
                    try {
                        int len;
                        if((len=bis.read(cathe))!=-1){
                            nextData = Arrays.copyOf(cathe,len);
                        }
                        return (len != -1);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            }

            @Override
            public byte[] next() {
                if (nextData != null || hasNext()) {
                    byte[] data = nextData;
                    nextData = null;
                    return data;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public Stream<byte[]> bytes(File file) throws IOException{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        try {
            return bytes(bis).onClose(asUncheckedRunnable(bis));
        } catch (Error|RuntimeException e) {
            try {
                bis.close();
            } catch (IOException ex) {
                try {
                    e.addSuppressed(ex);
                } catch (Throwable ignore) {}
            }
            throw e;
        }
    }
    private Runnable asUncheckedRunnable(Closeable c) {
        return () -> {
            try {
                c.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}