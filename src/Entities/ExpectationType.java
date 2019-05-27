package com.stinja.birg.Entities;

public enum ExpectationType {
	EXPECTED ("XP") , ORDINARY ("OR"), EXTRAODINARY ("XT");

	public final String abbreviation;

	ExpectationType(String abbreviation) {
		this.abbreviation = abbreviation;
	}
}