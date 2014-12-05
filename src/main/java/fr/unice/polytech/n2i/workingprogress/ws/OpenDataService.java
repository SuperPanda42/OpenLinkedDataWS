package fr.unice.polytech.n2i.workingprogress.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.unice.polytech.n2i.workingprogress.DataHelper;
import fr.unice.polytech.n2i.workingprogress.model.Question;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Created by clement0210 on 14-12-04.
 */
@Path("/opendata")
public class OpenDataService implements OpenDataServiceInterface{
    @Override
    public Response getAQuestion() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, true);

        Question solution= DataHelper.getQuestion();
        if(solution.getQuestion()==null){
            return Response.noContent().build();
        }
        try {
            String jsonRes=mapper.writeValueAsString(solution);
            return Response.ok(jsonRes).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.noContent().build();
        }

    }

    @Override
    public Response getAQuestionWithKeywords(List<String> keywords) {
        return null;
    }
}
