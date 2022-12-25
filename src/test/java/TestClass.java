import consumer.ConsumerAgent;
import distributor.DistributorAgent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import producer.ProducerAgent;
import util.ConfigReader;

public class TestClass {

    private static AgentContainer agentContainer;

    @SneakyThrows
    @Test
    public void test1() {
        ConfigReader.setPath("test_config_1");
        Properties properties = new Properties();
        properties.setProperty("gui", "true");
        ProfileImpl profile = new ProfileImpl(properties);
        Runtime.instance().setCloseVM(true);
        agentContainer = Runtime.instance().createMainContainer(profile);

        AgentController тэс = agentContainer.createNewAgent("ТЭС", ProducerAgent.class.getName(), new Object[]{});

        TestDistributorAgentBehaviour testDistributorAgentBehaviour = new TestDistributorAgentBehaviour();

        AgentController distributor_1 = agentContainer.createNewAgent("Distributor-1", DistributorAgent.class.getName(), new Object[]{testDistributorAgentBehaviour});
        AgentController consumer_1 = agentContainer.createNewAgent("Consumer-1", ConsumerAgent.class.getName(), new Object[]{});
        AgentController master = agentContainer.createNewAgent("Master", master.Master.class.getName(), new Object[]{});

        тэс.start();
        distributor_1.start();
        consumer_1.start();
        master.start();

        Thread.sleep(3000);

        тэс.kill();
        distributor_1.kill();
        consumer_1.kill();
        master.kill();
    }

    @SneakyThrows
    @Test
    public void test2() {
        ConfigReader.setPath("test_config_2");
        Properties properties = new Properties();
        properties.setProperty("gui", "true");
        ProfileImpl profile = new ProfileImpl(properties);
        Runtime.instance().setCloseVM(true);
        agentContainer = Runtime.instance().createMainContainer(profile);

        AgentController тэс = agentContainer.createNewAgent("ТЭС", ProducerAgent.class.getName(), new Object[]{});
        AgentController вэс = agentContainer.createNewAgent("ВЭС", ProducerAgent.class.getName(), new Object[]{});

        TestDistributorAgentBehaviour testDistributorAgentBehaviour = new TestDistributorAgentBehaviour();

        AgentController distributor_1 = agentContainer.createNewAgent("Distributor-1", DistributorAgent.class.getName(), new Object[]{testDistributorAgentBehaviour});
        AgentController consumer_1 = agentContainer.createNewAgent("Consumer-1", ConsumerAgent.class.getName(), new Object[]{});
        AgentController master = agentContainer.createNewAgent("Master", master.Master.class.getName(), new Object[]{});

        тэс.start();
        вэс.start();
        distributor_1.start();
        consumer_1.start();
        master.start();

        Thread.sleep(3000);

        тэс.kill();
        вэс.kill();
        distributor_1.kill();
        consumer_1.kill();
        master.kill();
    }

    @SneakyThrows
    @Test
    public void test3() {
        ConfigReader.setPath("test_config_3");
        Properties properties = new Properties();
        properties.setProperty("gui", "true");
        ProfileImpl profile = new ProfileImpl(properties);
        Runtime.instance().setCloseVM(true);
        agentContainer = Runtime.instance().createMainContainer(profile);

        AgentController тэс = agentContainer.createNewAgent("ТЭС", ProducerAgent.class.getName(), new Object[]{});
        AgentController вэс = agentContainer.createNewAgent("ВЭС", ProducerAgent.class.getName(), new Object[]{});
        AgentController сэс = agentContainer.createNewAgent("СЭС", ProducerAgent.class.getName(), new Object[]{});

        TestDistributorAgentBehaviour testDistributorAgentBehaviour = new TestDistributorAgentBehaviour();

        AgentController distributor_1 = agentContainer.createNewAgent("Distributor-1", DistributorAgent.class.getName(), new Object[]{testDistributorAgentBehaviour});
        AgentController consumer_1 = agentContainer.createNewAgent("Consumer-1", ConsumerAgent.class.getName(), new Object[]{});
        AgentController master = agentContainer.createNewAgent("Master", master.Master.class.getName(), new Object[]{});

        тэс.start();
        вэс.start();
        сэс.start();
        distributor_1.start();
        consumer_1.start();
        master.start();

        Thread.sleep(3000);

        тэс.kill();
        вэс.kill();
        сэс.kill();
        distributor_1.kill();
        consumer_1.kill();
        master.kill();
    }
}
