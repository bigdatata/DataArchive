package com.lt.study.archive;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Set;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static java.util.Map targetDataSources;

    public void setTargetDataSources(java.util.Map targetDataSources){
        this.targetDataSources=targetDataSources;
        super.setTargetDataSources(targetDataSources);
    }

	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getCustomerType();
	}

    public static Set<String> getDataSourcesKey(){
        if(null==targetDataSources){
            return null;
        }
        return targetDataSources.keySet();
    }

}
