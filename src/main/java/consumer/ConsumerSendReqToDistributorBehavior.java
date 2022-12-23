package consumer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConsumerSendReqToDistributorBehavior extends Behaviour {

    int counter;
    int maxCost;

    public ConsumerSendReqToDistributorBehavior(int maxCost) {
        this.maxCost = maxCost;
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
        ACLMessage start = myAgent.receive(messageTemplate);
        ConsumerAgent agent = (ConsumerAgent) myAgent;

        ACLMessage request = new ACLMessage(ACLMessage.CFP);

        if (start != null) {
            if (myAgent.getLocalName().equals("Consumer-1")) {
                request.addReceiver(new AID("Distributor-1", false));
            }

            Integer energy = agent.getPoints().get(counter);
            request.setContent(energy+";"+maxCost);


            System.out.println(myAgent.getLocalName() + " | "+(counter) +"ч |"+" Отправляет запрос на закупку "
                    + energy + " энергии, максимальная цена = "+ maxCost );

            if (++counter == 24) {
                counter = 0;
            }

//            myAgent.addBehaviour(new ConsumerGetRespFromDistributorBehavior());

            myAgent.send(request);
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
