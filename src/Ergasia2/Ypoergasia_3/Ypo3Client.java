package Ergasia2.Ypoergasia_3;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ypo3Client extends Thread{
    private final int clientNo;
    private static final String serverHostName = "127.0.0.1";
    private static Random rand = new Random();
    private String operation;
    private List<Integer> portList;


    public Ypo3Client(String operation, List<Integer> portList, int clientNo) {
        this.operation = operation;
        this.portList = portList;
        this.clientNo = clientNo;
    }

    @Override
    public void run(){
        int value = rand.nextInt(91) + 10;
        Socket echoSocket = null;
        BufferedReader in = null;
        int index;
        OutputStream outputStream;
        DataOutputStream dataOutputStream;

        while (true) {
            try {
                index = randomIndex(portList);
                System.out.println("Attempting to connect to host " + serverHostName + " on port " + portList.get(index));
                echoSocket = new Socket(serverHostName, portList.get(index));

                // get the output stream from the socket.
                outputStream = echoSocket.getOutputStream();
                // create a data output stream from the output stream so we can send data through it
                dataOutputStream = new DataOutputStream(outputStream);
                System.out.println(value + " sent to Server's Storage.");

                // write the message we want to send with ADD or SUB depending on PRODUCER/CONSUMER instance
                dataOutputStream.writeUTF(this.operation + " " + value);
                dataOutputStream.flush();

                // get server message
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    if (serverMessage.length() == 0) continue;
                    System.out.println("Server: " + serverMessage);
                }

                in.close();
                dataOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Client"+clientNo+": Couldn't connect");
                System.exit(1);
            }

            try {
                echoSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println();
            // make execution halt for 1 to 10 seconds and restart
            try {
                System.out.println("Client"+clientNo+": Disconnected from Server");
                Thread.sleep(1000 * new Random().nextInt(10) + 1);
                System.out.println("Client"+clientNo+": Reconnecting to a different server");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // helper method returns random index of List
    // used to create a pseudo-random next choice of what port to connect to
    public int randomIndex (List<Integer> intList) throws Exception {
        if (intList != null) {
            int size = intList.size();
            return new Random().nextInt(size);
        }

        throw new Exception("Empty PORT List");
    }

}
