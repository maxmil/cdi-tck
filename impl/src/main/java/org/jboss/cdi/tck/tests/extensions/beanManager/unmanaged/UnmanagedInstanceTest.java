/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
package org.jboss.cdi.tck.tests.extensions.beanManager.unmanaged;

import static org.jboss.cdi.tck.TestGroups.INTEGRATION;
import static org.jboss.cdi.tck.cdi.Sections.BIZ_METHOD;
import static org.jboss.cdi.tck.cdi.Sections.BM_OBTAIN_UNMANAGED_INSTANCE;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.enterprise.inject.spi.Unmanaged;
import javax.enterprise.inject.spi.Unmanaged.UnmanagedInstance;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.cdi.tck.AbstractTest;
import org.jboss.cdi.tck.shrinkwrap.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

/**
 *
 * @author Martin Kouba
 */
@SpecVersion(spec = "cdi", version = "1.1 Final Release")
public class UnmanagedInstanceTest extends AbstractTest {

    @Deployment
    public static WebArchive createTestArchive() {
        return new WebArchiveBuilder().withTestClassPackage(UnmanagedInstanceTest.class).build();
    }

    @Test(groups = INTEGRATION)
    @SpecAssertions({ @SpecAssertion(section = BM_OBTAIN_UNMANAGED_INSTANCE, id = "a") })
    public void testObtainNonContextualInstance() {

        Builder.reset();
        Nail.reset();
        Hammer.reset();

        Unmanaged<Builder> unmanagedBuilder = new Unmanaged<Builder>(getCurrentManager(), Builder.class);
        UnmanagedInstance<Builder> unmanagedBuilderInstance = unmanagedBuilder.newInstance();
        Builder builder = unmanagedBuilderInstance.produce().inject().postConstruct().get();
        builder.build();

        assertTrue(Builder.postConstructCalled);
        assertTrue(Nail.postConstructCalled);
        assertTrue(Hammer.postConstructCalled);

        unmanagedBuilderInstance.preDestroy().dispose();

        assertTrue(Builder.preDestroyCalled);
        assertTrue(Nail.preDestroyCalled);
        assertFalse(Hammer.preDestroyCalled);
    }

    @Test(groups = INTEGRATION)
    @SpecAssertions({ @SpecAssertion(section = BIZ_METHOD, id = "ac") })
    public void testNonContextualInstanceIsIntercepted() {

        ToolInterceptor.intercepted = false;

        Unmanaged<Axe> unmanagedAxe = new Unmanaged<Axe>(getCurrentManager(), Axe.class);
        UnmanagedInstance<Axe> unmanagedAxeInstance = unmanagedAxe.newInstance();
        unmanagedAxeInstance.produce().inject().postConstruct().get().cut();
        unmanagedAxeInstance.preDestroy().dispose();

        assertTrue(ToolInterceptor.intercepted);
    }

}
