package org.egreen.plantation.api;

import java.util.Map;

/**
 * Created by dewmal on 2/26/15.
 */

public class DataPacket {

    private String tablename;
    private Map<String, Object> row;


    public DataPacket(String tablename, Map<String, Object> row) {
        this.tablename = tablename;
        this.row = row;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }


    public Map<String, Object> getRow() {
        return row;
    }

    public void setRow(Map<String, Object> row) {
        this.row = row;
    }
}
