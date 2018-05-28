package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * League object.
 * Used to store created Squads as well as the waitlist of Players.
 */
public class League {
	private List<Squad> squads = new ArrayList<Squad>();
	private List<Player> players;

	public List<Squad> getSquads() {
		return squads;
	}

	public void setSquads(List<Squad> squads) {
		this.squads = squads;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
