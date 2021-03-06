package a1.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import a1.client.ClientState;
import a1.client.ClientStateFactory;
import a1.common.InitialConfigurations.BroadcastMode;
import a1.common.message.Message;
import util.interactiveMethodInvocation.ConsensusAlgorithm;
import util.interactiveMethodInvocation.IPCMechanism; 

public class Util {

	public static byte[] serializeMessage(Message msg) {
		try { 
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos); 
			oos.writeObject(msg); 
			oos.flush(); 
			return bos.toByteArray(); 
		} 
		catch (IOException e) { e.printStackTrace();}
		return null; 
	}
	
	public static Message deserializeMessage(byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = null;
	    try {
			is = new ObjectInputStream(in);
			return (Message) is.readObject(); 
		} catch (Exception e) {}
	    return null; 
	}
	
	public static BroadcastMode getBroadcastModeFromState() {
		ClientState state = ClientStateFactory.getState(); 
		return state.isAtomicBroadcast() == null ? null : 
				state.isLocalProcessingOnly() ? BroadcastMode.LOCAL : 
				state.isAtomicBroadcast() ? BroadcastMode.ATOMIC : BroadcastMode.NON_ATOMIC; 
	}
	
	public static long getSendDelay() {
		return ClientStateFactory.getState().getDelay(); 
	}
	
	public static IPCMechanism getIpcMechanismFromState() {
		return ClientStateFactory.getState().getIPCMechanism(); 
	}
	
	public static ConsensusAlgorithm getConsensusAlgorithmFromState() {
		return ClientStateFactory.getState().getConsensusAlgorithm(); 
	}
	
	public static String[] generateRandomSimulationCommands(int numCommands) {
		String[] commands = new String[numCommands]; 
		for (int i = 0; i < numCommands; i++) {
			double randVal = Math.random(); 
			if 		(randVal < .07) 	{ commands[i] = "Take " + Util.randomIntInRange(0,5, false); } 
			else if (randVal < .15) 	{ commands[i] = "Give " + Util.randomIntInRange(0,5, false); } 
			else if (randVal < .18) 	{ commands[i] = "Undo"; } 
			else 					{ commands[i] = "Move " + Util.randomIntInRange(0,100, true) + " " + Util.randomIntInRange(0,100, true); }
		}
		return commands; 
	}
	
	public static int randomIntInRange(int lower, int upper, boolean canBeNegative) { 
		return ((int) Math.ceil(Math.random() * (upper - lower) + lower)) * (canBeNegative ? (Math.random() < .5 ? 1 : -1) : 1);
	}
	
}
