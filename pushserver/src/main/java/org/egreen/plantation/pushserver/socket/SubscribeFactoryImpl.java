package org.egreen.plantation.pushserver.socket;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.providers.grizzly.GrizzlyAsyncHttpProvider;
import com.ning.http.client.websocket.WebSocket;
import com.ning.http.client.websocket.WebSocketUpgradeHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.egreen.plantation.socket.Subscribe;
import org.egreen.plantation.socket.SubscribeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dewmal on 3/3/15.
 */
@Service
public class SubscribeFactoryImpl implements SubscribeFactory {

    private static final Logger LOGGER = LogManager.getLogger(SubscribeFactory.class);


    @Autowired
    @Qualifier(value = "WEB_SOCKET_PATH")
    private String WEB_SOCKET_PATH;


    @Override
    public Subscribe generate(final String path) {
        WebSocketUpgradeHandler.Builder builder = new WebSocketUpgradeHandler.Builder();
        Subscribe subscribe = new WebSocketSubscriber(builder, path);
        subscribe.init();
        return subscribe;
    }


    /**
     * Web socket Data Subscriber
     */
    public class WebSocketSubscriber extends WebSocketUpgradeHandler implements Subscribe {

        private final String path;
        private AsyncHttpClient asyncClient;
        private WebSocket socket;

        protected WebSocketSubscriber(Builder b, String path) {
            super(b);
            this.path = WEB_SOCKET_PATH + "bind/" + path; // Create Path
        }


        /**
         * Initializing
         */
        @Override
        public void init() {
            AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder().build();
            asyncClient = new AsyncHttpClient(new GrizzlyAsyncHttpProvider(config), config);
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public void onUpdate(Object message) {
        }




        @Override
        public void sendMessage(Object message) throws IOException, ExecutionException, InterruptedException {
            socket = asyncClient.preparePost(getPath()).execute(this).get();
            if (socket != null) {
                LOGGER.info(" IS Socket Open " + socket.isOpen());
                socket.sendTextMessage((message + ""));
                socket.close();
            }
        }
    }
}
