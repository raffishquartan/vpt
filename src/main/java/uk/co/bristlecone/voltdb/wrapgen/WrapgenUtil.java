package uk.co.bristlecone.voltdb.wrapgen;

import java.util.concurrent.CompletableFuture;

import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

public class WrapgenUtil {
  private WrapgenUtil() {
    // private to hide
  }

  /**
   * Return a {@link ProcedureCallback} that stores its results or an exception in <code>cf</code>
   * 
   * @param cf The CompletableFuture to store the result in
   * @return A ProcedureCallback instance which can be passed to {@link Client#callProcedure} for async execution
   */
  public static ProcedureCallback getHandler(CompletableFuture<ClientResponse> cf) {
    return (ClientResponse resp) -> {
      if(resp.getStatus() == ClientResponse.SUCCESS) {
        cf.complete(resp);
      } else {
        cf.completeExceptionally(new RuntimeException(
            String.format("ClientResponse is not SUCCESS: %d %s", resp.getStatus(), resp.getStatusString())));
      }
    };
  }
}
