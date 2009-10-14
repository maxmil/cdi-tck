/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.jsr299.tck.tests.lookup.injection.enterprise;

import org.jboss.jsr299.tck.AbstractJSR299Test;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.IntegrationTest;
import org.jboss.testharness.impl.packaging.Packaging;
import org.jboss.testharness.impl.packaging.PackagingType;
import org.testng.annotations.Test;

@Artifact
@IntegrationTest
@Packaging(PackagingType.EAR)
@SpecVersion(spec="cdi", version="PFD2")
public class SessionBeanInjectionTest extends AbstractJSR299Test
{

   @Test
   @SpecAssertions({
      @SpecAssertion(section = "5.6", id = "a"),
      @SpecAssertion(section = "5.6.4", id = "a")})
   public void testInjectionOnContextualSessionBean()
   {
      assert getInstanceByType(FarmLocal.class).isInjectionPerformedCorrectly();
   }

   @Test
   @SpecAssertions({
      @SpecAssertion(section = "5.6", id = "c"),
      @SpecAssertion(section = "5.6.4", id = "a")})
   public void testInjectionOnNonContextualSessionBean()
   {
      assert getInstanceByType(InjectedSessionBeanLocal.class).getFarm().isInjectionPerformedCorrectly();
   }

   @Test(groups = "jboss-as-broken")
   @SpecAssertion(section = "5.6", id = "ed")
   //JBAS-7046
   public void testInjectionOnEJBInterceptor()
   {
      // Test interceptor that intercepts contextual Session Bean
      assert getInstanceByType(FarmLocal.class).getAnimalCount() == 2;
      // Test interceptor that intercepts non-contextual Session Bean
      assert getInstanceByType(InjectedSessionBeanLocal.class).getFarm().getAnimalCount() == 2;
   }
   
   @Test
   @SpecAssertion(section = "4.2", id = "ab")
   public void testFieldDeclaredInSuperclassInjected()
   {
      DeluxeHenHouseLocal henHouse = getInstanceByType(DeluxeHenHouseLocal.class);
      assert henHouse.getFox() != null;
      assert henHouse.getFox().getName().equals("gavin");      
   }
   
   @Test
   @SpecAssertion(section = "4.2", id = "ad")
   public void testFieldDeclaredInSuperclassIndirectlyInjected()
   {
      MegaPoorHenHouseLocal henHouse = getInstanceByType(MegaPoorHenHouseLocal.class);
      assert henHouse.getFox() != null;
      assert henHouse.getFox().getName().equals("gavin");      
   }   
}