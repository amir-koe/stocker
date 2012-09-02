package com.stocker.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseEntity
{
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "LAST_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @PrePersist
    void fillCreated()
    {
        created = new Date();
        lastModified = new Date();
    }

    void fillLastModified()
    {
        lastModified = new Date();
    }

    public Date getCreated()
    {
        return created;
    }

    public Date getLastModified()
    {
        return lastModified;
    }
}
