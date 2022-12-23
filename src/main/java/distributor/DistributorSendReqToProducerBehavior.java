package distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import lombok.SneakyThrows;

public class DistributorSendReqToProducerBehavior extends OneShotBehaviour {

    public DistributorSendReqToProducerBehavior() {
    }

    @Override
    public void action() {
        DistributorAgent agent = (DistributorAgent) myAgent;
        Double energy = agent.getEnergy();
        Double costConsumer = agent.getCostConsumer();

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setContent(energy+";"+costConsumer);

        System.out.println( myAgent.getLocalName() + " Отправляю запрос производителям");

        String[] producers = getProducers();
        for (String producer: producers) {
            request.addReceiver(new AID(producer, false));
        }

        myAgent.addBehaviour(new DistributorGetRespFromProducerBehavior(producers.length));
        myAgent.send(request);
    }

    @SneakyThrows
    private String[] getProducers() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Producer");
        template.addServices(sd);

        DFAgentDescription[] result = DFService.search(myAgent, template);
        String[] producers = new String[result.length];
        for (int i=0; i < result.length; i++) {
            producers[i] = result[i].getName().getLocalName();
        }
        return producers;
    }
}
