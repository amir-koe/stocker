package com.stocker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;

import com.stocker.model.base.BaseEntity;

@Entity
@Table(name = "STOCK_SITE")
@Multitenant
public class StockSite extends BaseEntity
{
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STOCK_SITE", length = 10, nullable = false)
    private StockSiteType stockSite;

    @Column(name = "USER", length = 30)
    private String user;

    @Column(name = "PASSWORD", length = 30)
    private String password;

    public StockSite()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public StockSiteType getStockSite()
    {
        return stockSite;
    }

    public void setStockSite(StockSiteType stockSite)
    {
        this.stockSite = stockSite;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
