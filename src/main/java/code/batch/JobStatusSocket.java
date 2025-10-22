package code.batch;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@WebSocket(path = "/ws/jobs/status/{jobName}")
public class JobStatusSocket {
    private final Map<String, WebSocketConnection> jobConnections = new ConcurrentHashMap<>();

    @OnOpen(broadcast = true)
    public void onOpen(WebSocketConnection connection, @PathParam String jobName) {
        jobConnections.put(jobName, connection);
        Log.info("websocket open for " + jobName);
    }

    @OnClose
    public void onClose(WebSocketConnection connection, @PathParam String jobName) {
        jobConnections.remove(jobName);
        Log.info("websocket closed for " + jobName);
    }

    /**
     * Sends the updated HTML fragment to the client listening for the specified job.
     * This method is called by the JobService or Listener.
     */
    public void broadcastUpdate(String jobName, String updatedHtml) {
        WebSocketConnection connection = jobConnections.get(jobName);
        if (connection != null && connection.isOpen()) {
            Log.info("sending text broadcast for job: " + jobName);
            connection.sendTextAndAwait(updatedHtml);
        } else {
            Log.warn("No active connection found for job: " + jobName + ". Update failed.");
        }
    }
}