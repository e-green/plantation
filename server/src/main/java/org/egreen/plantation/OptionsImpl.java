package org.egreen.plantation;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by dewmal on 3/4/15.
 */
@Component
@Qualifier(value = "DB_OPTIONS")
@Scope(value = "singleton")
public class OptionsImpl extends Properties implements Options {

    private static final String DB_NAME = "DB.NAME";
    private static final String DB_HOST_NAME = "DB.HOST";
    private static final String DB_PORT = "DB.PORT";

//    private final String path;


    public OptionsImpl()  {

        String path = System.getProperty("user.dir");
        //System.out.println(property);
//        URL resource = OptionsImpl.class.getResource(".");
       // System.out.println(resource.toString());

        try {
            load(new FileInputStream(path + "/options.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public String getDBName() {
        return getProperty(DB_NAME, "test");
    }

    @Override
    public String getDBHost() {
        return getProperty(DB_HOST_NAME, "localhost");
    }

    @Override
    public int getPort() {
        return Integer.parseInt(getProperty(DB_PORT, "28015"));
    }
}
