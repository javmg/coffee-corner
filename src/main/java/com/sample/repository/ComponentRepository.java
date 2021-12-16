package com.sample.repository;

import com.sample.domain.Component;
import com.sample.domain.ComponentId;

public interface ComponentRepository {

    Component getById(ComponentId id);
}
