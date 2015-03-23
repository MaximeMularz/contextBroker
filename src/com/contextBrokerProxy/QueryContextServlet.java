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
		
		allowCORS(res);
		
		try {
			res.getWriter().append(
					"You make a doGet on dev-mason.appspot.com:\n\nQUERY => "
					+ req.getQueryString());
		} catch (IOException e) {
			// TODO:  Report the error.
			e.printStackTrace();
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		LOGGER.log(Level.INFO, "Request received: " + req.toString());
		
		String targetIp = req.getParameter("targetIp");
		if (Validator.isNotValidIp(targetIp)) {
			// TODO: Send error to the client.
			return;
		}
		
		// Read JSON from request.
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			
			if (buffer.length() == 0)
				LOGGER.log(Level.INFO, "The request hasn't body content.");
		} catch (Exception e) {
			// TODO: Report the error.
			e.printStackTrace();
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}

		try {
			// Get the target Context URL.
			URL url = new URL(String.format(Constant.URL_NGSI10_QUERYCONTEXT, targetIp));
			LOGGER.log(Level.INFO, "Connection to Context Broker: " + url.toString());
			
			// Set a connection.
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty(Constant.ACCEPT, Constant.APPLICATION_JSON);
			connection.addRequestProperty(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			
			// Send the request data.
			OutputStreamWriter outStream = new OutputStreamWriter(
					connection.getOutputStream());
			outStream.write(buffer.toString());
			outStream.close();
			
			allowCORS(res);
			
			// Return Context Broker response as JSON.
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = inStream.readLine()) != null){
				res.getWriter().append(inputLine);
			}
			inStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Allow CORS request on the given HTTP response.
	 * 
	 * @param response HTTP to set Cross-Domain.
	 */
	private void allowCORS(HttpServletResponse response) {
		response.addHeader(Constant.AC_ALLOW_ORIGIN, "*");
		response.addHeader(Constant.ACCEPT, Constant.APPLICATION_JSON);
		response.addHeader(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
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
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
}
