package org.caller.mhealth.tools;

import java.io.*;
import java.net.HttpURLConnection;


/**
 * Created by wl on 2016/10/31.
 */

public final class StreamUtil {

    public static void close(Object stream){
        if (stream != null) {
            try {
                if (stream instanceof Closeable) {
                    ((Closeable) stream).close();
                }else if(stream instanceof HttpURLConnection){
                    ((HttpURLConnection) stream).disconnect();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static byte[] readStream(InputStream in){
        byte[] ret = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        readStream(in, out);

        ret = out.toByteArray();

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static long readStream(InputStream in, OutputStream out){
        long ret  = 0;

        if (in != null && out != null) {

            byte[] buf = new byte[1024];
            int len;

            try {
                while (true) {
                    len = in.read(buf);
                    if (len == -1) {
                        break;
                    }
                    out.write(buf, 0, len);
                    ret += len;
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        return ret;
    }

}
