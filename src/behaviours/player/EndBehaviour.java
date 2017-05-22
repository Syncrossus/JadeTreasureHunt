package behaviours.player;

import agents.Player;
import jade.core.behaviours.OneShotBehaviour;

public class EndBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = 3144287550765838023L;
	Player player;
	public EndBehaviour(Player p) {
		player = p;
	}
	@Override
	public void action() {
		System.out.println(player.getAID().getLocalName() + "> I found the treasure at "+player.getPos()+"!");
		player.doDelete();
	}

}
