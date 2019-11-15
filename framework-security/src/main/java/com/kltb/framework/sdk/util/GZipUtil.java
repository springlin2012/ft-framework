/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {
    public GZipUtil() {}

    public static void compress(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        GZIPOutputStream gzOut = new GZIPOutputStream(out);

        int read = 0;
        for (read = in.read(buffer); read > 0; read = in.read(buffer)) {
            gzOut.write(buffer, 0, read);
        }

        gzOut.close();
        in.close();
        gzOut.close();
    }

    public static void decompress(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read = 0;
        GZIPInputStream gzIn = new GZIPInputStream(in);

        for (read = gzIn.read(buffer); read > 0; read = gzIn.read(buffer)) {
            out.write(buffer, 0, read);
        }

        gzIn.close();
        in.close();
        out.close();
    }

    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] var5;
        try {
            compress(bais, baos);
            byte[] output = baos.toByteArray();
            baos.flush();
            var5 = output;
        } finally {
            try {
                baos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                bais.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

        return var5;
    }
}
