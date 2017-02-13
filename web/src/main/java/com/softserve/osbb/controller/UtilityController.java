package com.softserve.osbb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.Utility;
import com.softserve.osbb.service.UtilityService;

@RestController
@CrossOrigin
@RequestMapping(value = "/restful/utility")
public class UtilityController {

	@Autowired
	private UtilityService utilityService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void saveUtilities(@RequestBody Utility utility) {

		utilityService.save(utility);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Utility> getAll() {

		return utilityService.getAll();
	}

}