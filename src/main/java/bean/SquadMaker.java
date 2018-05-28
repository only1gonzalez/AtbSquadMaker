package bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.Gson;

import domain.League;
import domain.Player;
import domain.PlayerSkillComparator;
import domain.Squad;

/**
 * SquadMaker backing bean.
 * This bean handles the JSON import, and league population.
 */
@ManagedBean
@SessionScoped
public class SquadMaker implements Serializable {

	private static final long serialVersionUID = -4823295172962937652L;

	private League league;
	private Integer numberOfSquads;

	/**
	 * Initializes the screen on creation.
	 */
	@PostConstruct
	public void init() {
		retrieveFreePlayers();
	}
	
	/**
	 * [Reset] button action.
	 */
	public void reset() {
		league = null;
		numberOfSquads = null;
		retrieveFreePlayers();
	}

	/**
	 * Retrieves players from JSON file and imports them into the league.
	 * If we are grabbing JSON from an API, this method would need to be updated.
	 */
	protected void retrieveFreePlayers() {
		try {
			// Take JSON file from resource and create string representation
			InputStream inputStream = SquadMaker.class.getResourceAsStream("/players.json");
			BufferedReader streamReader;
			streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputString = null;
			while ((inputString = streamReader.readLine()) != null) {
				responseStrBuilder.append(inputString);
			}

			// Map string JSON attributes to the League object, which includes players, skills, etc.
			league = new Gson().fromJson(responseStrBuilder.toString(), League.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		league.standardizePlayerSkills();
		
		// Sort alphabetically
		Collections.sort(league.getPlayers());
	}

	/**
	 * [Create Squads] button action.
	 * This method is called via button click, and creates and populates squads based on the number of squads selected.
	 */
	public void createSquads() {

		// Ensure the number of squads does not exceed the number of players
		int numberOfPlayers = league.getPlayers().size();
		int actualNumberOfSquads = numberOfSquads;
		if(numberOfSquads > numberOfPlayers) {
			actualNumberOfSquads = numberOfPlayers;
		}
		
		int playersPerTeam = numberOfPlayers / actualNumberOfSquads;
		int numberLeftover = numberOfPlayers - (playersPerTeam * actualNumberOfSquads);
		
		if (numberLeftover < 0) {
			numberLeftover = 0;
		}

		// Create all squads (for now empty)
		int i = 1;
		while (i <= actualNumberOfSquads) {
			league.getSquads().add(new Squad("Squad " + i));
			i++;
		}

		// Make a local copy of all players
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.addAll(league.getPlayers());
		
		// Sort players by their total standardized skill
		Collections.sort(allPlayers, new PlayerSkillComparator().reversed());
		
		// Perform population of squads
		i = 0;
		List<Squad> squads = new ArrayList<Squad>();
		squads.addAll(league.getSquads());
		while (i < allPlayers.size() - numberLeftover) {
			for (Squad squad : squads) {
				Player player = allPlayers.get(i);
				squad.getPlayers().add(player);
				league.getPlayers().remove(player);
				i++;
			}
			
			// Swap recent additions between squads if it lowers skill difference
			league.setSkillDifference(league.calcSkillDifference());
			for (Squad squadA : squads) {
				for (Squad squadB : squads) {
					if (squadA.getId().equals(squadB.getId())) {
						continue;
					}
					// If the swap results in smaller team differences, keep. Otherwise swap back.
					swapSquadPlayer(squadA, squadB);
					if (league.isSkillDifferenceBetter()) {
						continue;
					}
					swapSquadPlayer(squadA, squadB);
				}
			}
		}

		// Sort final squad players by name
		for (Squad squad : league.getSquads()) {
			Collections.sort(squad.getPlayers());
		}
	}
	
	/**
	 * Swaps the last player from squad A with the last player from squad B.
	 * @param squadA - Squad A
	 * @param squadB - Squad B
	 */
	private void swapSquadPlayer(Squad squadA, Squad squadB) {
		Player playerA = squadA.getPlayers().get(squadA.getPlayers().size() - 1);
		squadA.getPlayers().remove(playerA);

		Player playerB = squadB.getPlayers().get(squadB.getPlayers().size() - 1);
		squadB.getPlayers().remove(playerB);
		
		squadA.getPlayers().add(playerB);
		squadB.getPlayers().add(playerA);
	}
	
	/**
	 * The input should be disabled if squads have already been formed.
	 * @return boolean
	 */
	public boolean isInputDisabled() {
		if (league.getSquads() != null && league.getSquads().size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Indicates that the waiting list is empty.
	 * @return boolean
	 */
	public boolean isWaitingListEmpty() {
		if (league.getPlayers() == null || league.getPlayers().size() == 0) {
			return true;
		}
		return false;
	}
	
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Integer getNumberOfSquads() {
		return numberOfSquads;
	}

	public void setNumberOfSquads(Integer numberOfSquads) {
		this.numberOfSquads = numberOfSquads;
	}
}
