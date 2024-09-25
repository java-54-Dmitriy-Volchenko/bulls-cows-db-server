package telran.net.games.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.net.games.BullsCowsTestPersistenceUnitInfo;
import telran.net.games.exceptions.*;
import telran.net.games.model.*;
import telran.net.games.repo.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BullsCowsServiceTest {
	private static final int N_DIGITS = 4;
	static BullsCowsRepository repository;
	static BullsCowsService bcService;
	static {
		HashMap<String, Object> hibernateProperties = new HashMap<>();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
		repository = new BullsCowsRepositoryJpa
				(new BullsCowsTestPersistenceUnitInfo(), hibernateProperties);
		BullsCowsGameRunner bcRunner = new BullsCowsGameRunner(N_DIGITS);
		bcService = new BullsCowsServiceImpl(repository, bcRunner);
		
	}
	 @Test
	    @Order(1)
	    void testCreateGame() {
	        
	        long gameId = bcService.createGame();
	        assertNotNull(gameId, "Game ID should not be null");
	       
	        String sequence = ((BullsCowsServiceImpl) bcService).getSequence(gameId);
	        assertEquals(N_DIGITS, sequence.length(), "Sequence length should be " + N_DIGITS);
	    }
	    
	    @Test
	    @Order(2)
	    void testRegisterGamer() {
	     
	        String username = "player1";
	        LocalDate birthDate = LocalDate.of(1990, 1, 1);
	        bcService.registerGamer(username, birthDate);
	        
	        assertDoesNotThrow(() -> repository.getGamer(username), "Gamer should be registered successfully");
	    }

	    @Test
	    @Order(3)
	    void testGamerJoinGame() {
	        
	        long gameId = bcService.createGame();
	        String username = "player1";
	        
	        assertDoesNotThrow(() -> bcService.gamerJoinGame(gameId, username), "Gamer should be able to join the game");
	        List<String> gamers = bcService.getGameGamers(gameId);
	        assertTrue(gamers.contains(username), "Gamer should be in the game");
	    }

	    @Test
	    @Order(4)
	    void testStartGame() {
	       
	        long gameId = bcService.createGame();
	        String username = "player1";
	        bcService.gamerJoinGame(gameId, username);
	        
	        List<String> gamers = bcService.startGame(gameId);
	        assertTrue(gamers.contains(username), "Game should start with the gamer");
	    }

	    @Test
	    @Order(5)
	    void testMoveProcessing() {
	        
	        long gameId = bcService.createGame();
	        String username = "player1";
	        bcService.gamerJoinGame(gameId, username);
	        bcService.startGame(gameId);
	        
	        
	        String correctGuess = ((BullsCowsServiceImpl) bcService).getSequence(gameId);
	        List<MoveData> moves = bcService.moveProcessing(correctGuess, gameId, username);
	        
	        assertNotNull(moves, "Moves list should not be null");
	        assertFalse(moves.isEmpty(), "Moves list should not be empty");
	        
	       
	        assertTrue(bcService.gameOver(gameId), "Game should be over after the correct guess");
	    }
	    
	    @Test
	    @Order(6)
	    void testIncorrectMove() {
	       
	        long gameId = bcService.createGame();
	        String username = "player1";
	        bcService.gamerJoinGame(gameId, username);
	        bcService.startGame(gameId);
	        
	        String incorrectGuess = "12345"; 
	        assertThrows(IncorrectMoveSequenceException.class, 
	            () -> bcService.moveProcessing(incorrectGuess, gameId, username),
	            "Incorrect move should throw IncorrectMoveSequenceException");
	    }
	}

