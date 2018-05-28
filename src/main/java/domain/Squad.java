package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Squad object.
 * Contains a list of all players assigned to a squad, along with methods for grabbing squad-wide skill averages.
 */
public class Squad {
	private String id;
	private List<Player> players = new ArrayList<Player>();

	public Squad(String id) {
		this.id = id;
	}
	
	public Integer getAverageShooting() {
		return skillAverage(SkillType.Shooting);
	}
	
	public Integer getAverageSkating() {
		return skillAverage(SkillType.Skating);
	}
	
	public Integer getAverageChecking() {
		return skillAverage(SkillType.Checking);
	}
	
	/**
	 * Determines the squad-wide average for a given skill.
	 * @param skillType - A Player skill
	 * @return Integer - Average rating
	 */
	public Integer skillAverage(SkillType skillType) {
		Integer total = 0;
		for (Player player : players) {
			switch (skillType) {
				case Shooting:
					total += player.getShooting();
					break;
				case Skating:
					total += player.getSkating();
					break;
				case Checking:
					total += player.getChecking();
					break;
			}
		}
		return total / players.size();
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
