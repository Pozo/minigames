package pozo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@ApplicationPath(ApiRoot.ROOT)
@Path("games")
public class GameRoom {
    // wrong approach, this class is stateless
    private final List<String> games = new ArrayList<>();
    @Context
    private HttpServletRequest request;

    public GameRoom() {
        System.out.println("new instance creation");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> list() {
        System.out.println("request = " + request);
        System.out.println("games = " + games);
        return games;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public boolean join(@FormParam("id") int id) {
        games.add(String.valueOf(id));
        System.out.println("games = " + games);
        return true;

    }
}
