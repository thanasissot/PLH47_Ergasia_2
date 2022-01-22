package Ergasia2.Ypoergasia_4;

import exercise2_2.yp4.ClientOperation;
import exercise2_2.yp4.MenuHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author alexandros
 * extended implementation by Sotiroglou Athanasios
 */

public class Client {
    private HashTableOperations remoteService;

    /**
     * Constructor for the client class.
     * @param connectionString The string for connecting to rmiregistry
     */
    public Client(String connectionString) throws MalformedURLException, RemoteException, NotBoundException{
        remoteService = (HashTableOperations) Naming.lookup(connectionString);
    }

    /**
     * Client's main method
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        Client cl = new Client("//localhost:11234/MyServer");
        cl.execute();
    }

    /**
     * Execute client's operations
     */
    public void execute() throws RemoteException {

        // insert values
        for (int i = 1; i < 10; i++){
            System.out.println("Value "+ i +" was " + (remoteService.upsert(i, (i * 10) + 1)? "inserted." : "not inserted"));
        }

        // read values
        for (int i = 1; i < 10; i++){
            System.out.println("Value of " + i + " in Server's hashTable is " + remoteService.getKeyValue(i));
        }

        // update one value and check it again
        remoteService.upsert(1, 111);
        System.out.println("New value of key=1 is " + remoteService.getKeyValue(1) + ". It should be 111.");

        // delete value
        if (remoteService.delete(1)) {
            System.out.println("Key=1 value is deleted");
        }
        System.out.println("New value of key=1 should be 0. It currently is " + remoteService.getKeyValue(1));
        System.out.println("End of commands input");
    }

}
