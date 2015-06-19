package org.egreen.plantation.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.egreen.plantation.socket.DataBroker;
import org.egreen.plantation.utils.JsonMapperUtill;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by dewmal on 2/24/15.
 */
@Component
@Path("/")
public class DBControllerRestAPI implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(DBControllerRestAPI.class);

    @Autowired
    private DataBroker dataBroker;

    @Autowired
    private JsonMapperUtill js;


    @Autowired
    private DBController dbController;

    @Context
    private HttpServletRequest request;

    public DBControllerRestAPI() {

//

    }


    /**
     * GEt ALL
     *
     * @param limit
     * @param offset
     * @return
     */
    @GET
    @Path("all/{child}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DataPacket> all(@PathParam("child") String child, InputStream inputStream) {

        Map<String, Object> dataMap = null;
        try {
            dataMap = js.getDataMap(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataPacket dataPacket = new DataPacket(child, dataMap);
        List<DataPacket> packets = dbController.query(dataPacket);
        return packets;
    }


    /**
     * Get Selected Object
     *
     * @param child
     * @param id
     * @return
     */
    @GET
    @Path("get/{child}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("child") String child, @PathParam("id") String id) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", id);
        DataPacket dataPacket = new DataPacket(child, dataMap);
        dataPacket.setRow(dataMap);
        DataPacket packet = dbController.get(dataPacket);
        return packet.getRow() + "";
    }


    /**
     * Push Object
     *
     * @param child
     * @param inputStream
     * @return
     */
    @POST
    @Path("/push/{child}")
    @Produces("application/json")
    public String getClichedMessage(@PathParam("child") String child, InputStream inputStream) {
        Map<String, Object> dataMap = null;
        try {
            dataMap = js.getDataMap(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataPacket dataPacket = new DataPacket(child, dataMap);
        Object process = dbController.saveUpdateDataPacket(dataPacket);
        dataPacket.getRow().put("id", process);
        dataPacket.getRow().put("_createtime", Calendar.getInstance().getTimeInMillis());

        try {
            JSONObject jsonObject = new JSONObject(dataPacket.getRow());// Send Object
            dataBroker.update(jsonObject.toString(), child);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataPacket.getRow() + "";
    }


}
