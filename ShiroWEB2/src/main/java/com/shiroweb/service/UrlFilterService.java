package com.shiroweb.service;

import java.util.List;

import com.shiroweb.entity.UrlFilter;

public interface UrlFilterService {
	public UrlFilter createUrlFilter(UrlFilter urlFilter);  
    public UrlFilter updateUrlFilter(UrlFilter urlFilter);  
    public void deleteUrlFilter(Long urlFilterId);  
    public UrlFilter findOne(Long urlFilterId);  
    public List<UrlFilter> findAll();
}
