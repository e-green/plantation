package org.egreen.plantation.pushserver.client;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Created by dewmal on 3/3/15.
 */
@ClientEndpoint
public class DataPacketWSClient {
    private final ClientEndpointConfig cec;
    private Session userSession = null;
    private MessageHandler messageHandler;

    public DataPacketWSClient(final URI endpointURI) {
        try {
            cec = ClientEndpointConfig.Builder.create().build();
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(final Session userSession) {
        this.userSession = userSession;
        System.out.println(userSession);
    }

    @OnClose
    public void onClose(final Session userSession, final CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(final String message) {
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }

    public void addMessageHandler(final MessageHandler msgHandler) {
        messageHandler = msgHandler;
    }

    public void sendMessage(final String message) {
        userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }


}
