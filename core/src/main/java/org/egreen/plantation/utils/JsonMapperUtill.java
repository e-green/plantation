package org.egreen.plantation.utils;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dewmal on 3/1/15.
 */
@Component
public class JsonMapperUtill {


    /**
     * Convert Input Stream to Data Map
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public Map<String, Object> getDataMap(InputStream inputStream) throws IOException {
        String json = read(inputStream, 1024);
        return convertToMap(json);
    }


    /**
     * Convert String to Map
     *
     * @param json
     * @return
     * @throws IOException
     */
    public Map<String, Object> convertToMap(String json) throws IOException {
        Map<String, Object> dataMap = new HashMap<>();


        ObjectMapper mapper = new ObjectMapper();


        //convert JSON string to Map
        dataMap = mapper.readValue(json,
                new TypeReference<HashMap<String, String>>() {
                });


        return dataMap;
    }


    /**
     * Read Inputstream to String
     *
     * @param inputStream
     * @param bufferSize
     * @return
     * @throws IOException
     */
    public String read(InputStream inputStream, int bufferSize) throws IOException {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }

        return out.toString();
    }
}
