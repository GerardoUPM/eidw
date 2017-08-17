package edu.upm.midas.data.validation.tvp.model.response;

public class Match {

	private ValidationFinding validationFinding;
	private String validationFindingString;
	private String validationMethod;

	public Match(){}
	
	public Match(ValidationFinding vf, String vfs, String validationMethod) {
		this.validationFinding = vf;
		this.validationFindingString = vfs;
		this.validationMethod = validationMethod;
	}

	
	public String getValidationMethod() {
		return validationMethod;
	}

	public void setValidationMethod(String validationMethod) {
		this.validationMethod = validationMethod;
	}


	public ValidationFinding getValidationFinding() {
		return validationFinding;
	}

	public void setValidationFinding(ValidationFinding validationFinding) {
		this.validationFinding = validationFinding;
	}

	public String getValidationFindingString() {
		return validationFindingString;
	}


	public void setValidationFindingString(String validationFindingString) {
		this.validationFindingString = validationFindingString;
	}


	public String toWriteValidated() {
		String ret = "";
		ret += validationFindingString + "!" + validationFinding.getSource() + "!" + validationMethod;
		return ret;
	}

}
