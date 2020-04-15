package com.miniproject.Astral.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class AstralController {

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/apod")
	@ResponseBody
	public String Apod(HttpServletRequest request) {
		String url = "https://api.nasa.gov/planetary/apod?date=" + request.getQueryString().split("=")[1] + "&hd=false&api_key=BGwoonrtzeh2fIudyfji8VleNRH9iHlDxT7fGhh7";
		URLConnection connection;
		Gson gson = new Gson();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			connection = new URL(url).openConnection();
			connection.connect();
			String josnString = (new BufferedReader(
					new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")))).readLine();
			System.out.println(josnString);

			map = (HashMap<String, Object>) gson.fromJson(josnString, map.getClass());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (new StringBuffer())
					.append("<div class='container-fluid jumbotron-fluid mx-auto d-block'>")
					.append("	<div class='container-fluid jumbotron-fluid mx-auto d-block px-5 pb-5 m-5'>")
					.append( map.containsKey("title")? "		<h2 class='w-100 mt-3 mb-5'>" + map.get("title") + "</h2>": "")
					.append("		<div class='row'>")
       // 			.append( map.containsKey("hdurl")? "			<img class='p-3 rounded-lg shadow' id='APoD-hd-img' src='" + map.get("hdurl") +"'>": ( map.containsKey("url")? "			<img class='p-3 rounded-lg shadow w-100' id='APoD-hd-img' src='" + map.get("url") +"'>": ""))
	    			.append( map.containsKey("url")? "			<img class='p-3 rounded-lg shadow w-100' id='APoD-hd-img' src='" + map.get("url") +"'>": "")
					.append("			<span class='col-sm mx-auto my-auto pl-5'>")
					.append( map.containsKey("explanation")? "				<p class='text-justify mt-5'> " + map.get("explanation") +"</p>": "")
					.append("			</span>")
					.append("		</div>")
					.append("	</div>")
					.append("</div>")
					.toString();
	}
}
