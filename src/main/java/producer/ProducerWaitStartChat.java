package producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import util.ChatBehaviour;

public class ProducerWaitStartChat extends Behaviour {
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
        ACLMessage request = myAgent.receive(messageTemplate);

        if (request != null) {
            myAgent.addBehaviour(new ChatBehaviour());
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
