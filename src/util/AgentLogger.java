package util;

import jade.lang.acl.ACLMessage;

/**
 * @author Aymen Sassi
 */
public class AgentLogger {
	public static void log(ACLMessage message){
		if(message != null){
			System.out.println("\n" + message.getSender().getLocalName() + "> " + message.getContent());
		}
	}

}
