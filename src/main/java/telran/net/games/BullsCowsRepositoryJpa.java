package telran.net.games;

import java.time.*;
import java.util.*;

import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.*;
import jakarta.persistence.spi.*;
import telran.net.games.exceptions.GameNotFoundException;
import telran.net.games.exceptions.GamerAlreadyExistsdException;
import telran.net.games.exceptions.GamerNotFoundException;

public class BullsCowsRepositoryJpa implements BullsCowsRepository {
	private EntityManager em;
	public BullsCowsRepositoryJpa(PersistenceUnitInfo persistenceUnit,
			HashMap<String, Object> hibernateProperties) {
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(persistenceUnit, hibernateProperties);
		em = emf.createEntityManager();
		
	}
	@Override
	public Game getGame(long id) {
		Game game = em.find(Game.class, id);
		if(game == null) {
			throw new GameNotFoundException(id);
		}
		return game;
	}

	@Override
	public Gamer getGamer(String username) {
		Gamer gamer = em.find(Gamer.class, username);
		if(gamer == null) {
			throw new GamerNotFoundException(username);
		}
		return gamer;
	}

	@Override
	public long createNewGame(String sequence) {
		Game game = new Game(null, false, sequence);
			createObject(game);
		return game.getId();
	}
	
	private <T>void createObject(T obj) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(obj);
		transaction.commit();
	}

	@Override
	public void createNewGamer(String username, LocalDate birthdate) {
		try {
			Gamer gamer = new Gamer(username, birthdate);
			createObject(gamer);
		} catch (Exception e) {
			throw new GamerAlreadyExistsdException(username);
		}

	}

	@Override
	public boolean isGameStarted(long id) {
		Game game = getGame(id);
		return game.getDate() != null;
	}

	@Override
	public void setStartDate(long gameId, LocalDateTime dateTime) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		Game game = getGame(gameId);
		game.setDate(dateTime);
		transaction.commit();

	}

	@Override
	public boolean isGameFinished(long id) {
		Game game = getGame(id);
		return game.isfinished();
		
	}

	@Override
	public void setIsFinished(long gameId) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		Game game = getGame(gameId);
		game.setfinished(true);
		transaction.commit();

	}

	
//	@Override
//	public void setGameGamerWinner(long gameId, String username) {
//	  //the same as setWinner method
//	}



	
	@Override
	public List<Long> getGameIdsNotStarted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getGameGamers(long id) {
		TypedQuery<String> query = em.createQuery(
				"select gamer.username from GameGamer where game.id=?1",
				String.class);
		return query.setParameter(1, id).getResultList();
	}

	@Override
	public void createGameGamer(long gameId, String username) {
		Game game = getGame(gameId);
		Gamer gamer = getGamer(username);
		GameGamer gameGamer = new GameGamer(false, game, gamer);
		createObject(gameGamer);

	}

	@Override
	public void createGameGamerMove(MoveDto moveDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MoveData> getAllGameGamerMoves(long gameId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWinner(long gameId, String username) {
	
	EntityTransaction transaction = em.getTransaction();
	
	transaction.begin();
		
		GameGamer gameGamer = getGameGamer (gameId, username);
		gameGamer.isWinner=true;
	    
	    Game game = getGame(gameId);
	    game.setfinished(true);
		    
	    em.persist(gameGamer);
	    em.persist(game);
		
	transaction.commit();

	}

	private GameGamer getGameGamer(long gameId, String username) {

		    
		  TypedQuery<GameGamer> query = em.createQuery(
		    		
		    "select record from GameGamer record where record.game.id = ?1 AND record.gamer.username = ?2",
		    	GameGamer.class);
		    	query.setParameter(1, gameId);
		    	query.setParameter(2, username);
		    	GameGamer gameGamer = query.getSingleResult();
		    
		    if (gameGamer == null) {
		    		throw new GamerNotFoundException("No such gamer in the game");
		    }
		    
		    return gameGamer;
		
	}
	@Override
	public boolean isWinner(long gameId, String username) {
		
	GameGamer gameGamer = getGameGamer(gameId, username);
	
	return gameGamer.isWinner ==true;
	
		
		
		
	}

}
