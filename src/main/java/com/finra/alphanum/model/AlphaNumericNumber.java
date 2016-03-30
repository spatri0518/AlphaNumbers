package com.finra.alphanum.model;

import java.io.Serializable;

public class AlphaNumericNumber implements Comparable<AlphaNumericNumber>,
		Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String phone;
    
	public AlphaNumericNumber(String phone) {
		super();
		this.phone = phone;
	}

	public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlphaNumericNumber other = (AlphaNumericNumber) obj;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	@Override
	public int compareTo(AlphaNumericNumber obj) {
		return this.getPhone().compareTo(obj.getPhone());
	}

}