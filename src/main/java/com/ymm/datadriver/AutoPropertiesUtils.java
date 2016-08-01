package com.ymm.datadriver;

import java.io.*;
import java.util.Properties;

/**
 * Created by litianlong on 2016/6/12.
 */
public class AutoPropertiesUtils {

    public static String getPropertiesValues(String path,String key){
        Properties properties = new Properties();
        InputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            properties.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
