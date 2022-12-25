package distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.SneakyThrows;

public class DistributorSendRespToConsumerBehaviour extends OneShotBehaviour {

    Double energy;
    Double cost;

    public DistributorSendRespToConsumerBehaviour(Double energy, Double cost) {
        this.energy = energy;
        this.cost = cost;
    }

    @SneakyThrows
    @Override
    public void action() {
        ACLMessage request = new ACLMessage(ACLMessage.INFORM);
        if (myAgent.getLocalName().equals("Distributor-1")) {
            request.addReceiver(new AID("Consumer-1", false));
        }
        if (myAgent.getLocalName().equals("Distributor-2")) {
            request.addReceiver(new AID("Consumer-2", false));
        }
        if (myAgent.getLocalName().equals("Distributor-3")) {
            request.addReceiver(new AID("Consumer-3", false));
        }
        Thread.sleep(10);
        if (cost != null) {
            String stringCost = String.format("%.3f", cost);
            System.out.println(myAgent.getLocalName() + ": отправляю Consumer-1 : " + energy + " энергии по "+stringCost+" руб/кВт*ч");
            request.setContent(energy+";"+cost);
        } else {
            System.out.println(myAgent.getLocalName() + ": не удалось купить ЭЭ");
            request.setContent("null;null");
        }
        myAgent.send(request);
    }
}