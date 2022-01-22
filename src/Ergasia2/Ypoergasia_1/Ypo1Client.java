package Ergasia2.Ypoergasia_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ypo1Client {
    public static void main(String[] args) throws IOException {
        String serverHostName = "127.0.0.1";
        if (args.length > 0) {
            serverHostName = args[0];
        }
        System.out.println("Attempting to connect to host " + serverHostName + " on port 10007.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echoSocket = new Socket(serverHostName, 10007);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHostName);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        String serverMessage;

        while ((serverMessage = in.readLine()) != null) {
            System.out.println(serverMessage);

            // We assume that the client provides input when the message from server ends with ':' character.
            if (serverMessage.length() > 0 && serverMessage.charAt(serverMessage.length() - 1) == ':') {
                userInput = stdIn.readLine();
                out.println(userInput);
            }

        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();

    }
}
