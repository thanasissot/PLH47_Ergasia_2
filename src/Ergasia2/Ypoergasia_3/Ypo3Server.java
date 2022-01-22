package Ergasia2.Ypoergasia_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Ypo3Server {
    protected Socket clientSocket;
    private int STORAGE;

    // constructor, creates a Server that listens to 2 PORT
    // creates 2 new instances of inner class OpenSocket and AcceptConnection which implement Runnable interface
    // used for handling the logic of the server
    public Ypo3Server(int PORT1, int PORT2){
        this.STORAGE = new Random().nextInt(1000) + 1;

        OpenSocket socket_a = new OpenSocket(PORT1);
        OpenSocket socket_b = new OpenSocket(PORT2);

        Thread a = new Thread(socket_a);
        Thread b = new Thread(socket_b);

        a.start();
        b.start();
    }

    // keeps listening to PORT and waiting for clients to connect
    public class OpenSocket implements Runnable {
        public int PORT;

        public OpenSocket(int PORT) {
            this.PORT = PORT;
        }

        @Override
        public void run() {
            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Connection Socket Created");
                try {
                    while (true) {
                        System.out.println("Waiting for Connection");
                        // when client connects creates a new Thread instance of AcceptConnection
                        // so that it handles the logic
                        new AcceptConnection(serverSocket.accept());
                    }
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + PORT);
                System.exit(1);
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Could not close port: " + PORT);
                    System.exit(1);
                }
            }
        }
    }

    // every client connected is handled here
    // privedes input and output of client and handles the value sent according to OPERATOR String
    public class AcceptConnection extends Thread{
        protected Socket clientSocket;

        public AcceptConnection(Socket clientSocket){
            this.clientSocket = clientSocket;
            start();
        }

        @Override
        public void run() {
            System.out.println("Client Connection successful");
            System.out.println("Waiting for input....");

            try {
                // output to clients
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                // get the input stream from the connected socket
                InputStream inputStream = clientSocket.getInputStream();
                // create a DataInputStream so we can read data from it.
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                // output to client
                out.println("\nEntering value to server");
                try {
                    // clients input used to get OPERATOR and VALUE
                    String[] inputs = dataInputStream.readUTF().split(" ");
                    String operation = inputs[0];
                    int value = Integer.parseInt(inputs[1]);

                    // helper method for handling ADD/SUB and value input cases
                    handleInput(operation, value, out);

                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("An exception was thrown. Disconnecting.");
                }

                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Client disconnected");
        }
    }

    // helper method to handle logic of Server, adding or subtracting to STORAGE value
    // and some checking of Operands and limits of transaction
    public void handleInput(String operation, int value, PrintWriter out) {
        if (!(operation.equals("ADD") || operation.equals("SUB"))
                || (value < 10 || value > 100)) {
            out.println("Something went wrong. Connection terminating.");
        }

        if (operation.equals("ADD")) {
            if ((STORAGE + value) > 1000) {
                out.println("Could not add " + value + " to Storage. Surpasses value of 1000.");
            }
            else {
                STORAGE += value;
                out.println("Operation confirmed. STORAGE is " + STORAGE);
            }
        }
        else {
            if ((STORAGE - value) < 1) {
                out.println("Could not subtract " + value + " to Storage. Value below 1.");
            }
            else {
                STORAGE -= value;
                out.println("Operation confirmed. STORAGE is " + STORAGE);
            }
        }
    }
}
