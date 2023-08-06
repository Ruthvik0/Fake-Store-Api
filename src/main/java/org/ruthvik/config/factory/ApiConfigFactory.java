package org.ruthvik.config.factory;

import org.aeonbits.owner.ConfigCache;
import org.ruthvik.config.ApiConfig;

public final class ApiConfigFactory {
	
	private ApiConfigFactory() {}

	public static ApiConfig getConfig() {
		return ConfigCache.getOrCreate(ApiConfig.class);
	}
}
