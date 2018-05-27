package domain;

import java.util.List;

public class League {
	private String id;
	private List<Squad> squads;
	private List<Player> waitlist;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Squad> getSquads() {
		return squads;
	}

	public void setSquads(List<Squad> squads) {
		this.squads = squads;
	}

	public List<Player> getWaitlist() {
		return waitlist;
	}

	public void setWaitlist(List<Player> waitlist) {
		this.waitlist = waitlist;
	}
}
