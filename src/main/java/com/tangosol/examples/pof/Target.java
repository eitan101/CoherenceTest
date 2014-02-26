/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tangosol.examples.pof;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import java.io.IOException;

/**
 *
 * @author handasa
 */
public class Target implements PortableObject {
    long id;
    int[] fields;
    String name;

    public Target() {
    }

    public Target(long id, int[] fields, String name) {
        this.id = id;
        this.fields = fields;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void readExternal(PofReader reader) throws IOException {
        id = reader.readLong(0);
        name = reader.readString(1);
        fields = reader.readIntArray(2);
    }

    @Override
    public void writeExternal(PofWriter writer) throws IOException {
        writer.writeLong(0, id);
        writer.writeString(1, name);
        writer.writeIntArray(2, fields);
    }
    
    
}
