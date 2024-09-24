package telran.net.games.exceptions;
@SuppressWarnings("serial")
public class GameAlreadyFinishedException extends IllegalStateException {
	public GameAlreadyFinishedException(long gameId) {
		super("Game " + gameId + " already finished");
	}
}
