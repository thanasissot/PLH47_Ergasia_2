package Ergasia2.Ypoergasia_3;

import java.io.IOException;

public class RunServers {
    /**
     * @param args [0] should be a small integer but in theory is resource restricted initially by number of PORTS available
     *             and also is resource restricted, cores/threads.
     *             Each server listens to two Ports. If args is passed as a count
     *             Start from ports 10007, 20007 and ++ each Port number to a new Server
     * @throws InterruptedException
     * @throws IOException
     */
    // create and use a 2nd client running at the same time with another instance of Ypo2Client
    // and check if updating values in 1 client persists the changes to other one
    public static void main(String[] args) throws InterruptedException, IOException {
        // if args is given we manually create servers
        if (args.length> 0){
            try {
                int SERVERS = Integer.parseInt(args[0]);

                if (SERVERS <= 0) return;

                // start new servers
                for (int i = 0; i < SERVERS || i < 10; i++) {
                    new Ypo3Server(10007 + i, 20007 + i, i + 1);
                }

            }
            catch(NumberFormatException e){
                System.err.println("Input entered could not be parsed into an Integer");
            }

        }
        // default to 3 servers running
        else {
            new Ypo3Server(10007, 20007, 1);
            new Ypo3Server(10008, 20008, 2);
            new Ypo3Server(10009, 20009, 3);
        }

    }
}
