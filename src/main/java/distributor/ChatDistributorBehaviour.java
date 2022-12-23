package distributor;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.SneakyThrows;

import java.util.List;

public class ChatDistributorBehaviour extends Behaviour {

    boolean stop = false;
    Double startCost = 77d;
    List<String> producers;
    DistributorAgent agent;
    int countOut;

    public ChatDistributorBehaviour(List<String> producers) {
        this.producers = producers;
        countOut = producers.size();
    }

    @SneakyThrows
    @Override
    public void onStart() {
        agent = (DistributorAgent) myAgent;
        System.out.println(myAgent.getLocalName() + " я в чате. Начальная цена: " + agent.getBestCost());
        Thread.sleep(100);
        ACLMessage startTorg = new ACLMessage(ACLMessage.INFORM_REF);
        /** Не лучшему производителю отправляю письмо с начальной ценой для аукциона  */
        for (String producer : producers) {
            if (!producer.equals(agent.getBestProducer())) {
                startTorg.addReceiver(new AID(producer, false));
            }
        }
        startTorg.setContent(myAgent.getLocalName()+";"+agent.getBestCost());
        myAgent.send(startTorg);
    }

    @SneakyThrows
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF);
        ACLMessage request = myAgent.receive(messageTemplate);

//        Thread.sleep(200);
//        stop = true;

        if (request != null) {
            String requestContent = request.getContent();
            System.out.println("----------------------------------------------------------------- "+ requestContent);
            if (requestContent.startsWith("Цена")) {
                countOut--;
            } else {
                String[] mess = request.getContent().split(";");
                agent.setBestProducer(mess[0]);
                agent.setBestCost(Double.parseDouble(mess[1]));
            }
            if (countOut == 1) {
                ACLMessage stopTorg = new ACLMessage(ACLMessage.INFORM_REF);
                stopTorg.addReceiver(new AID(agent.getBestProducer(), false));
                stopTorg.setContent("stop;stop");

                myAgent.send(stopTorg);
                Thread.sleep(100);
                stop = true;
            }

        } else {
            block();
        }
    }

    @Override
    public int onEnd() {
        agent.getBestCost();
        System.out.println("\n"+myAgent.getLocalName() + ": аукцион закрыт:\nЛучшая цена "+agent.getBestCost()+" от "+ agent.getBestProducer());
        myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour(agent.getEnergy(), agent.getBestCost()));
        return 0;
    }

    @Override
    public boolean done() {
        return stop;
    }

}
