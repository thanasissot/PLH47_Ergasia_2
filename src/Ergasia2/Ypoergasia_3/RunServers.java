package Ergasia2.Ypoergasia_3;

import java.io.IOException;

public class RunServers {
    // create and use a 2nd client running at the same time with another instance of Ypo2Client
    // and check if updating values in 1 client persists the changes to other one
    public static void main(String[] args) throws InterruptedException, IOException {
        new Ypo3Server(10007, 20007);
        new Ypo3Server(10008, 20008);
        new Ypo3Server(10009, 20009);
    }
}
