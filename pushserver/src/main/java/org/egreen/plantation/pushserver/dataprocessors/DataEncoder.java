package org.egreen.plantation.pushserver.dataprocessors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.egreen.plantation.api.DataPacket;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Map;

/**
 * Created by dewmal on 3/3/15.
 */
public class DataEncoder implements Encoder.Text<DataPacket> {

    private static final Logger LOGGER = LogManager.getLogger(DataEncoder.class);

    @Override
    public String encode(DataPacket object) throws EncodeException {
        Map<String, Object> row = object.getRow();
        JSONObject jsonObject = new JSONObject(row);
        return jsonObject.toString();
    }

    @Override
    public void init(EndpointConfig config) {
        LOGGER.info("init method called");

    }

    @Override
    public void destroy() {
        LOGGER.info("destroy method called");
    }
}
