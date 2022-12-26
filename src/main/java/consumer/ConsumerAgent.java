package consumer;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import util.ConfigReader;
import util.Graphic;

import java.util.List;

public class ConsumerAgent extends Agent {
    private List<Integer> points;
    private int maxCost;

    @Override
    @SneakyThrows
    protected void setup() {
        Graphic graphic = ConfigReader.getGraphic();
        points = graphic.getPoints();
        maxCost = graphic.getMaxPrice();

        System.out.println(this.getLocalName() + " агент запущен");

        this.addBehaviour(new ConsumerSendReqToDistributorBehavior(maxCost));
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("Consumer");

        Object[] arguments = getArguments();
        for (Object argument: arguments) {
            addBehaviour((Behaviour) argument);
        }

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        dfd.addServices(sd);

        DFService.register(this, dfd);
    }

    public List<Integer> getPoints() {
        return points;
    }
}
