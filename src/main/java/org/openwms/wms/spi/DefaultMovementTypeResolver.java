/*
 * Copyright 2005-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.wms.spi;

import org.ameba.annotation.Measured;
import org.openwms.wms.MovementProperties;
import org.openwms.wms.MovementTarget;
import org.openwms.wms.api.MovementType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * A DefaultMovementTypeResolver.
 *
 * @author Heiko Scherrer
 */
@Validated
@Profile("!CUSTOM")
@Component
class DefaultMovementTypeResolver implements MovementTypeResolver {

    private final MovementProperties properties;

    DefaultMovementTypeResolver(MovementProperties properties) {
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Measured
    public Optional<MovementType> resolve(@NotEmpty String transportUnitBK, @NotEmpty String target) {
        MovementTarget movementTarget = properties.findTarget(target);
        return Optional.of(movementTarget.getType());
    }
}
