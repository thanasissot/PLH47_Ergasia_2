package Ergasia2.Ypoergasia_3;

import java.util.ArrayList;
import java.util.List;

public class RunClients {
    public static void main(String[] args) {
        List<Integer> producerPORTS = new ArrayList<>();
        producerPORTS.add(10007);
        producerPORTS.add(10008);
        producerPORTS.add(10009);

        List<Integer> consumerPORTS = new ArrayList<>();
        consumerPORTS.add(20007);
        consumerPORTS.add(20008);
        consumerPORTS.add(20009);

        new Client("SUB", consumerPORTS).start();
        new Client("ADD", producerPORTS).start();
        new Client("SUB", consumerPORTS).start();
        new Client("ADD", producerPORTS).start();
        new Client("SUB", consumerPORTS).start();
        new Client("ADD", producerPORTS).start();
    }
}
