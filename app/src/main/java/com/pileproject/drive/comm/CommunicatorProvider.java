/**
 * Copyright (C) 2011-2016 The Drive Authors <pile-dev@googlegroups.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pileproject.drive.comm;

import com.pileproject.drivecommand.model.com.ICommunicator;

/**
 * An interface for providing a communicator which makes connections between Android devices and machines.
 * Concrete classes are supposed to provide the concrete classes of {@link ICommunicator}
 * with respect to their responsible communication means.
 *
 */
public interface CommunicatorProvider {

    /**
     * Returns a concrete communicator.
     *
     * @return a concrete instance of {@link ICommunicator}
     */
    ICommunicator getCommunicator();
}
