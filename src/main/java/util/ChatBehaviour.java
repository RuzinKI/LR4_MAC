package util;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class ChatBehaviour extends OneShotBehaviour {


    @Override
    public void action() {
        System.out.println(myAgent.getLocalName() + " я в чате ");
    }

}
