package domain;

import java.util.Comparator;

/**
 * Allows custom comparison of the Player object.
 * Specifically, it allows for Player to be sorted by their total skill rating.
 */
public class PlayerSkillComparator implements Comparator<Player> {

	@Override
	public int compare(Player a, Player b) {
		return a.getTotalRating().compareTo(b.getTotalRating());
	}
}