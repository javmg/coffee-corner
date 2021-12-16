package com.sample.view;

import com.sample.domain.ComponentId;
import java.util.List;

public class SingleComponentProduct implements Product {

    private final ComponentId id;

    public SingleComponentProduct(ComponentId id) {
        this.id = id;
    }

    @Override
    public List<ComponentId> getComponentIds() {
        return List.of(id);
    }
}
