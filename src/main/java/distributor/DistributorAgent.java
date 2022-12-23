package distributor;

import jade.core.Agent;

public class DistributorAgent  extends Agent {

    private Double energy = 0d;
    private Double costConsumer = 0d;
    private Double bestCost = 0d;
    private String nameProducer;

    @Override
    protected void setup() {
        System.out.println(this.getLocalName() + " запущен");

        this.addBehaviour(new DistributorGetReqFromConsumerBehavior());
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getCostConsumer() {
        return costConsumer;
    }

    public void setCostConsumer(Double costConsumer) {
        this.costConsumer = costConsumer;
    }

    public Double getBestCost() {
        return bestCost;
    }

    public void setBestCost(Double bestCost) {
        this.bestCost = bestCost;
    }

    public String getNameProducer() {
        return nameProducer;
    }

    public void setNameProducer(String nameProducer) {
        this.nameProducer = nameProducer;
    }
}