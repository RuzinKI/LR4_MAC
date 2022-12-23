package consumer;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import lombok.SneakyThrows;
import util.ConfigReader;
import util.Graphic;

import java.util.List;

public class ConsumerAgent extends Agent {
    private List<Integer> points;
    private int maxCost = 5;

    @Override
    @SneakyThrows
    protected void setup() {
        Graphic graphic = ConfigReader.getGraphic("config");
        points = graphic.getPoints();

        System.out.println(this.getLocalName() + " агент запущен");

        this.addBehaviour(new ConsumerSendReqToDistributorBehavior(maxCost));
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("Consumer");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        dfd.addServices(sd);

        DFService.register(this, dfd);
    }

    public List<Integer> getPoints() {
        return points;
    }
}
