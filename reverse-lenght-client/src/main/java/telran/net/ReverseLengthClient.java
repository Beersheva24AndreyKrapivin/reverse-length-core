package telran.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONObject;

public class ReverseLengthClient {
    private Socket socket;
    private PrintStream writer;
    private BufferedReader reader;

    public ReverseLengthClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            writer = new PrintStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String sendAndReceive(String string, String type) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("type", type);
            jsonObj.put("data", string);
            writer.println(jsonObj.toString());
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
