package org.egreen.plantation.pushserver.dataprocessors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.egreen.plantation.api.DataPacket;
import org.egreen.plantation.utils.JsonMapperUtill;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dewmal on 3/3/15.
 */
public class DataDecoder implements Decoder.Text<DataPacket> {
    private static final Logger LOGGER = LogManager.getLogger(DataDecoder.class);

    private JsonMapperUtill jsonMapperUtill;


    public DataDecoder() {
        this.jsonMapperUtill = new JsonMapperUtill();
    }

    @Override
    public DataPacket decode(String s) throws DecodeException {
        Map<String, Object> row = new HashMap<>();
        try {
            row = jsonMapperUtill.convertToMap(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataPacket dataObject = new DataPacket("", row);
        return dataObject;
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            LOGGER.info(jsonMessage);
            Map<String, Object> row = jsonMapperUtill.convertToMap(jsonMessage);
            LOGGER.info(row);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
