package com.finra.alphanum.service;

import java.util.Set;

import com.finra.alphanum.model.AlphaNumericNumber;

public interface AlphaNumberService {
	public Set<AlphaNumericNumber> getAlphaNumericNumbers(String number);
}
