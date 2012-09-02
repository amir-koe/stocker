package com.stocker.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockSiteRepository extends CrudRepository<StockSite, Integer>
{
}
