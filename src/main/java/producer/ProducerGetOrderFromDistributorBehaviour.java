package producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProducerGetOrderFromDistributorBehaviour extends Behaviour {

    ProducerAgent agent;

    @Override
    public void onStart() {
        agent = (ProducerAgent) myAgent;
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);

        ACLMessage request = myAgent.receive(messageTemplate);

        if (request != null) {
            double energySold = Double.parseDouble(request.getContent());
            agent.setEnergy(agent.getEnergy()-energySold);
            String ostatok = String.format("%.2f", agent.getEnergy());
            System.out.println(myAgent.getLocalName() + " продал "+ energySold + " кВт для "+
                    request.getSender().getLocalName()+", осталось "+ ostatok);
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
