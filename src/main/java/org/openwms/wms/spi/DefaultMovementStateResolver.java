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

import org.openwms.wms.api.MovementState;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * A DefaultMovementStateResolver.
 *
 * @author Heiko Scherrer
 */
@Profile("!CUSTOM")
@Component
class DefaultMovementStateResolver implements MovementStateResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementState getNewState() {
        return DefaultMovementState.INACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementState getCompletedState() {
        return DefaultMovementState.DONE;
    }
}