package Ergasia2.Ypoergasia_4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HashTableOperations extends Remote {
    public boolean upsert(int key, int value) throws RemoteException;
    public boolean delete(int key) throws RemoteException;
    public int getKeyValue (int key) throws RemoteException;
}
