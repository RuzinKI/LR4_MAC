package util;

import consumer.ConsumerAgent;
import distributor.DistributorAgent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import lombok.SneakyThrows;
import producer.ProducerAgent;

public class Main {

    private static AgentContainer agentContainer;

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("gui", "true");
        ProfileImpl profile = new ProfileImpl(properties);
        Runtime.instance().setCloseVM(true);
        agentContainer = Runtime.instance().createMainContainer(profile);

        createAgents();

    }

    @SneakyThrows
    public static void createAgents() {

        ConfigReader.setPath("config");

        AgentController тэс = agentContainer.createNewAgent("ТЭС", ProducerAgent.class.getName(), new Object[]{});
        AgentController вэс = agentContainer.createNewAgent("ВЭС", ProducerAgent.class.getName(), new Object[]{});
        AgentController сэс = agentContainer.createNewAgent("СЭС", ProducerAgent.class.getName(), new Object[]{});

        AgentController distributor_1 = agentContainer.createNewAgent("Distributor-1", DistributorAgent.class.getName(), new Object[]{});
        AgentController consumer_1 = agentContainer.createNewAgent("Consumer-1", ConsumerAgent.class.getName(), new Object[]{});
        AgentController master = agentContainer.createNewAgent("Master", master.Master.class.getName(), new Object[]{});

        тэс.start();
        вэс.start();
        сэс.start();
        distributor_1.start();
        consumer_1.start();
        master.start();
    }

}