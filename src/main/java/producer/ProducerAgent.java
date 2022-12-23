package producer;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import lombok.SneakyThrows;

public class ProducerAgent extends Agent {

    private Double energy = 0d;
    private Double startCost = 0d;
    private Double minCost = 0d;

    @Override
    @SneakyThrows
    protected void setup() {
        System.out.println(this.getLocalName() + " запущен");

        if (this.getLocalName().equals("ТЭС")) {
            this.addBehaviour(new ProducerBehavior("ТЭС", 15.7));
        }

        if (this.getLocalName().equals("ВЭС")) {
            this.addBehaviour(new ProducerBehavior("ВЭС", 7.2, 8.3));
        }

        if (this.getLocalName().equals("СЭС")) {
            this.addBehaviour(new ProducerBehavior("СЭС", -78.985, 20.313, -1.3185, 0.0247));
        }

        this.addBehaviour(new ProducerGetReqFromDistributorBehavior());
        this.addBehaviour(new ProducerWaitStartChat());

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("Producer");
        dfd.addServices(sd);
        DFService.register(this, dfd);

    }

    public void generate(double generatedEnergy) {
        energy = energy + generatedEnergy;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getStartCost() {
        return startCost;
    }

    public void setStartCost(Double startCost) {
        this.startCost = startCost;
    }

    public Double getMinCost() {
        return minCost;
    }

    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }
}