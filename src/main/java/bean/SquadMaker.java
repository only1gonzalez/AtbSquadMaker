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

	@PostConstruct
	public void init() {
		retrieveFreePlayers();
	}
	
	public void reset() {
		league = null;
		numberOfSquads = null;
		retrieveFreePlayers();
	}

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

		// Sort alphabetically
		Collections.sort(league.getPlayers());
	}

	/**
	 * [Create Squads] action.
	 * This method is called via button click, and creates and populates squads based on the number of squads selected.
	 */
	public void createSquads() {
		int numberOfPlayers = league.getPlayers().size();
		int playersPerTeam = numberOfPlayers / numberOfSquads;
		int numberLeftover = numberOfPlayers - (playersPerTeam * numberOfSquads);
		
		if (numberLeftover < 0) {
			numberLeftover = 0;
		}

		// Create all squads (for now empty)
		int i = 1;
		while (i <= numberOfSquads && i <= numberOfPlayers) {
			league.getSquads().add(new Squad("Squad " + i));
			i++;
		}

		// Make a local copy of all players
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.addAll(league.getPlayers());
		
		// Sort players by total skill
		Collections.sort(allPlayers, new PlayerSkillComparator().reversed());
		
		// Perform initial population of squads
		i = 0;
		while (i < allPlayers.size() - numberLeftover) {
			for (Squad squad : league.getSquads()) {
				Player player = allPlayers.get(i);
				squad.getPlayers().add(player);
				league.getPlayers().remove(player);
				i++;
			}
			Collections.reverse(league.getSquads());
		}
		
		// Refine squad population
		
		
		// Sort final squad players by name
		for (Squad squad : league.getSquads()) {
			Collections.sort(squad.getPlayers());
		}
	}
	
	public boolean isInputDisabled() {
		// The input should be disabled if squads have already been formed
		if (league.getSquads() != null && league.getSquads().size() > 0) {
			return true;
		}
		return false;
	}
	
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
