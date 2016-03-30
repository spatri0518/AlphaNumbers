package com.finra.alphanum.model;

import java.io.Serializable;

public class SearchCriteria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
