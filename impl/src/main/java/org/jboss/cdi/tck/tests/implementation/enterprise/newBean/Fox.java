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
package org.jboss.cdi.tck.tests.implementation.enterprise.newBean;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

@SuppressWarnings("serial")
@Stateful
@SessionScoped
public class Fox implements FoxLocal {
    @Produces
    @Tame
    private static Den den = new Den("FoxDen");

    private int nextLitterSize;

    private boolean litterDisposed = false;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.cdi.tck.tests.implementation.enterprise.newBean.FoxLocal#getDen()
     */
    public Den getDen() {
        return den;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jboss.cdi.tck.tests.implementation.enterprise.newBean.FoxLocal#setDen(org.jboss.cdi.tck.tests.implementation
     * .enterprise.newBean.Den)
     */
    public void setDen(Den den) {
        this.den = den;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.cdi.tck.tests.implementation.enterprise.newBean.FoxLocal#getNextLitterSize()
     */
    public int getNextLitterSize() {
        return nextLitterSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.cdi.tck.tests.implementation.enterprise.newBean.FoxLocal#setNextLitterSize(int)
     */
    public void setNextLitterSize(int nextLitterSize) {
        this.nextLitterSize = nextLitterSize;
    }

    @Produces
    @Tame
    public Litter produceLitter() {
        return new Litter(nextLitterSize);
    }

    public void disposeLitter(@Disposes @Tame Litter litter) {
        this.litterDisposed = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.cdi.tck.tests.implementation.enterprise.newBean.FoxLocal#isLitterDisposed()
     */
    public boolean isLitterDisposed() {
        return litterDisposed;
    }
}
