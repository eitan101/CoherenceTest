
package com.tangosol.examples.security;


import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import java.security.PrivilegedExceptionAction;

import javax.security.auth.Subject;


/**
* This class shows how a Coherence Proxy can require a password to get a
* reference to a cache.
* <p>
* The PasswordIdentityTransformer will generate a security token that
* contains the password. The PasswordIdentityAsserter will validate the
* security token to enforce the password. The token generation and
* validation occurs automatically when a connection to the proxy is made.
*
* @author dag 2010.04.16
*/
@SuppressWarnings (value="unchecked")
public class PasswordExample
    {
    // ----- static methods -------------------------------------------------

    /**
    * Get a reference to the cache. Password will be required.
    */
    public static void getCache()
        {
        System.out.println("------password example begins------");

        Subject subject = SecurityExampleHelper.login("BuckarooBanzai");

        try
            {
            NamedCache cache = (NamedCache) Subject.doAs(
                    subject, new PrivilegedExceptionAction()
                {
                public Object run()
                        throws Exception
                    {
                    NamedCache cache;

                    cache = CacheFactory.getCache(
                            SecurityExampleHelper.SECURITY_CACHE_NAME);
                    System.out.println("------password example succeeded------");
                    return cache;
                    }
                });
            }
        catch (Exception e)
            {
            // get exception if the password is invalid
            System.out.println("Unable to connect to proxy");
            e.printStackTrace();
            }
        System.out.println("------password example completed------");
        }
    }
