package distributor;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class DistributorAgent  extends Agent {

    private Double energy = 0d;
    private Double costConsumer = 0d;
    private Double bestCost = 0d;
    private String nameProducer;
    private String bestProducer;
    private Boolean del = false;

    @Override
    protected void setup() {
        System.out.println(this.getLocalName() + " запущен");

        this.addBehaviour(new DistributorGetReqFromConsumerBehavior());

        Object[] arguments = getArguments();
        for (Object argument: arguments) {
            addBehaviour((Behaviour) argument);
        }
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public String getBestProducer() {
        return bestProducer;
    }

    public void setBestProducer(String bestProducer) {
        this.bestProducer = bestProducer;
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