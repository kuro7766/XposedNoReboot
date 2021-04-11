package com.example.xposednoreboot.Util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileOperator {
    public static String ROOT = "/sdcard/";
    private String filePath;

    public FileOperator(String filePath) {
        if (filePath.startsWith(ROOT))
            this.filePath = filePath;
        else
            this.filePath = ROOT + filePath;
    }

    public String read() {
        StringBuilder sb = new StringBuilder();
        boolean appendN = false;
        try {
            FileReader in = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(in);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
                appendN = true;
            }
            in.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sb.length() > 1 && appendN)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String read(InputStream in) {
        StringBuilder sb = new StringBuilder();
        boolean appendN = false;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
                appendN = true;
            }
            bufferedReader.close();
        } catch (IOException e) {
            return null;
        }
        if (sb.length() > 1 && appendN)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void write(String s) {
        //默认覆盖
        write(s, false);
    }

    public void write(String s, boolean append) {
        try {
            FileOutputStream o = new FileOutputStream(filePath, append);
            o.write(s.getBytes());
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
