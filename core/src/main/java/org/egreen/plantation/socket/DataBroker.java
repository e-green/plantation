package org.egreen.plantation.socket;

import org.egreen.plantation.SubscriberPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dewmal on 3/2/15.
 */
@Service
public class DataBroker {


    @Autowired
    private SubscriberPool subscriberPool;


    public void update(Object object, String path) throws InterruptedException, ExecutionException, IOException {
        subscriberPool.get(path).sendMessage(object);
    }


}
