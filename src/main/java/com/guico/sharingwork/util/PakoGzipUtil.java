package com.guico.sharingwork.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PakoGzipUtil { /**
 * @param str：正常的字符串
 * @return 压缩字符串 * @throws IOException
 */
public static String compress(String str) throws IOException {
    if (str == null || str.length() == 0) {
        return str;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip = new GZIPOutputStream(out);
    gzip.write(str.getBytes());
    gzip.close();
    return out.toString("ISO-8859-1");
}


    /**
     * @param str：类型为：
     * @return 解压字符串  生成正常字符串。
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }

    /**
     * @param jsUriStr :字符串类型为：
     * @return 生成正常字符串
     * @throws IOException
     */
    public static String  unCompressURI(String jsUriStr) throws IOException {
        String gzipCompress=uncompress(jsUriStr);
        String result= URLDecoder.decode(gzipCompress,"UTF-8");

        return result;
    }
    /**
     * @param strData :字符串类型为： 正常字符串
     * @return 生成字符串类型为：
     * @throws IOException
     */
    public static String  compress2URI(String strData) throws IOException {
        String encodeGzip=compress(strData);
        String jsUriStr= URLEncoder.encode(encodeGzip,"UTF-8");
        return jsUriStr;
    }
}
