package Ergasia2.Ypoergasia_3;

import java.util.ArrayList;
import java.util.List;

public class RunClients {
    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                int PORTS = Integer.parseInt(args[0]);
                int CLIENTS = Integer.parseInt(args[1]);

                if (PORTS <= 0 || CLIENTS <=0) return;

                List<Integer> producerPORTS = new ArrayList<>();
                List<Integer> consumerPORTS = new ArrayList<>();

                for (int i = 0; i < PORTS || i < 10; i++) {
                    producerPORTS.add(10007 + i);
                    consumerPORTS.add(20007 + i);
                }

                for (int i = 0; i < CLIENTS || i < 10; i++){
                    new Ypo3Client("ADD", consumerPORTS, (i * 2) + 1 ).start();
                    new Ypo3Client("SUB", producerPORTS, (i * 2) + 2).start();
                }

            }
            catch(NumberFormatException e){
                System.err.println("Input entered could not be parsed into an Integer");
            }
        }

        else {
            List<Integer> producerPORTS = new ArrayList<>();
            producerPORTS.add(10007);
            producerPORTS.add(10008);
            producerPORTS.add(10009);

            List<Integer> consumerPORTS = new ArrayList<>();
            consumerPORTS.add(20007);
            consumerPORTS.add(20008);
            consumerPORTS.add(20009);

            new Ypo3Client("SUB", consumerPORTS,1 ).start();
            new Ypo3Client("ADD", producerPORTS, 2).start();
            new Ypo3Client("SUB", consumerPORTS, 3).start();
            new Ypo3Client("ADD", producerPORTS, 4).start();
            new Ypo3Client("SUB", consumerPORTS, 5).start();
            new Ypo3Client("ADD", producerPORTS, 6).start();
        }

    }
}
