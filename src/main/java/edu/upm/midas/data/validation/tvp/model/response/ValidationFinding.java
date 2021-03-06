package edu.upm.midas.data.validation.tvp.model.response;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ValidationFinding {

	private final String CHAR_SEPARATOR = "!";
	private String name;
	private String code;
	private String uri;
	private String source;
	private List<String> cuis;
	private List<String> synonyms;
	private List<String> codes;

	public ValidationFinding() {
		this.name = "n/a";
		this.code = "n/a";
		this.uri = "n/a";
		this.source = "n/a";
		this.cuis = new ArrayList<>();
		this.synonyms = new ArrayList<>();
		this.codes = new ArrayList<>();
	}

	public ValidationFinding(String n) {
		this.name = n;
		this.synonyms = new ArrayList<>();
		this.codes = new ArrayList<>();
		this.cuis = new ArrayList<>();
	}

	public ValidationFinding(String n, String c, String u, String cui, String s) {
		this.cuis = new ArrayList<>();
		this.synonyms = new ArrayList<>();
		this.codes = new ArrayList<>();
		this.name = n;
		this.code = c;
		this.uri = u;
		this.cuis.add(cui);
		this.source = s;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<String> getCuis() {
		return cuis;
	}

	public void setCuis(List<String> cuis) {
		this.cuis = cuis;
	}

	public void addCuis(List<String> cuis) {
		this.cuis.addAll(cuis);
	}
	public void addCui(String cui) {
		this.cuis.add(cui);
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String toString() {
		String ret = "\n";
		ret += "Name: " + name + "\n";
		ret += "ID (" + source + "): " + code + "\n";
		ret += "CUIs: " + getCuisPlainTextSeparatedArrobas().replace('@', ',') + "\n";
		ret += "URI: " + uri + "\n";
		ret += "Other codes: "
				+ getCodesPlainTextSeparatedArrobas().replace('@', ',') + "\n";
		ret += "Synonyms: "
				+ getSynonymsPlainTextSeparatedArrobas().replace('@', ',')
				+ "\n\n";
		return ret;
	}

	public String toWrite() {
		String synoms = getSynonymsPlainTextSeparatedArrobas();
		String altCodes = getCodesPlainTextSeparatedArrobas();
		String cuis = getCuisPlainTextSeparatedArrobas();
		return name + CHAR_SEPARATOR + code + CHAR_SEPARATOR + cuis + CHAR_SEPARATOR + uri + CHAR_SEPARATOR + synoms + CHAR_SEPARATOR
				+ altCodes + CHAR_SEPARATOR + source;
	}

	private String getCuisPlainTextSeparatedArrobas() {
		String cuist = "";
		for (int i = 0; i < this.cuis.size(); i++) {
			cuist += this.cuis.get(i) + "@";
		}
		if (cuist.length() > 0) {
			cuist = cuist.substring(0, cuist.length() - 1);
		}
		if (cuist.length() == 0) {
			cuist = "n/a";
		}
		return cuist;
	}
	
	private String getSynonymsPlainTextSeparatedArrobas() {
		String synoms = "";
		for (int i = 0; i < this.synonyms.size(); i++) {
			synoms += this.synonyms.get(i) + "@";
		}
		if (synoms.length() > 0) {
			synoms = synoms.substring(0, synoms.length() - 1);
		}
		if (synoms.length() == 0) {
			synoms = "n/a";
		}
		return synoms;
	}

	private String getCodesPlainTextSeparatedArrobas() {
		String codes = "";
		for (int i = 0; i < this.codes.size(); i++) {
			codes += this.codes.get(i) + "@";
		}
		if (codes.length() > 0) {
			codes = codes.substring(0, codes.length() - 1);
		}
		if (codes.length() == 0) {
			codes = "n/a";
		}
		return codes;
	}

	public void addSynonyms(LinkedList<String> synoms) {
		this.synonyms.addAll(synoms);
	}

	public void addCodes(LinkedList<String> codes) {
		this.codes.addAll(codes);

	}


}
