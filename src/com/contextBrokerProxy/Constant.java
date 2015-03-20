package com.contextBrokerProxy;

public class Constant {
	
	/**
	 * Constant : {@value #AC_ALLOW_ORIGIN}
	 */
	public static final String AC_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	
	/**
	 * Constant : {@value #ACCEPT}
	 */
	public static final String ACCEPT = "Accept";
	
	/**
	 * Constant: {@value #APPLICATION_JSON}
	 */
	public static final String APPLICATION_JSON = "application/json";
	
	/**
	 * Constant : {@value #CONTENT_TYPE}
	 */
	public static final String CONTENT_TYPE = "Content-Type";

	/**
	 * URL format to make queryContext.
	 */
	public static final String URL_NGSI10_QUERYCONTEXT =
			"http://%s:1026/NGSI10/queryContext";
	
}
