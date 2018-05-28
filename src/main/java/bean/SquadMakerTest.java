package bean;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for unit testing SquadMaker.
 * Includes high level tests to ensure that both the JSON and Squad population are occurring successfully.
 */
public class SquadMakerTest {

	@Test
	public void testPlayerRetrievalIsSuccessful() {
		SquadMaker squadMaker = new SquadMaker();
		squadMaker.retrieveFreePlayers();
		assertTrue(!squadMaker.getLeague().getPlayers().isEmpty());
	}
	
	@Test
	public void testSquadCreation() {
		SquadMaker squadMaker = new SquadMaker();
		squadMaker.retrieveFreePlayers();
		squadMaker.setNumberOfSquads(6);
		squadMaker.createSquads();
		
		assertTrue(!squadMaker.getLeague().getSquads().isEmpty());
	}

}
