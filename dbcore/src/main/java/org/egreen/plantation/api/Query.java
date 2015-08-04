package org.egreen.plantation.api;

import java.util.Map;

/**
 * Created by dewmal on 3/2/15.
 */
public class Query {

    private long offest;
    private long limit;
    private String order;
    private String query;

    public Query(String query) {
        this.query = query;
    }

    public Query(Map<String, Object> dataMap) {
        offest = (Long) dataMap.getOrDefault("start", 0);
        limit = (Long) dataMap.getOrDefault("limit", 10);
        order = (String) dataMap.getOrDefault("order", "_createtime");
        query = (String) dataMap.getOrDefault("query", null);
        query = (String) dataMap.getOrDefault("query", null);
    }



}
