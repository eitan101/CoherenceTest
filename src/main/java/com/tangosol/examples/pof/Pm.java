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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author handasa
 */
public class Pm implements PortableObject {
    long id;
    String name;
    int[] fields;
    long targetId;
    Date dueDate;

    public Pm() {
    }

    public Pm(long id, String name, int[] fields, long targetId, Date dueDate) {
        this.id = id;
        this.name = name;
        this.fields = fields;
        this.targetId = targetId;
        this.dueDate = dueDate;
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

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Pm{" + "id=" + id + ", name=" + name + ", targetId=" + targetId + ", dueDate=" + dueDate + '}';
    }

    @Override
    public void readExternal(PofReader reader) throws IOException {
        id = reader.readInt(0);
        name = reader.readString(1);
        targetId = reader.readInt(2);
        dueDate = new Date(reader.readLong(3));        
        fields = reader.readIntArray(4);
    }

    @Override
    public void writeExternal(PofWriter writer) throws IOException {
        writer.writeLong(0, id);
        writer.writeString(1, name);
        writer.writeLong(2, targetId);
        writer.writeLong(3, dueDate.getTime());
        writer.writeIntArray(4, fields);
    }    
}
