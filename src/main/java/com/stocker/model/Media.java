package com.stocker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.eclipse.persistence.annotations.Multitenant;

import com.stocker.model.base.BaseEntity;

@Entity
@Multitenant
public class Media extends BaseEntity
{
    @Id
    @Column(name = "ID", length = 10, nullable = false)
    private String id;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEDIA_TYPE", length = 20, nullable = false)
    private MediaType mediaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STOCK_SITE", length = 10, nullable = false)
    private StockSiteType stockSite;

    Media()
    {
    }

    public Media(StockSiteType site, String id, String title, MediaType mediaType)
    {
        this();
        this.stockSite = site;
        this.id = id;
        this.title = title;
        this.mediaType = mediaType;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public MediaType getMediaType()
    {
        return mediaType;
    }
}
