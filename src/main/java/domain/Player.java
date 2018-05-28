package domain;

import java.util.List;

/**
 * Player object.
 * Includes comparable implementation to allow default sort based on name.
 * 
 */
public class Player implements Comparable<Object> {
	private String firstName;
	private String lastName;
	private List<Skill> skills;

	// Transient variables to reduce number of times we loop through the skills
	private Integer shooting;
	private Integer skating;
	private Integer checking;

	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	/**
	 * @return Shooting skill rating
	 */
	public Integer getShooting() {
		if (shooting == null) {
			shooting = getSkillRating(SkillType.Shooting);
		}
		return shooting;
	}

	/**
	 * @return Skating skill rating
	 */
	public Integer getSkating() {
		if (skating == null) {
			skating = getSkillRating(SkillType.Skating);
		}
		return skating;
	}

	/**
	 * @return Checking skill rating
	 */
	public Integer getChecking() {
		if (checking == null) {
			checking = getSkillRating(SkillType.Checking);
		}
		return checking;
	}

	/**
	 * Gets a player's skill rating for a given skill.
	 * @param skillType - A Player skill
	 * @return Integer - Skill rating
	 */
	public Integer getSkillRating(SkillType skillType) {
		for (Skill skill : skills) {
			if (skill.getType() == skillType) {
				return skill.getRating();
			}
		}
		return 0;
	}

	public Integer getTotalRating() {
		return getShooting() + getSkating() + getChecking();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Player{" + "firstName='" + firstName + '\'' + ", lastName=" + lastName + '}';
	}

	@Override
	public int compareTo(Object o) {
		String aFullName = firstName + lastName;
		String bFullName = ((Player) o).getFirstName() + ((Player) o).getLastName();
		return aFullName.compareTo(bFullName);
	}
}