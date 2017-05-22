package behaviours.player;

import agents.GameMaster;
import agents.Player;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import util.AgentLogger;

public class PlayBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -2850535188580377701L;
	Player player;
	int nextState;
	
	public PlayBehaviour(Player p) {
		this.player = p;
		nextState = 1;
	}
	
	@Override
	public void action() {
		player.doWait();
		ACLMessage hintMsg = player.receive();
		AgentLogger.log(hintMsg);
		System.out.println(player.getAID().getLocalName() + " pos : " + player.getPos());
		String hint = hintMsg.getContent();
		
		String moveDirection = player.determineNextMove(hint);
		if(moveDirection.equals("win"))
			nextState = 0;
		
		ACLMessage hintRequest = new ACLMessage(ACLMessage.REQUEST);
		hintRequest.setContent(moveDirection);
		player.move(moveDirection);
		hintRequest.addReceiver(GameMaster.IDENTIFIER);
		player.send(hintRequest);
	}
	
	@Override
	public int onEnd() {
		return nextState;
	}

}
