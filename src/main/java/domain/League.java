package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * League object. Used to store created Squads as well as the waitlist of Players.
 * Also contains methods for standardizing player skills across the league, as well as comparing skill totals across
 * all squads in the league.
 */
public class League {
	private List<Squad> squads = new ArrayList<Squad>();
	private List<Player> players;
	
	// The current skill difference between squads
	private Integer skillDifference;

	/**
	 * Since generally players might rank higher for some skills as opposed to
	 * others, we can prevent one skill dominating the others by standardizing
	 * them among players. One way to do this is to base rankings on skills as
	 * they compare to everyone else.
	 */
	public void standardizePlayerSkills() {
		// Get totals
		BigDecimal shootingTotal = BigDecimal.ZERO;
		BigDecimal skatingTotal = BigDecimal.ZERO;
		BigDecimal checkingTotal = BigDecimal.ZERO;

		for (Player player : players) {
			shootingTotal = shootingTotal.add(new BigDecimal(player.getShooting()));
			skatingTotal = skatingTotal.add(new BigDecimal(player.getSkating()));
			checkingTotal = checkingTotal.add(new BigDecimal(player.getChecking()));
		}

		// Use totals to determine the percentage of the total skill that each
		// player possesses. This can then be used
		// to rank all skills fairly.
		for (Player player : players) {
			player.setStandardizedShooting(new BigDecimal(player.getShooting())
					.divide(shootingTotal, 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
			player.setStandardizedSkating(new BigDecimal(player.getSkating())
					.divide(skatingTotal, 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
			player.setStandardizedChecking(new BigDecimal(player.getChecking())
					.divide(checkingTotal, 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
		}
	}

	/**
	 * Compares the squad skill difference to the previously compared value.
	 * @return boolean - true if the skillDifference is better now than before
	 */
	public boolean isSkillDifferenceBetter() {
		int newDifference = calcSkillDifference();
		if (newDifference < skillDifference) {
			skillDifference = newDifference;
			return true;
		}
		return false;
	}
	
	/**
	 * Finds the numerical difference of the max and min from each discipline across all squads.
	 * The max/min difference across shooting, skating, and checking is determined and summed.
	 * @return int - total difference of skills
	 */
	public int calcSkillDifference() {
		int maxShooting = 0;
		int minShooting = 100;
		int maxSkating = 0;
		int minSkating = 100;
		int maxChecking = 0;
		int minChecking = 100;
		
		for (Squad squad : squads) {
			int shooting = squad.getAverageShooting();
			int skating = squad.getAverageSkating();
			int checking = squad.getAverageChecking();
			
			if (maxShooting < shooting) {
				maxShooting = shooting;
			}
			if (minShooting > shooting) {
				minShooting = shooting;
			}
			if (maxSkating < skating) {
				maxSkating = skating;
			}
			if (minSkating > skating) {
				minSkating = skating;
			}
			if (maxChecking < checking) {
				maxChecking = checking;
			}
			if (minChecking > checking) {
				minChecking = checking;
			}
		}
		
		return (maxShooting - minShooting) + (maxSkating - minSkating) + (maxChecking - minChecking);
	}
	
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

	public Integer getSkillDifference() {
		return skillDifference;
	}

	public void setSkillDifference(Integer skillDifference) {
		this.skillDifference = skillDifference;
	}
}
