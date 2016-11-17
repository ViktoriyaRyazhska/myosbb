/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.TicketDTO;
import com.softserve.osbb.model.Ticket;

/**
 * Created by Kris on 22.08.2016.
 */
public class TicketDTOMapper {

    public static TicketDTO mapTicketEntityToDTO(Ticket ticket) {
        TicketDTO ticketDTO = null;

        if (ticket != null) {
            ticketDTO = new TicketDTO();
            ticketDTO.setTicketId(ticket.getTicketId());
            ticketDTO.setName(ticket.getName());
            ticketDTO.setDescription(ticket.getDescription());
            ticketDTO.setState(ticket.getState());
            ticketDTO.setStateTime(ticket.getStateTime());
            ticketDTO.setTime(ticket.getTime());
            ticketDTO.setUser(UserDTOMapper.mapUserEntityToDTO(ticket.getUser()));
            ticketDTO.setAssigned(UserDTOMapper.mapUserEntityToDTO(ticket.getAssigned()));
        }

        return ticketDTO;
    }

}
