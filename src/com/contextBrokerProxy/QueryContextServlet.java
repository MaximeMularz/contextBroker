package com.contextBrokerProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryContextServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		LOGGER.log(Level.INFO, "Request GET received...");
		
		// Allow  JavaScript XHR request.
		res.addHeader(AC_ALLOW_ORIGIN, "*");
		res.addHeader(CONTENT_TYPE, APPLICATION_JSON);
		
		try {
			res.getWriter().append("You make a doGet on dev-mason.appspot.com:\n\nQUERY => " + req.getQueryString());
		} catch (IOException e) {
			// TODO:  Report the error.
			e.printStackTrace();
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
		LOGGER.log(Level.INFO, "Request POST received...");
		
		// Read JSON from request.
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			// TODO: Report the error.
			e.printStackTrace();
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}

		LOGGER.log(Level.INFO, "Request received. Buffer.length = " + buffer.length());
		
		try {
			// Get the target Context URL.
			URL url = new URL(URL_QUERY_CONTEXT);
			
			// Set a connection.
			LOGGER.log(Level.INFO, "Connection to Context Broker...");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			
			// Send the request data.
			LOGGER.log(Level.INFO, "Sending data to CB...");
			OutputStreamWriter outStream = new OutputStreamWriter(
					connection.getOutputStream());
			outStream.write(buffer.toString());
			outStream.close();
			
			// Read the response.
			LOGGER.log(Level.INFO, "Receiving a response from CB...");
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// Allow  JavaScript XHR request.
			res.addHeader(AC_ALLOW_ORIGIN, "*");
			res.addHeader(CONTENT_TYPE, APPLICATION_JSON);
			
			// Return the result as XML.
			LOGGER.log(Level.INFO, "Sending response to client...");
			String inputLine;
			while ((inputLine = inStream.readLine()) != null){
				res.getWriter().append(inputLine);
			}
			inStream.close();
			
			LOGGER.log(Level.INFO, "Done.");
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Class name.
	 */
	private static final String CLASS = QueryContextServlet.class.getName();
	
	/**
	 * Default {@link Logger}.
	 */
	private static final Logger LOGGER = Logger.getLogger(CLASS);
	
	/**
	 * Constant: {@value #APPLICATION_JSON}
	 */
	private static final String APPLICATION_JSON = "application/json";
	
	/**
	 * Constant : {@value #AC_ALLOW_ORIGIN}
	 */
	private static final String AC_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	
	/**
	 * Constant : {@value #CONTENT_TYPE}
	 */
	private static final String CONTENT_TYPE = "Content-Type";
	
	/**
	 * Context Broker URL on Orion.
	 */
//	private static final String URL_QUERY_CONTEXT = "http://193.52.45.159:1026/NGSI10/queryContext";
	
	/**
	 * Context Broker URL on Google Compute Engine.
	 */
	private static final String URL_QUERY_CONTEXT = "http://130.211.103.49:1026/NGSI10/queryContext";
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
}
