package producer;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProducerBehavior extends Behaviour {

    String type;
    double A;
    double B1;
    double B2;
    double C0;
    double C1;
    double C2;
    double C3;
    int hour = 1;

    public ProducerBehavior(String type, double A) {
        this.type = type;
        this.A = A;
    }

    public ProducerBehavior(String type, double B1, double B2) {
        this.type = type;
        this.B1 = B1;
        this.B2 = B2;
    }

    public ProducerBehavior(String type, double C0, double C1, double C2, double C3) {
        this.type = type;
        this.C0 = C0;
        this.C1 = C1;
        this.C2 = C2;
        this.C3 = C3;
    }


    @Override
    public void action() {

        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
        ACLMessage start = myAgent.receive(messageTemplate);

        if (start != null) {

            if (type.equals("ТЭС")) {
                ProducerAgent producerAgent = (ProducerAgent) this.getAgent();
                Double energy = A;
                producerAgent.setEnergy(energy);
                producerAgent.setMinCost(3.0);
                producerAgent.setStartCost(6d);
                System.out.println("Энергия ТЭС " + producerAgent.getEnergy());
                hour++;
                if (hour == 24) {
                    hour = 1;
                }
            }

            if (type.equals("ВЭС")) {
                ProducerAgent producerAgent = (ProducerAgent) this.getAgent();
                Double energy = (1 / (B2*Math.sqrt(2*Math.PI)) * Math.exp(-(hour-B1) * (hour-B1) / (2*B2*B2)))*250;

                Double startCost = 54/(energy+2)+1;
                if (startCost > 7) {
                    startCost = 8d;
                }
                producerAgent.setStartCost(startCost);
                producerAgent.setMinCost(startCost/2);

                producerAgent.setEnergy(energy);
                System.out.println("Энергия ВЭС " + producerAgent.getEnergy());
                hour++;
                if (hour == 24) {
                    hour = 1;
                }
            }

            if(type.equals("СЭС")) {
                ProducerAgent producerAgent = (ProducerAgent) this.getAgent();
                Double energy = 0d;
                Double startCost = 100d;
                if (hour > 5 && hour < 19) {
                    energy = C0 + C1*hour + C2*hour*hour + C3*hour*hour*hour;
                    startCost = 40/(energy+2);
                } else {
                    energy = 0d;
                }
                producerAgent.setStartCost(startCost);
                producerAgent.setMinCost(startCost/2);
                producerAgent.setEnergy(energy);
                System.out.println("Энергия СЭС " + producerAgent.getEnergy());
                hour++;
                if (hour == 24) {
                    hour = 1;
                }
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
