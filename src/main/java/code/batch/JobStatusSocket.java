package code.batch;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@WebSocket(path = "/ws/jobs/status/{jobName}")
public class JobStatusSocket {
    // Map to store active connections by job name
    private static final Map<String, WebSocketConnection> jobConnections = new ConcurrentHashMap<>();

    // Called when a new client connects
    @OnOpen
    public void onOpen(WebSocketConnection connection, @PathParam String jobName) {
        // Associate the connection with the job name
        jobConnections.put(jobName, connection);
        Log.info("websocket open...");
    }

    // Called when a client disconnects
    @OnClose
    public void onClose(WebSocketConnection connection, @PathParam String jobName) {
        // Remove the connection
        jobConnections.remove(jobName);
        Log.info("websocket closed...");
    }

    /**
     * Sends the updated HTML fragment to the client listening for the specified job.
     * This method is called by the JobService when a status changes.
     */
    public static void broadcastUpdate(String jobName, String updatedHtml) {
        WebSocketConnection connection = jobConnections.get(jobName);
        if (connection != null && connection.isOpen()) {
            // Use the sendText() method on the connection object
            connection.sendText(updatedHtml);
        }
    }

}
