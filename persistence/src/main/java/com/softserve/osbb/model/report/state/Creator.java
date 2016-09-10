package com.softserve.osbb.model.report.state;

/**
 * Created by nazar.dovhyy on 05.09.2016.
 */
public interface Creator<T> {

    default T createByType(String type) {
        throw new UnsupportedOperationException();
    }

}
