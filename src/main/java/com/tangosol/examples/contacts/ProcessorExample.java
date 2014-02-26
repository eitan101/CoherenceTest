
package com.tangosol.examples.contacts;


import com.tangosol.examples.pof.Address;
import com.tangosol.examples.pof.OfficeUpdater;

import com.tangosol.net.NamedCache;

import com.tangosol.util.filter.EqualsFilter;


/**
* ProcessorExample demonstrates how to use a processor to modify data in the
* cache. All Contacts who live in MA will have their work address updated.
*
* @author dag  2009.02.26
*/
public class ProcessorExample
    {
    // ----- ProcessorExample methods -----------------------------------

    /**
    * Perform the example updates to contacts.
    *
    * @param cache  Cache
    */
    public void execute(NamedCache cache)
        {
        System.out.println("------ProcessorExample begins------");
        // People who live in Massachusetts moved to an in-state office
        Address addrWork = new Address("200 Newbury St.", "Yoyodyne, Ltd.",
                "Boston", "MA", "02116", "US");
       //Apply the OfficeUpdater on all contacts which lives in MA
        cache.invokeAll(new EqualsFilter("getHomeAddress.getState", "MA"),
                new OfficeUpdater(addrWork));
        System.out.println("------ProcessorExample completed------");
        }
    }
