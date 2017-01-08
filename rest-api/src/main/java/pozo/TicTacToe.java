package pozo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationPath(ApiRoot.ROOT)
@Path("tictactoe")
public class TicTacToe {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayhello() {

        return "hello";
    }
}
