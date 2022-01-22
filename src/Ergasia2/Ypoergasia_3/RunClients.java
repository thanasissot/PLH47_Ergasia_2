package Ergasia2.Ypoergasia_3;

import java.util.ArrayList;
import java.util.List;

public class RunClients {
    public static void main(String[] args) {
        List<Integer> consumerPORTS = new ArrayList<>();
        consumerPORTS.add(20007);
        consumerPORTS.add(20008);
        consumerPORTS.add(20009);

        List<Integer> producerPORTS = new ArrayList<>();
        producerPORTS.add(20007);
        producerPORTS.add(20008);
        producerPORTS.add(20009);

        new Client("ADD", consumerPORTS).start();
        new Client("SUB", producerPORTS).start();
    }
}
