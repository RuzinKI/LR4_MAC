package distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import util.ChatBehaviour;

import java.util.List;

public class DistributorSendMessageStartChat extends OneShotBehaviour {
    List<String> producers;


    public DistributorSendMessageStartChat(List<String> producer) {
        this.producers = producer;
    }

    @Override
    public void action() {

        ACLMessage request = new ACLMessage(ACLMessage.CONFIRM);
        request.setContent("GoChat");
        for (String producer: producers) {
            request.addReceiver(new AID(producer, false));
        }
        myAgent.send(request);
        myAgent.addBehaviour(new ChatBehaviour());
    }
}
