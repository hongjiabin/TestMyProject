package com.shiroweb.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shiroweb.dao.UrlFilterDao;
import com.shiroweb.entity.UrlFilter;
import com.shiroweb.service.UrlFilterService;

@Service
public class UrlFilterServiceImpl implements UrlFilterService {
	
	@Resource
	UrlFilterDao urlFilterDao;
	
	@Resource
	ShiroFilerChainManager  shiroFilerChainManager;

	@Override
	public UrlFilter createUrlFilter(UrlFilter urlFilter) {
		urlFilterDao.createUrlFilter(urlFilter);
        initFilterChain();
        return urlFilter;
	}

	@Override
    public UrlFilter updateUrlFilter(UrlFilter urlFilter) {
        urlFilterDao.updateUrlFilter(urlFilter);
        initFilterChain();
        return urlFilter;
    }

    @Override
    public void deleteUrlFilter(Long urlFilterId) {
        urlFilterDao.deleteUrlFilter(urlFilterId);
        initFilterChain();
    }

    @Override
    public UrlFilter findOne(Long urlFilterId) {
        return urlFilterDao.findOne(urlFilterId);
    }

    @Override
    public List<UrlFilter> findAll() {
        return urlFilterDao.findAll();
    }
	
	@PostConstruct
    public void initFilterChain() {
        shiroFilerChainManager.initFilterChains(findAll());
	}

}
