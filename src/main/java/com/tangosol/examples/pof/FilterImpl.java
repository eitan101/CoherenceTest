/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tangosol.examples.pof;

import com.tangosol.examples.pof.Pm;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.Filter;
import java.io.IOException;

/**
 *
 * @author handasa
 */
public class FilterImpl implements Filter,PortableObject {
    public FilterImpl() {
    }

    @Override
    public boolean evaluate(Object pm) {
        return ((Pm) pm).getName().startsWith("a");
    }

    @Override
    public void readExternal(PofReader reader) throws IOException {
    }

    @Override
    public void writeExternal(PofWriter writer) throws IOException {
    }
    
}
