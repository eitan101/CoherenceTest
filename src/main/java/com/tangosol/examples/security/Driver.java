
package com.tangosol.examples.security;


/**
* Driver executes all the security examples.
* <p>
* Examples are invoked in this order <p/>
* 1) Password enforcement<br/>
* 2) Cache access control<br/>
* 3) InvocationService access control<br/>
*
* @author dag  2010.04.15
*/
public class Driver
    {
    // ----- static methods -------------------------------------------------

    /**
    * Execute Security examples.
    * <p/>
    * usage: [cache-name]
    *
    * @param asArg command line arguments
    */
    public static void main(String[] asArg)
        {
        System.out.println("------security examples begin------");

        // Run password example
        PasswordExample.getCache();

        // Run cache access control example
        AccessControlExample.accessCache();

        // Run map listener access control example
        AccessControlExample.accessMapListener();

        // Run invocation service access control example
        AccessControlExample.accessInvocationService();

        System.out.println("------security examples completed------");
        }
    }