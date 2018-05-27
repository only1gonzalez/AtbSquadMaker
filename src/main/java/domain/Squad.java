package domain;

import java.util.List;

public class Squad {
	private String id;
	private List<Player> players;

	public double averageShooting() {
		double total = 0;
		for (Player player : players) {
			total += player.getShooting();
		}
		return total/players.size();
	}
	
	public double averageSkating() {
		double total = 0;
		for (Player player : players) {
			total += player.getSkating();
		}
		return total/players.size();
	}
	
	public double averageChecking() {
		double total = 0;
		for (Player player : players) {
			total += player.getChecking();
		}
		return total/players.size();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
