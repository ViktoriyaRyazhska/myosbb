package com.softserve.osbb.util.filter;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by nazar.dovhyy on 04.09.2016.
 */
@Component
public interface FilterList<T, E> {

    default List<T> generateFilteredList() {
        throw new UnsupportedOperationException();
    }

    default List<T> generateFilteredList(String status, Page<E> page) {
        throw new UnsupportedOperationException();
    }

}
