package behaviours.gamemaster;

import agents.GameMaster;
import jade.core.behaviours.OneShotBehaviour;

public class EndBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = 1626253317062691728L;
	GameMaster gm;
	
	public EndBehaviour(GameMaster gm) {
		this.gm = gm;
	}

	@Override
	public void action() {
		System.out.println(gm.getAID().getLocalName() + "> The player found the treasure at "+gm.getTreasure()+"!");
		
	}

}
