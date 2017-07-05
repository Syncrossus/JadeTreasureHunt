package agents;

import util.Coord;

import java.util.ArrayList;
import java.util.List;

import behaviours.player.EndBehaviour;
import behaviours.player.PlayBehaviour;
import behaviours.player.InitBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class Player extends Agent{
	private static final long serialVersionUID = 1709672583743068992L;
	public static AID  IDENTIFIER = new AID("player", AID.ISLOCALNAME);
	
	private static final String BEHAVIOUR_INIT = "initialisation";
	private static final String BEHAVIOUR_PLAY = "jeu";
	private static final String BEHAVIOUR_END = "end";
	
	private Coord oldPos;
	private Coord pos;
	private List<Coord> candidates;

	@Override
	public void setup(){
		FSMBehaviour behaviour = new FSMBehaviour(this);
		
		//States
		behaviour.registerFirstState(new InitBehaviour(this), BEHAVIOUR_INIT);
		behaviour.registerState(new PlayBehaviour(this), BEHAVIOUR_PLAY);
		behaviour.registerLastState(new EndBehaviour(this), BEHAVIOUR_END);
		
		//Transitions
		behaviour.registerDefaultTransition(BEHAVIOUR_INIT, BEHAVIOUR_PLAY);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_PLAY, 1);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_END, 0);
		
		addBehaviour(behaviour);
	}
	
	public void initGame() {
		setPos(new Coord(5, 5));
		candidates = new ArrayList<Coord>();
		for (int i = 1; i <= 11; i++) {
			for (int j = 1; j <= 11; j++) {
				candidates.add(new Coord(i, j));
			}
		}
	}

	public String determineNextMove(String hint) {
		if(hint.equals("win")) return "win";
		eliminateCandidates(hint);
		Coord target = candidates.get(0);
		// if player is on target, they can only be sure they're
		// on the treasure if it's the only target left
		if(pos.equals(target)){
			//if player has found treasure, the win
			if(candidates.size()==1)
				return "win";
			// if not, the player moves on to get more indications
			else{
				target = candidates.get(1);
			}
		}
		
		return getPos().determineDirectionTo(target);
	}
	
	private void eliminateCandidates(String hint){
		List<Coord> toRemove = new ArrayList<Coord>();
		switch(hint){
		//if player got warmer, they can remove all
		//candidates they got farther away from
		case "warmer":
			for (Coord c : candidates) {
				if(getPos().distanceTo(c) > oldPos.distanceTo(c))
					toRemove.add(c);
			}
			break;
		//if player got colder, they can remove all
		//candidates they got closer to
		case "colder":
			for (Coord c : candidates) {
				if(getPos().distanceTo(c) < oldPos.distanceTo(c))
					toRemove.add(c);
			}
			break;
		default: // case "same"
			break;
		}
		candidates.removeAll(toRemove);
	}

	public void move(String direction) {
		oldPos = getPos().clone();
		getPos().move(direction);
	}

	public Coord getPos() {
		return pos;
	}

	public void setPos(Coord pos) {
		this.pos = pos;
	}
}
