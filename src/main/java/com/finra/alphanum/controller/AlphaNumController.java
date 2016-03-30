package com.finra.alphanum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.finra.alphanum.model.AlphaNumbersJsonObject;
import com.finra.alphanum.model.AlphaNumericNumber;
import com.finra.alphanum.model.SearchCriteria;
import com.finra.alphanum.service.AlphaNumberService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AlphaNumController {

	@Autowired
	private AlphaNumberService alphaNumericService;

	@Autowired
	@Qualifier("phoneValidator")
	private Validator phoneValidator;

	@RequestMapping(value = "/alphaNumbers.action", method = RequestMethod.GET)
	public String generateAlphaNumericNumbers(
			@ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
			ModelMap model, HttpServletRequest request,
			BindingResult bindingResult, HttpServletResponse response) {

		phoneValidator.validate(searchCriteria.getNumber(), bindingResult);

		List<AlphaNumericNumber> personsList = null;
		HttpSession session = request.getSession();

		if (!bindingResult.hasErrors()) {
			// Create page list data
			personsList = createPaginationData(searchCriteria.getNumber());

			if (session.isNew()) {
				// session time out 20 minutes
				session.setMaxInactiveInterval(20 * 60 * 1000);
			} else {
				session.removeAttribute("searchResult");
			}
		}
		session.setAttribute("searchResult", personsList);

		return "phoneNumberGenerator";
	}

	@RequestMapping(value = "/alphaNumbersPaging.action", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getAlphaNumbersData(HttpServletRequest request)
			throws IOException {

		// Fetch the page number from client
		Integer pageNumber = 0;
		if (null != request.getParameter("iDisplayStart")){
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;
		}
		
		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		// Create page list data
		List<AlphaNumericNumber> personsList = (List<AlphaNumericNumber>) request
				.getSession().getAttribute("searchResult");

		int totalRecords = 0;

		if (personsList != null && !personsList.isEmpty()) {
			totalRecords = personsList.size();
			int startIndex = (pageNumber == 1) ? 0 : (pageNumber - 1)
					* pageDisplayLength;

			if (startIndex > totalRecords) {
				startIndex = 0;
			}

			int endIndex = (totalRecords > (startIndex + pageDisplayLength)) ? (startIndex + pageDisplayLength)
					: totalRecords;

			if (personsList.size() > 0) {
				personsList = personsList.subList(startIndex, endIndex);
			}

		}

		AlphaNumbersJsonObject jsonObject = new AlphaNumbersJsonObject();
		// Set Total display record
		jsonObject.setiTotalDisplayRecords(totalRecords);
		// Set Total record
		jsonObject.setiTotalRecords(totalRecords);
		jsonObject.setAaData(personsList);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(jsonObject);

		return json2;
	}

	private List<AlphaNumericNumber> createPaginationData(String numberString) {
		List<AlphaNumericNumber> numbersList = new ArrayList<AlphaNumericNumber>();
		if (!StringUtils.isEmpty(numberString)) {
			Set<AlphaNumericNumber> numbers = alphaNumericService
					.getAlphaNumericNumbers(numberString);
			numbersList = new ArrayList<AlphaNumericNumber>(numbers);
			Collections.sort(numbersList);
		}
		return numbersList;
	}
}