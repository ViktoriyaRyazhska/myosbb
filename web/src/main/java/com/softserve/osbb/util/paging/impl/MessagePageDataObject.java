package com.softserve.osbb.util.paging.impl;

import com.softserve.osbb.dto.MessageDTO;
import com.softserve.osbb.model.Message;
import com.softserve.osbb.util.paging.PageDataObject;
import org.springframework.hateoas.Resource;

import java.util.List;

/**
 * Created by Kris on 29.08.2016.
 */
public class MessagePageDataObject extends PageDataObject<Resource<MessageDTO>>{

        private List<String> dates;

        public List<String> getDates() {
            return dates;
        }

}
