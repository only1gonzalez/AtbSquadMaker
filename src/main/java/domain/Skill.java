package domain;

/**
 * Skill object.
 * Contains the rating for a particular skill.
 */
public class Skill {
	private SkillType type;
	private Integer rating;

	public SkillType getType() {
		return type;
	}

	public void setType(SkillType type) {
		this.type = type;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
