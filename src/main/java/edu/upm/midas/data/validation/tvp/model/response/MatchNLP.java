package edu.upm.midas.data.validation.tvp.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchNLP {

	private Concept concept;
	private boolean hasMatches;
	private List<Match> matches;

	public MatchNLP(){}

	public MatchNLP(Concept concept) {
		this.concept = concept;
		this.matches = new ArrayList<>();
	}


	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
		this.hasMatches = matches.size() > 0;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setHasMatches(boolean b) {
		this.hasMatches = b;
	}

	public boolean hasMatches() {
		return hasMatches;
	}

	public boolean isHasMatches() {
		return hasMatches;
	}

	public String toWriteValidated() {
		String ret = "";
		ret += this.concept.getName() + ":\n";
		for (int i = 0; i < matches.size(); i++) {
			ret += "\t" + matches.get(i).toWriteValidated() + "\n";
		}
		return ret;
	}

	public String toWriteNotValidated() {
		return this.concept.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MatchNLP)) return false;
		MatchNLP matchNLP = (MatchNLP) o;
		return Objects.equals(getConcept(), matchNLP.getConcept());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getConcept());
	}
}
