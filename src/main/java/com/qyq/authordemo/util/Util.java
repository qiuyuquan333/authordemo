package com.qyq.authordemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getCPU() throws IOException {
        StringBuilder sb = new StringBuilder();
        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "CPU", "get", "ProcessorID"});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String next;
        while ((next = reader.readLine()) != null){
            sb.append(next);
        }
        reader.close();
        return sb.toString();
    }

    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        return format;
    }
}
