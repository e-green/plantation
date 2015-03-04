package org.egreen.plantation.socket;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dewmal on 3/3/15.
 */
public interface Subscribe {

    void init();

    String getPath();

    void onUpdate(Object message);

    void sendMessage(Object message) throws IOException, ExecutionException, InterruptedException;
}
