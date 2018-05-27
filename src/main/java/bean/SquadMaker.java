package bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import domain.League;

@ManagedBean
@SessionScoped
public class SquadMaker implements Serializable {

	private static final long serialVersionUID = -4823295172962937652L;

	private String message = "Your SquadMaker is up and away!!!";
	private League league;

	@PostConstruct
	public void init() {
		league = new League();

		// Retrieve player list

		// populate waiting list
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
