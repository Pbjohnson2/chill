package com.swerve.chill.views.contracts;

import java.util.Collection;

public interface ModelFinder <E> {
    Collection<E> findModels(final ServiceAccessor serviceAccessor);
}
