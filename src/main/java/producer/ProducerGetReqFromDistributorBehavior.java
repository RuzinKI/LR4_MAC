package producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProducerGetReqFromDistributorBehavior extends Behaviour {

    @Override
    public void action() {

        MessageTemplate messageTemplate =  MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.CANCEL));
        ACLMessage request = myAgent.receive(messageTemplate);

        ProducerAgent agent = (ProducerAgent) myAgent;

        if (request != null) {
            String[] split = request.getContent().split(";");

            double energyRequest = Double.parseDouble(split[0]);
            double costRequest = Double.parseDouble(split[1]);
            double currentEnergy = agent.getEnergy();

            String currentEnergyString = String.format("%.3f", currentEnergy);

            if (request.getPerformative() == ACLMessage.REQUEST) {
                System.out.println("Запрашивают " + energyRequest + ";  имею "
                        + currentEnergyString + "   " + myAgent.getLocalName());

                ACLMessage response = request.createReply();
                if (energyRequest > currentEnergy) {
                    response.setPerformative(ACLMessage.REFUSE);
                } else {
                    response.setPerformative(ACLMessage.PROPOSE);
                    response.setContent(energyRequest + ";" + agent.getStartCost());
                }
                myAgent.send(response);
            }


            if (request.getPerformative() == ACLMessage.CANCEL) {
                System.out.println("Запрашивают " + energyRequest + " по цене: "+costRequest +";  имею " + currentEnergy + "   " + myAgent.getLocalName());
                ACLMessage response = request.createReply();
                if ((energyRequest > currentEnergy)) {
                    response.setPerformative(ACLMessage.REFUSE);
                } else if (costRequest>agent.getMinCost()) {
                    response.setPerformative(ACLMessage.PROPOSE);
                    response.setContent(energyRequest + ";" + costRequest);
                } else {
                    response.setPerformative(ACLMessage.REFUSE);
                }
                myAgent.send(response);
            }

        } else {
            block();
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
