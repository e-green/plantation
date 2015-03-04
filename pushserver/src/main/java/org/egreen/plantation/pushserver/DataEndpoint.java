package org.egreen.plantation.pushserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.egreen.plantation.api.DataPacket;
import org.egreen.plantation.pushserver.dataprocessors.DataDecoder;
import org.egreen.plantation.pushserver.dataprocessors.DataEncoder;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dewmal on 3/3/15.
 */
@Component
@ServerEndpoint(value = "/bind/{child}",
        encoders = {DataEncoder.class},
        decoders = {DataDecoder.class}
)
public class DataEndpoint {

    private static final Logger LOGGER = LogManager.getLogger(DataEndpoint.class);

    @OnOpen
    public void open(Session session) {
        LOGGER.info(session.getRequestParameterMap());
        LOGGER.info(session.getQueryString());
    }


    @OnMessage
    public void message(@PathParam("child") String child, DataPacket message, Session client) throws IOException, EncodeException {
        LOGGER.info(client.getRequestParameterMap());
        LOGGER.info(client.getQueryString());

        Map<String, Object> dataMap = message.getRow();// jsonMapperUtill.getDataMap(message);
        message.setTablename(child);

        LOGGER.info("child: " + child);
        LOGGER.info("message: " + dataMap);
        for (Session peer : client.getOpenSessions()) {
            Map<String, String> pathParameters = peer.getPathParameters();
            boolean child1 = pathParameters.get("child").equals(child);
            if (child1)
                peer.getBasicRemote().sendObject(message);
        }
    }

}
