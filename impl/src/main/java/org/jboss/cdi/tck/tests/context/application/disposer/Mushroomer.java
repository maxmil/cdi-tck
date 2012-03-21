/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.cdi.tck.tests.context.application.disposer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.jboss.cdi.tck.SimpleLogger;

/**
 * @author Martin Kouba
 */
public class Mushroomer {

    private static final SimpleLogger logger = new SimpleLogger(Mushroomer.class);

    private final long id = System.currentTimeMillis();

    @Produces
    @Edible
    @RequestScoped
    public Mushroom pickMushroom() {
        return new Mushroom("Boletus");
    }

    public void eatMushroom(@Disposes @Edible Mushroom mushroom, Forest forest) {
        logger.log("Dispose mushroom - {0}", this.toString());
        forest.setEmpty();
    }

    @Override
    public String toString() {
        return "Mushroomer [id=" + id + "]";
    }

}
