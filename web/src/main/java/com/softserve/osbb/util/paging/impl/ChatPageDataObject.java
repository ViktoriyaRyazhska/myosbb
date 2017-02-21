package com.softserve.osbb.util.paging.impl;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.softserve.osbb.dto.ChatDTO;
import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.util.paging.PageDataObject;

public class ChatPageDataObject extends PageDataObject<Resource<ChatDTO>>{

	private List<String> dates;
	
	public List<String> getDates() {
        return dates;
    }
	
}
