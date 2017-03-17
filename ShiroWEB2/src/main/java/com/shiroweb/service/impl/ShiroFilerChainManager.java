package com.shiroweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.shiroweb.entity.UrlFilter;

@Service
public class ShiroFilerChainManager {
	@Resource
	private DefaultFilterChainManager filterChainManager;  
	
    private Map<String, NamedFilterList> defaultFilterChains; 
    
    @PostConstruct  
    public void init() {  
        defaultFilterChains =   
          new HashMap<String, NamedFilterList>(filterChainManager.getFilterChains());  
    }  
    public void initFilterChains(List<UrlFilter> urlFilters) {  
        //1、首先删除以前老的filter chain并注册默认的  
        filterChainManager.getFilterChains().clear();  
        if(defaultFilterChains != null) {  
            filterChainManager.getFilterChains().putAll(defaultFilterChains);  
        }  
        //2、循环URL Filter 注册filter chain  
        for (UrlFilter urlFilter : urlFilters) {  
            String url = urlFilter.getUrl();  
            //注册roles filter  
            if (!StringUtils.isNullOrEmpty(urlFilter.getRoles())) {  
                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());  
            }  
            //注册perms filter  
            if (!StringUtils.isNullOrEmpty(urlFilter.getPermissions())) {  
                filterChainManager.addToChain(url, "perms", urlFilter.getPermissions());  
            }  
        }  
    } 
}
