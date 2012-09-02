package com.stocker.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocker.model.StockSite;
import com.stocker.model.StockSiteRepository;

@Service
public class SettingsService
{
    @Inject
    private StockSiteRepository stockSiteRepository;

    public Iterable<StockSite> getStockSiteLogins()
    {
        return stockSiteRepository.findAll();
    }

    @Transactional
    public void addStockSite(StockSite stockSite)
    {
        stockSiteRepository.save(stockSite);
    }
}
