package org.egreen.plantation.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by dewmal on 2/26/15.
 */
@Component
@Path("/setup")
public class SetupProject implements Serializable {

    @Autowired
    protected DBController dbController;


    @POST
    @Produces("application/json")
    public String getClichedMessage(InputStream inputStream) {
        Object setupDB = dbController.setupDB();
        return " " + setupDB;
    }
}
