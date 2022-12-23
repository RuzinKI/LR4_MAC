package distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import producer.ChatProducerBehaviour;

import java.util.List;
import java.util.stream.Collectors;

public class DistributorSendMessageStartChat extends OneShotBehaviour {
    List<String> producers;


    public DistributorSendMessageStartChat(List<String> producer) {
        this.producers = producer;
    }

    @Override
    public void action() {

        ACLMessage request = new ACLMessage(ACLMessage.CONFIRM);
        String collectProd = producers.stream().collect(Collectors.joining(";"));
        String collectAll = collectProd + ";" + myAgent.getLocalName();
        System.out.println("Список участников чата: "+collectAll);
        System.out.println();

        request.setContent(collectAll);
        for (String producer: producers) {
            request.addReceiver(new AID(producer, false));
        }
        myAgent.addBehaviour(new ChatDistributorBehaviour(producers));
        myAgent.send(request);
    }
}
