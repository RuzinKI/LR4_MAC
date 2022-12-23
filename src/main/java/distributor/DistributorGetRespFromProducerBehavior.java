package distributor;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.SneakyThrows;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DistributorGetRespFromProducerBehavior extends Behaviour {

    boolean stop = false;
    Integer count = 0;
    int num;
    String bestProducer = "none";
    Double bestCost = 100d;
    List<String> producer;
    Double costConsumer;

    Integer countProducer = 0;

    Integer twoCount = 0;
    //    Double energyDouble;
    Double energyConsumer;

    public DistributorGetRespFromProducerBehavior(Integer num ) {
        this.num = num;
        producer = new ArrayList<>();
    }

    @SneakyThrows
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.REFUSE),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

        ACLMessage response = myAgent.receive(messageTemplate);

        DistributorAgent agent = (DistributorAgent) myAgent;
        energyConsumer = agent.getEnergy();
        costConsumer = agent.getCostConsumer();

        synchronized (count) {
            if (response != null) {
                String[] split = {"0.0d", "0.0d"};
                count++;
                if (response.getPerformative() == ACLMessage.REFUSE) {
                    System.out.println(response.getSender().getLocalName() + ":    Отказ от " + response.getSender().getLocalName());
                }

                if (response.getPerformative() == ACLMessage.PROPOSE) {
                    split = response.getContent().split(";");
                    Double costFromProducer = Double.parseDouble(split[1]);

                    String cost = String.format("%3f", costFromProducer);

                    if (costFromProducer <= costConsumer) {
                        producer.add(response.getSender().getLocalName());
                        System.out.println(response.getSender().getLocalName() + ":    Предложение цены " + cost + " от " + response.getSender().getLocalName() + " подходит потребителю");

                        if (bestCost > costFromProducer ) {
                            bestCost = costFromProducer;
                        }
                        bestProducer = response.getSender().getLocalName();
                    } else {
                        producer.add(response.getSender().getLocalName());
                        System.out.println(response.getSender().getLocalName() + ":    Не подходит потребителю " + cost + " > " + costConsumer);
                    }
                }

                if (count == num) {
                    stop = true;
                }

            } else {
                block();
            }

        }

    }

    @Override
    public int onEnd() {
        if (producer.size() == 0) {
            System.out.println("У производителей нет подходящей энергии");
            myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour(null, null));
        }
        if ((producer.size() == 1) && (bestCost > costConsumer)) {
            System.out.println("На рынке один поставщик с подходящей энергией, но высокой ценой");
            myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour(null, null));
        }
        if ((producer.size() == 1) && (bestCost <= costConsumer)) {
            System.out.println("На рынке один поставщик с подходящей энергией и ценой");
            myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour(energyConsumer, costConsumer));
        }
        if (producer.size() > 1) {
            System.out.println("Есть несколько поставщиков, можно начинать торги");
            System.out.println("");
            myAgent.addBehaviour(new DistributorSendMessageStartChat(producer));

        }
        return 0;
    }

    @Override
    public boolean done() {
        return stop;
    }


}
