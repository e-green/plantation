package org.egreen.plantation.socket;

/**
 * Created by dewmal on 3/3/15.
 */
public interface SubscribeFactory {
    Subscribe generate(String path);
}
