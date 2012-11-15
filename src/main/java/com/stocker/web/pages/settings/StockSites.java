package com.stocker.web.pages.settings;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

import com.stocker.model.StockSite;
import com.stocker.services.SettingsService;
import com.stocker.web.components.Layout;

public class StockSites {
	@Component
	private Layout layout;

	@Property
	private StockSite stockSite;

	@Inject
	private SettingsService settingsService;

	public Iterable<StockSite> getStockSiteLogins() {
		return settingsService.getStockSiteLogins();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "addStockSiteForm")
	void addStockSite() {
		settingsService.addStockSite(stockSite);
	}
}
