package com.avecircle.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.avecircle.bindings.Quote;
import com.avecircle.props.AppProps;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private AppProps props;
	
	private Quote[] quotes = null;
	  
	@Override
	public String getQuote() {

		if (quotes==null) {
			
			RestTemplate rt=new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(props.getMessages().get("qouteURL"), String.class);
			String body = forEntity.getBody();
			
			ObjectMapper mapper=new ObjectMapper();
			try {
				quotes =mapper.readValue(body, Quote[].class);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		Random random=new Random();
		int index = random.nextInt(quotes.length-1);
		String text = quotes[index].getText();
		return text;
	}

}
