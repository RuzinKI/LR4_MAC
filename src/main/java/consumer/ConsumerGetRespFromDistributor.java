package consumer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.SneakyThrows;

public class ConsumerGetRespFromDistributor extends Behaviour {

    int result;
    int counter;

    @Override
    @SneakyThrows
    public void action() {

        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage response = myAgent.receive(messageTemplate);

        if (response != null) {

            counter++;

            if (response.getContent().equals("null;null")) {
                System.out.println("Не удалось найти поставщика");
                result = 1;
            }

            if (!response.getContent().equals("null;null")) {
                System.out.println("Энергия куплена");
                result = 2;
            }

            if (counter == 3) {
                result = 3;
                System.out.println("Произошло разбиение");
            }

        }

    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public int onEnd() {
        return result;
    }
}
