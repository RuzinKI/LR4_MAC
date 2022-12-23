package master;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.SneakyThrows;

import java.util.Arrays;

public class MasterBehavior extends TickerBehaviour {

    private final String[] producers;
    private final String[] consumers;

    public MasterBehavior(Agent a, long period, String[] producers, String[] consumers) {
        super(a, period);
        this.producers = producers;
        this.consumers = consumers;
        System.out.println(Arrays.toString(producers));
        System.out.println(Arrays.toString(consumers));
    }

    @Override
    @SneakyThrows
    protected void onTick() {
        System.out.println();
        System.out.println("============================================================================================");
        System.out.println("============================================================================================");
        System.out.println();
        ACLMessage start = new ACLMessage(ACLMessage.PROPAGATE);
        for (String producer: producers) {
            start.addReceiver(new AID(producer, false));
        }
        myAgent.send(start);

        Thread.sleep(100);

        start = new ACLMessage(ACLMessage.PROPAGATE);
        for (String consumer: consumers) {
            start.addReceiver(new AID(consumer, false));
        }
        myAgent.send(start);
    }
}
