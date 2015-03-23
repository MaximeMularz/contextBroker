package com.contextBrokerProxy;

import java.util.regex.Pattern;

public class Validator {
	
	/**
	 * Validate IP address with regular expression.
	 * 
	 * @param ip IP address for validation
	 * @return true invalid IP address, false valid IP address
	 */
	public static boolean isNotValidIp(String ip) {
		return !isValidIp(ip);
	}
 
   /**
    * Validate IP address with regular expression.
    * 
    * @param ip IP address for validation
    * @return true valid IP address, false invalid IP address
    */
    public static boolean isValidIp(String ip){		  
	  return ipPattern.matcher(ip).matches();	    	    
    }
    
    /**
     * Pattern used to check IP addresses.
     */
    private static final String IP_ADDRESS_PATTERN =
    		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    /**
     * Pattern compiled to check IP addresses.
     */
    private static final Pattern ipPattern = Pattern.compile(IP_ADDRESS_PATTERN);
	
}
