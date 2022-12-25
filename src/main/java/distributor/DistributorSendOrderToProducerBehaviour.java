package distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Map;
import java.util.Set;

public class DistributorSendOrderToProducerBehaviour extends OneShotBehaviour {
    Map<String,Double> prodAndCost;
    int count;

    public DistributorSendOrderToProducerBehaviour() {
        this.count = 1;
    }

    public DistributorSendOrderToProducerBehaviour(Map<String,Double> prodAndCost) {
        this.prodAndCost = prodAndCost;
        this.count = prodAndCost.size();
    }

    @Override
    public void action() {
        DistributorAgent agent = (DistributorAgent) myAgent;
        if (count == 1) {
            ACLMessage sendOrder = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
            sendOrder.addReceiver(new AID(agent.getBestProducer(), false));
            sendOrder.setContent(""+agent.getEnergy());
            myAgent.send(sendOrder);
        } else {
            Set<String> producers = prodAndCost.keySet();
            ACLMessage sendOrder = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
            for (String producer : producers) {
                sendOrder.addReceiver(new AID(producer, false));
            }
            sendOrder.setContent(""+agent.getEnergy());
            myAgent.send(sendOrder);
        }
    }
}
