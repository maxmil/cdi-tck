package org.jboss.jsr299.tck.tests.lookup.injection.non.contextual.ws;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name="SheepWS")
public class SheepWSEndPoint
{
   @Inject
   private Sheep sheep;
   
   @WebMethod
   public boolean isSheepInjected() {
      return (sheep != null);
   }
}