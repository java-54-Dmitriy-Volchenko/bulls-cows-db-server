package telran.net.games.exceptions;
@SuppressWarnings("serial")
public class IncorrectMoveSequenceException extends IllegalArgumentException  {
	public IncorrectMoveSequenceException(String sequence) {
		super("Not correct sequence  " + sequence);
	}
}
