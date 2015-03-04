package org.egreen.plantation.api;

import java.util.List;

/**
 * Created by dewmal on 3/2/15.
 */
public class Query {

    private int limit;
    private int start;
    private String order;


    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public class Where {
        private String feild;
        private String query;
        private String condition;


    }

}
