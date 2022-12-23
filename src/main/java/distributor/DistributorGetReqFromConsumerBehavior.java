package distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import producer.ProducerAgent;

public class DistributorGetReqFromConsumerBehavior extends Behaviour {

    private double energy;
    private boolean exit;

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage request = myAgent.receive(messageTemplate);
        DistributorAgent agent = (DistributorAgent) myAgent;

        if (request != null) {
            Double energyVal;
            Double maxCost;

            String[] content = request.getContent().split(";");

            energyVal = Double.parseDouble(content[0]);
            maxCost = Double.parseDouble(content[1]);
            agent.setEnergy(energyVal);
            agent.setCostConsumer(maxCost);

            System.out.println(myAgent.getLocalName() + " получил запрос от "+ request.getSender().getLocalName() +
                    " на покупку " + energyVal + "кВт за "+ maxCost + " рублей");

            myAgent.addBehaviour(new DistributorSendReqToProducerBehavior());
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

}