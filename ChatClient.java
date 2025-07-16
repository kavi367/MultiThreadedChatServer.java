import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to chat server!");

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = serverInput.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Server disconnected.");
                }
            });

            // Thread to send messages to server
            Thread writeThread = new Thread(() -> {
                try {
                    String userMsg;
                    while ((userMsg = userInput.readLine()) != null) {
                        output.println(userMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Error sending message.");
                }
            });

            readThread.start();
            writeThread.start();

        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
        }
    }
}
