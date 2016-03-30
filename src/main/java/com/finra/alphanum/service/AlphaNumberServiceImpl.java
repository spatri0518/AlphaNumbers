package com.finra.alphanum.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.finra.alphanum.model.AlphaNumericNumber;

@Service
public class AlphaNumberServiceImpl implements AlphaNumberService {

	private static char[][] alphaKeyPad = new char[][] { { '0' }, { '1' },
			{ '2', 'A', 'B', 'C' }, { '3', 'D', 'E', 'F' },
			{ '4', 'G', 'H', 'I' }, { '5', 'J', 'K', 'L' },
			{ '6', 'M', 'N', 'O', }, { '7', 'P', 'Q', 'R', 'S' },
			{ '8', 'T', 'U', 'V' }, { '9', 'W', 'X', 'Y', 'Z' } };

	private void populateAlphaNumericCombinations(String number, int index,
			Set<AlphaNumericNumber> result) {
		List<String> currList = new ArrayList<String>();
		int num = Integer.parseInt(number.substring(index, index + 1));
		for (int i = 0; i < alphaKeyPad[num].length; i++) {
			char[] chars = number.toCharArray();
			chars[index] = alphaKeyPad[num][i];
			currList.add(String.valueOf(chars));
			result.add(new AlphaNumericNumber(String.valueOf(chars)));
		}

		if (index > 0) {
			for (String localNum : currList) {
				populateAlphaNumericCombinations(localNum, index - 1, result);
			}
		}
	}

	@Override
	public Set<AlphaNumericNumber> getAlphaNumericNumbers(String number) {
		String phoneNumber = number.replaceAll("\\s", "");
		Set<AlphaNumericNumber> result = new HashSet<AlphaNumericNumber>();
		this.populateAlphaNumericCombinations(phoneNumber, phoneNumber.length() - 1, result);
		return result;
	}

}
