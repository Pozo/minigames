package pozo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationPath(ApiRoot.ROOT)
@Path("battleships")
public class BattleShips {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayhello() {

        return "for the horde";
    }
}
