package org.egreen.plantation;

import org.egreen.plantation.socket.Subscribe;
import org.egreen.plantation.socket.SubscribeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dewmal on 3/3/15.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SubscriberPool {

    private Map<String, Subscribe> subscribeMap = new HashMap<>();


    @Autowired
    private SubscribeFactory subscribeFactory;

    public Subscribe get(String path) {
        Subscribe subscribe = subscribeMap.get(path);
        if (subscribe == null) {
            subscribe = subscribeFactory.generate(path);
            subscribeMap.put(path, subscribe);
        }
        return subscribe;
    }

}
