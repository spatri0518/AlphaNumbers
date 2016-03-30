package com.finra.alphanum.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PhoneValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return String.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		String phoneNo = (String) arg0;
		if (phoneNo != null && !phoneNo.isEmpty()) {
			
			phoneNo.replaceAll("\\s", "");
			// 7 or 10 digits 
			if (!(phoneNo.matches("\\d{10}|\\d{7}")
					// format 123 4567 or 123 456 7890
					|| phoneNo.matches("\\d{3}[\\s]{0,}\\d{3}[\\s]{0,}\\d{4}|\\d{3}[\\s]{0,}\\d{4}"))) {
				errors.rejectValue("number", "validation.phone.format",
						"Invalid Must be a number of length 7 digits or 10 digits.");
			}
		}
	}

}