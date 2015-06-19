package org.egreen.plantation.api;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.RethinkDBConnection;
import com.rethinkdb.ast.query.RqlQuery;
import com.rethinkdb.ast.query.RqlUtil;
import com.rethinkdb.ast.query.gen.DB;
import com.rethinkdb.model.RqlFunction;
import com.rethinkdb.model.RqlFunction2;
import com.rethinkdb.proto.Q2L;
import com.rethinkdb.response.model.DMLResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.egreen.plantation.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Import log4j classes.

/**
 * Created by dewmal on 2/26/15.
 */
@Repository
public class DBController implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(DBController.class);


    private RethinkDB r;
    private RethinkDBConnection con;

    @Autowired
    @Qualifier(value = "DB_OPTIONS")
    private Options dbOptions;


    public DBController() {
//        loadDBController();
    }

    @PostConstruct
    public void loadDBController() {
        r = RethinkDB.r;
        con = r.connect(dbOptions.getDBHost(), dbOptions.getPort());
    }


    /**
     *
     * @return
     */
    public List<DataPacket> query(DataPacket dataPacket) {

//        RqlQuery rqlQuery=RqlQuery.R.
//        r.table(dataPacket.getTablename())
//                .filter(rqlQuery);





        return null;
    }


    /**
     * Setup Database
     *
     * @return
     */
    public Object setupDB() {
        Object run = r.dbCreate(dbOptions.getDBName()).run(con);
        LOGGER.info(run);
        return run;
    }


    /**
     * GET Value
     *
     * @param dataPacket
     * @return
     */
    public DataPacket get(DataPacket dataPacket) {
        DB db = getDb();
        String tablename = dataPacket.getTablename();
        Map<String, Object> data = db.table(tablename).get(dataPacket.getRow().get("id")).run(con);
        dataPacket.setRow(data);
        return dataPacket;
    }


    /**
     * Save And Update Data Packet
     *
     * @param dataPacket
     * @return
     */
    public String saveUpdateDataPacket(DataPacket dataPacket) {
        DB db = getDb();


        // Get Table Name
        String tablename = dataPacket.getTablename();

        List<String> tbL = db.tableList().run(con);

        boolean isCreated = false;

        for (String s : tbL) {
            LOGGER.info(s);
            if (s.equals(tablename)) {
                isCreated = true;
                break;
            }
        }

        if (!isCreated) {
            db.tableCreate(tablename).run(con);
        }


        // Extract Data from Data Packet
        Map<String, Object> dataPacketRow = dataPacket.getRow();
        DMLResult insertObject;
        String key = "";


        if (dataPacketRow.get("id") != null) {

            dataPacketRow.put("update", Calendar.getInstance().getTimeInMillis());
            //Update
            insertObject = db.
                    table(dataPacket.getTablename())
                    .update(dataPacketRow).run(con);
            key = (String) dataPacket.getRow().get("id");
        } else {
            dataPacketRow.put("create", Calendar.getInstance().getTimeInMillis());
            // Insert
            insertObject = db.
                    table(dataPacket.getTablename())
                    .insert(dataPacketRow).run(con);
            key = insertObject.getGenerated_keys().get(0);
        }

        LOGGER.info(insertObject);


        return key;
    }

    private DB getDb() {
        // Connect to Database
        return r.db(dbOptions.getDBName());
    }

    public RethinkDB getR() {
        return r;
    }

    public void setR(RethinkDB r) {
        this.r = r;
    }

    public RethinkDBConnection getCon() {
        return con;
    }

    public void setCon(RethinkDBConnection con) {
        this.con = con;
    }

}