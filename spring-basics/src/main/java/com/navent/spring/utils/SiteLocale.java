package com.navent.spring.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SiteLocale {

	private static Map<String, Locale> locales = new HashMap<>();
	
	static {
		locales.put("AR", new Locale("es", "AR"));
		locales.put("MX", new Locale("es", "MX"));
		locales.put("BR", new Locale("pt"));
	}
	
	public static Locale locale(String site) {
		return locales.get(StringUtils.substring(site, 2));
	}
	
}
