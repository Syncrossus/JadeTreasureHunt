package behaviours.gamemaster;

import agents.GameMaster;
import agents.Player;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import util.AgentLogger;

public class PlayBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -2810893152176054082L;
	GameMaster gm;
	int nextState;
	
	public PlayBehaviour(GameMaster gm) {
		this.gm = gm;
		nextState = 1;
	}

	@Override
	public void action() {
		gm.doWait();
		
		ACLMessage hintRequest = gm.receive();
		AgentLogger.log(hintRequest);
		String playerMoveDirection = hintRequest.getContent();
		String hint = gm.evaluateProximity(playerMoveDirection);
		
		if(hint.equals("win"))
			nextState = 0;
		
		ACLMessage hintMessage = new ACLMessage(ACLMessage.INFORM);
		hintMessage.setContent(hint);
		hintMessage.addReceiver(Player.IDENTIFIER);
		gm.send(hintMessage);
	}
	
	@Override
	public int onEnd() {
		return nextState;
	}

}
