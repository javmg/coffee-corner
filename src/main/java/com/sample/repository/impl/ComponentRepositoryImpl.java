package com.sample.repository.impl;

import static java.util.Optional.ofNullable;

import com.sample.domain.Component;
import com.sample.domain.ComponentId;
import com.sample.repository.ComponentRepository;
import java.util.Map;

/**
 * This is a dummy component repository using a map as a backing structure.
 */
public class ComponentRepositoryImpl implements ComponentRepository {

    private final Map<ComponentId, Component> mapIdAndComponent;

    public ComponentRepositoryImpl(Map<ComponentId, Component> mapIdAndComponent) {
        this.mapIdAndComponent = mapIdAndComponent;
    }

    @Override
    public Component getById(ComponentId id) {

        return ofNullable(mapIdAndComponent.get(id))
            .orElseThrow(() -> new IllegalArgumentException(
                String.format("Component with id '%s' not found.", id)));
    }

}
