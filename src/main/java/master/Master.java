package master;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import lombok.SneakyThrows;

public class Master extends Agent {

    @Override
    @SneakyThrows
    protected void setup() {

        Thread.sleep(300);
        System.out.println();
        System.out.println();
        System.out.println("Мастер стартовал");
        this.addBehaviour(new MasterBehavior(this,10000, getProducers(), getConsumers()));

    }

    @SneakyThrows
    private String[] getProducers() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Producer");
        template.addServices(sd);

        DFAgentDescription[] result = DFService.search(this, template);
        String[] producers = new String[result.length];
        for (int i=0; i < result.length; i++) {
            producers[i] = result[i].getName().getLocalName();
        }
        return producers;
    }

    @SneakyThrows
    private String[] getConsumers() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Consumer");
        template.addServices(sd);

        DFAgentDescription[] result = DFService.search(this, template);
        String[] consumers = new String[result.length];
        for (int i=0; i < result.length; i++) {
            consumers[i] = result[i].getName().getLocalName();
        }
        return consumers;
    }
}
