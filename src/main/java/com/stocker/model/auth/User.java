package com.stocker.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.stocker.model.base.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity
{
    @Id
    private String username;

    @Column(length = 30)
    private String password;

    @Column(name = "TENANT_ID", nullable = false)
    private Integer tenantId;

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public Integer getTenantId()
    {
        return tenantId;
    }
}
