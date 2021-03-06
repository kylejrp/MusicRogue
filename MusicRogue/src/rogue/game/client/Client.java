package rogue.game.client;

import java.util.Observable;
import java.util.Observer;

import rogue.game.message.Message;
import rogue.game.message.Message.MessageDetail;
import rogue.game.message.Message.MessageType;
import rogue.game.message.MessageHandler;
import rogue.game.state.InputBuffer.Input;

public abstract class Client extends Observable implements Observer, Runnable {
	final protected int clientNumber;
	static int currentNumber = 1;

	public Client() {
		this.clientNumber = currentNumber++;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MessageHandler) {
			Message msg = (Message) arg;
			recieveMessage(msg);
		}
	}

	public abstract void recieveMessage(Message msg);
	
	public void sendInput(Input input){
		setChanged();
		notifyObservers(input);
	}
	
	public String toString(){
		return this.getClass().getSimpleName() + "("+ clientNumber + ")";
	}
	
	public boolean equals(Object obj){
		if (obj instanceof Client){
			return clientNumber == ((Client) obj).clientNumber;
		} else {
			return false;
		}
	}

	public void notifyGameStart() {
		setChanged();
		Message msg = new Message(MessageType.SERVER);
		msg.setDetail(MessageDetail.CREATE);
		notifyObservers(msg);
	}
}
