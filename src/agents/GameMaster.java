package agents;

import java.util.Random;

import behaviours.gamemaster.EndBehaviour;
import behaviours.gamemaster.PlayBehaviour;
import behaviours.gamemaster.StartBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import util.Coord;

public class GameMaster extends Agent {
	private static final long serialVersionUID = 3776482759573939188L;
	
	private static final String BEHAVIOUR_START = "start";
	private static final String BEHAVIOUR_PLAY = "play";
	private static final String BEHAVIOUR_END = "end";

	public static AID IDENTIFIER = new AID("game_master", AID.ISLOCALNAME);
	
	private Coord treasure;
	private Coord playerPos;
	
	
	public void setup(){
		FSMBehaviour behaviour = new FSMBehaviour(this);
		
		//States
		behaviour.registerFirstState(new StartBehaviour(this), BEHAVIOUR_START);
		behaviour.registerState(new PlayBehaviour(this), BEHAVIOUR_PLAY);
		behaviour.registerLastState(new EndBehaviour(this), BEHAVIOUR_END);
		
		//Transitions
		behaviour.registerDefaultTransition(BEHAVIOUR_START, BEHAVIOUR_PLAY);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_PLAY, 1);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_END, 0);
		
		addBehaviour(behaviour);
	}
	
	public void initGame() {
		Random generator = new Random();
		setTreasure(new Coord(generator.nextInt(9)+1, generator.nextInt(9)+1));
		playerPos = new Coord(5, 5);
	}

	public String evaluateProximity(String playerMoveDirection) {
		Coord newPlayerPos = playerPos.clone();
		newPlayerPos.move(playerMoveDirection);
		String hint = "colder";
		if(newPlayerPos.distanceTo(treasure)==0)
			hint = "win";
		else if (newPlayerPos.distanceTo(getTreasure())<playerPos.distanceTo(getTreasure()))
			hint = "warmer";
		else if(newPlayerPos.distanceTo(getTreasure())>playerPos.distanceTo(getTreasure()))
			hint = "colder";
		else
			hint = "same";
		
		playerPos = newPlayerPos;
		return hint;
	}

	public Coord getTreasure() {
		return treasure;
	}

	public void setTreasure(Coord treasure) {
		this.treasure = treasure;
	}	
}
