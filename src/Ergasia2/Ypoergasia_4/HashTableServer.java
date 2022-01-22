package Ergasia2.Ypoergasia_4;

import exercise2_2.yp4.ServerOperation;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class HashTableServer extends UnicastRemoteObject implements HashTableOperations {
    private Hashtable<Integer, Integer> table;

    protected HashTableServer() throws RemoteException {
        super();
        table = new Hashtable<>((int)Math.pow(2, 20));
    }

    @Override
    public boolean upsert(int key, int value) throws RemoteException{
        try {
            if (table.containsKey(key)){
                table.replace(key, value);
            }
            else {
                table.put(key, value);
            }
            return true;

        }catch (Exception e) {
            System.out.println("key not entered");
        }

        return false;
    }

    @Override
    public boolean delete(int key) throws RemoteException{
        table.remove(key);
        return true;
    }

    @Override
    public int getKeyValue(int key) throws RemoteException{
        if (table.containsKey(key)){
            return table.get(key);
        }

        return 0;
    }


    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(11234);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            Naming.rebind("//localhost:11234/MyServer", new HashTableServer());
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
