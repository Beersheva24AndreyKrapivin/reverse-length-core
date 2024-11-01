package telran.net;

import java.net.*;
import java.io.*;
import org.json.JSONObject;

public class Main {
    private static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                JSONObject jsonObj = new JSONObject(line);
                String type = jsonObj.getString("type");
                String originalString = jsonObj.getString("data");
                if (type.equals("reverse")) {
                    String reverseString = new StringBuilder(originalString).reverse().toString();
                    writer.printf("Reverse string for %s is: %s\n", originalString, reverseString);    
                } else if (type.equals("length")) {
                    writer.printf("Lenght string %s = %d\n", originalString, originalString.length());
                }
            }
        } catch (Exception e) {
            System.out.println("client closed connection anormally");
        }
    }
}