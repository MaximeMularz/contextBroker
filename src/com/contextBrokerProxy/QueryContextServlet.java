package com.contextBrokerProxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryContextServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		
		// Allow  JavaScript XHR request.
		res.addHeader(AC_ALLOW_ORIGIN, "*");
		res.addHeader(CONTENT_TYPE, APPLICATION_JSON);

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
		}

		
		try {
			// Get the target Context URL.
			URL url = new URL(URL_QUERY_CONTEXT);
			
			// Set a connection.
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			
			// Send the request data.
			OutputStreamWriter outStream = new OutputStreamWriter(
					connection.getOutputStream());
			outStream.write(buffer.toString());
			outStream.close();
			
			// Read the response.
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// Return the result as XML.
			String inputLine;
			while ((inputLine = inStream.readLine()) != null){
				res.getWriter().append(inputLine);
			}
			inStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	 * URL for request the queryContext.
	 */
	private static final String URL_QUERY_CONTEXT = "http://193.52.45.159:1026/NGSI10/queryContext";
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
}
