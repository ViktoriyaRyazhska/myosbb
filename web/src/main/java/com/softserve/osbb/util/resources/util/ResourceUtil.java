package com.softserve.osbb.util.resources.util;

import com.softserve.osbb.util.resources.exceptions.ResourceNotFoundException;
import org.springframework.hateoas.Resource;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
public class ResourceUtil {

    public static <T> Resource<T> toResource(T entity) {
        if (entity == null) {
            throw new ResourceNotFoundException();
        }
        Resource<T> entityResource = new Resource<>(entity);
        return entityResource;
    }



}
