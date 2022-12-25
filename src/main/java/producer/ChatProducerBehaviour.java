package producer;

import distributor.DistributorAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

public class ChatProducerBehaviour extends Behaviour {

    boolean stop = false;
    Double currentCost = 5d;
    List<String> chatUser;
    String testChatUser;
    ProducerAgent agent;
    Double minCost;

    public ChatProducerBehaviour(String chatUser) {
        String[] splitChatUser = chatUser.split(";");
        this.chatUser = Arrays.asList(splitChatUser);
        this.testChatUser = chatUser;
    }

    @Override
    public void onStart() {
        System.out.println(myAgent.getLocalName() + " я в чате");
        agent = (ProducerAgent) myAgent;
        minCost = agent.getMinCost();
    }

    @SneakyThrows
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF);
        ACLMessage request = myAgent.receive(messageTemplate);

//        Thread.sleep(20);

        if (request != null) {
            String[] mess = request.getContent().split(";");

            Thread.sleep(30);

            if (mess[0].equals("stop")) {
                agent.setEnergy(agent.getEnergy() - Double.parseDouble(mess[1]));
                System.out.println(myAgent.getLocalName() + " продал энергию, осталось : " + agent.getEnergy());
                stop = true;
            } else {

                if (!mess[0].equals(myAgent.getLocalName()) && !mess[0].startsWith("Цена")) {
                    ACLMessage torg = new ACLMessage(ACLMessage.INFORM_REF);
                    System.out.println(myAgent.getLocalName() + ": вижу цену " + request.getSender().getLocalName());

                    Double cost = Double.parseDouble(mess[1]) - 0.5;

//                if (mess[0].startsWith("Цена")) {
//                    cost = Double.parseDouble(mess[1]); // прошлый отказался, проверяем подходит ли его цена нам
//                } else {
//                    cost = Double.parseDouble(mess[1]) - 0.5;  // нужно понизить цену
//                }

                    if (cost < minCost) {
                        torg.setContent("Цена " + cost+ " не подходит " + myAgent.getLocalName()+";");
                        System.out.println(myAgent.getLocalName() + ": снизить до " + cost + " не могу, выхожу с торгов: ");
                        stop = true;
                    } else {
                        torg.setContent(myAgent.getLocalName() + ";" + cost);
                        System.out.println(myAgent.getLocalName() + ": Снижаю цену до " + cost + "");
                        currentCost = cost;
                    }
                    for (String chatUsers : chatUser) {
                        torg.addReceiver(new AID(chatUsers, false));
                    }
                    myAgent.send(torg);
                }
            }


        } else {
            block();
        }
    }

    @Override
    public int onEnd() {
        System.out.println(myAgent.getLocalName() + ": выхожу из чата");
        return 0;
    }

    @Override
    public boolean done() {
        return stop;
    }

}
