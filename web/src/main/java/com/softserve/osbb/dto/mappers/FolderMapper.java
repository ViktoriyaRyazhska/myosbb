package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.FolderDTO;
import com.softserve.osbb.model.Folder;

/**
 * Folder to FolderDTO and FolderDTO to Folder mapper helper class. 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 06.12.2016
 *
 */
public class FolderMapper {

    public static Folder toFolder(FolderDTO dto) {
        Folder folder = new Folder();
        folder.setId(dto.getId());
        folder.setName(dto.getName());
        return folder;
    }
    
    public static FolderDTO toDTO(Folder folder) {
        FolderDTO dto = new FolderDTO();
        dto.setId(folder.getId());
        dto.setName(folder.getName());
        return dto;
    }
}
