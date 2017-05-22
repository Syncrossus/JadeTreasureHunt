package behaviours.player;

import agents.GameMaster;
import agents.Player;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import util.AgentLogger;

public class InitBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -4395908709321689561L;
	Player player;
	
	public InitBehaviour(Player p){
		player = p;
	}
	
	@Override
	public void action() {
		//Wait for message to start
		player.doWait();
		ACLMessage message = player.receive();
		AgentLogger.log(message);
		
		player.initGame();
		
		ACLMessage hintRequest = new ACLMessage(ACLMessage.REQUEST);
		hintRequest.setContent("up");
		player.move("up");
		hintRequest.addReceiver(GameMaster.IDENTIFIER);
		player.send(hintRequest);
	}

}
