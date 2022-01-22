package Ergasia2.Ypoergasia_2;

import java.io.IOException;

public class Test {
    // create and use a 2nd client running at the same time with another instance of Ypo2Client
    // and check if updating values in 1 client persists the changes to other one
    public static void main(String[] args) throws InterruptedException, IOException {
        Ypo2Client.main(new String[]{"127.0.0.1"});
    }

}
