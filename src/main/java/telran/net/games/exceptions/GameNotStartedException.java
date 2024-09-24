package telran.net.games.exceptions;
@SuppressWarnings("serial")
public class GameNotStartedException extends IllegalStateException {
	public GameNotStartedException(long gameId) {
		super("Game " + gameId + " exists, but not started yet");
	}
}
