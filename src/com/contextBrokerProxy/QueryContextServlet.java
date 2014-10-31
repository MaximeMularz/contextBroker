package com.contextBrokerProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryContextServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String URL = "http://130.211.103.49:1026/NGSI10/queryContext";

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// Allow javascript XHRR, cross domain query
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Content-Type", "text/xml");

		// Read JSON from request
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /* report an error */
		}
		// Retourner le fichier json, pour test
		// res.getWriter().append(jb.toString());
		try {
			URL obj = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());
			out.write(jb.toString());
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				//Return XML Result
				res.getWriter().append(inputLine);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
