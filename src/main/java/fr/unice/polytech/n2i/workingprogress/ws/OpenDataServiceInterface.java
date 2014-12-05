package fr.unice.polytech.n2i.workingprogress.ws;

import fr.unice.polytech.n2i.workingprogress.model.Question;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by clement0210 on 14-12-04.
 */

@Produces("application/json")
public interface OpenDataServiceInterface {

    @Path("/question")
    @GET
    public Response getAQuestion();

    @Path("/question")
    @GET
    public Response getAQuestionWithKeywords(@QueryParam("keywords") List<String> keywords);
}
