/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.cdi.tck.tests.interceptors.definition.binding.overriding;

import static org.jboss.cdi.tck.TestGroups.INTERCEPTORS_SPEC;
import static org.testng.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.cdi.tck.AbstractTest;
import org.jboss.cdi.tck.shrinkwrap.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

/**
 * Test that interceptor bindings defined at method level override those defined at the class level.
 *
 * <p>
 * This test was originally part of Weld test suite.
 * <p>
 *
 * @author Jozef Hartinger
 * @author Martin Kouba
 */
@SpecVersion(spec = "cdi", version = "1.1 Final Release")
public class InterceptorBindingOverridingTest extends AbstractTest {

    @Inject
    private Pony pony;

    @Deployment
    public static WebArchive createTestArchive() {
        return new WebArchiveBuilder()
                .withTestClassPackage(InterceptorBindingOverridingTest.class)
                .withBeansXml(
                        Descriptors
                                .create(BeansDescriptor.class)
                                .createInterceptors()
                                .clazz(NegatingInterceptor.class.getName(), FastAgingInterceptor.class.getName(),
                                        SlowAgingInterceptor.class.getName()).up()).build();
    }

    @Test(groups = INTERCEPTORS_SPEC)
    // @SpecAssertion(section = INTERCEPTOR_BINDING_MEMBERS, id = "cb")
    public void testInterceptorBindingOverriden() {
        // getAge() returns 3, SlowAgingInterceptor adds one (4) and NegatingInterceptor negates (-4)
        assertEquals(pony.getAge(), -4);
    }

}
