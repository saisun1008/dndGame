package dnd.game.event;

public class SpeakEvent extends GameEvent {
	private String message;
	
	public SpeakEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
