package agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Launcher {
	public static void main(String[] args) {
		Runtime runtime = Runtime.instance();
		Profile config = new ProfileImpl("localhost", 8888, null);
		config.setParameter("gui", "true");
		AgentContainer mc = runtime.createMainContainer(config);
		AgentController ac1;
		AgentController ac2;
		try {
			ac1 = mc.createNewAgent("player", Player.class.getName(), null);
			ac2 = mc.createNewAgent("game_master", GameMaster.class.getName(), null);
			ac1.start();
			ac2.start();
		} catch (StaleProxyException e) {
		}
	}
}
