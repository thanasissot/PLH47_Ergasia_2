package Ergasia2.Ypoergasia_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Ypo1Server  {
    public static void main(String[]args) throws IOException{
        Hashtable<Integer, Integer> table = new Hashtable<>((int)Math.pow(2, 20));
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10007);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10007");
            System.exit(1);
        }

        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }

        System.out.println("Connection successful");
        System.out.println("Waiting for input....");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;

        while (true) {
            out.println("\nEnter 2 or 3 integer comma-separated values like \"A, B, C\" or \"A,B\" :");
            inputLine = in.readLine();

            // inputs is being assigned to a List<Integer> from getInputs static method
            // the assignment returns the value of List<Integer> which is being used by
            // clearInputs static method to check various scenarios about wrong input numbers
            // exceptions are thrown if conditions are met and finally if
            // something went wrong the error is being printed and the loop restarts
            List<Integer> inputs;
            try {
                clearInputs(inputs = getInputs(inputLine));
            }
            catch (NumberFormatException e){
                out.println("A Value entered could not be parsed into an Integer");
                out.println("Invalid input try again");
                continue;
            }
            catch (Exception e) {
                out.println(e.getMessage());
                out.println("Invalid input try again");
                continue;
            }

            int A = inputs.get(0);
            int B = inputs.get(1);
            if (A == 0 & B == 0) break;

            switch (A) {
                case 1: // INSERT KEY / VALUE
                    int C = inputs.get(2);
                    if(table.containsKey(B)){
                        table.replace(B, C);
                    }
                    else {
                        table.put(B, C);
                    }
                    out.println(1);
                    break;

                case 2: // DELETE VALUE if EXISTS
                    if (table.containsKey(B)){
                        table.remove(B);
                        out.println(1);
                    }
                    else {
                        out.println(0);
                    }
                    break;

                case 3: // SEARCH for VALUE of KEY
                    if (table.containsKey(B)){
                        out.println(table.get(B));
                    }
                    else {
                        out.println(0);
                    }
                    break;

                default:
                    // can never be reached
                    out.println("Something went wrong!");
                    break;
            }
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    // helper method, parses inputs to Integers, throws custom exceptions
    public static List<Integer> getInputs (String inputLine) throws Exception {
        if (inputLine == null || inputLine.length() == 0) {
            throw new Exception("Invalid input. Null or empty found. Try again");
        }
        List<Integer> result = new ArrayList<>();
        String[] arr = inputLine.split(",");
        for (String a : arr) {
            a = a.replaceAll("[\\D]", "").trim();
            result.add(Integer.parseInt(a));
        }

        return result;
    }

    // helper method to validate integer inputs
    public static void clearInputs(List<Integer> inputs) throws Exception {
        switch (inputs.size()) {
            case 1:
                throw new Exception("Wrong number of arguments. Only 1 inserted");

            case 2:
                int A = inputs.get(0);
                if (A == 1) {
                    throw new Exception("Not enough arguments. You need to provide key, value pair for INSERT");
                }
                else if (A < 0 || A > 3) {
                    throw new Exception("Wrong A command. Try 0-3 for 1st input");
                }
                else if (A == 0 && inputs.get(1) != 0) {
                    throw new Exception("You need to enter 0,0 or (0,0) to terminate connection/disconnect");
                }
        }
    }

}
