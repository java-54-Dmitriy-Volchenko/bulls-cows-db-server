package telran.net.games;
import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name = "game")
public class Game {
	@Id
	private long id;
	private LocalDate date;
	private boolean is_finished;
	private String sequence;
	
	
	
	public Game(long id, LocalDate date, boolean is_finished, String sequence) {
		super();
		this.id = id;
		this.date = date;
		this.is_finished = is_finished;
		this.sequence = sequence;
	}
	public Game() {
	}
	
	public long getId() {
		return id;
	}
	public LocalDate getDate() {
		return date;
	}
	public boolean isIs_finished() {
		return is_finished;
	}
	public String getSequence() {
		return sequence;
	}
	@Override
	public String toString() {
		return "Game [game id = " + id + ", game date = " + date + ", game finished? = " + is_finished + ", game sequence = " + sequence +"]";
	}
	
	
}
