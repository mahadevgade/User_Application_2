package com.avecircle.utils;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.avecircle.entity.City;
import com.avecircle.entity.Country;
import com.avecircle.entity.State;
import com.avecircle.repo.CityRepo;
import com.avecircle.repo.CountryRepo;
import com.avecircle.repo.StateRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		countryRepo.deleteAll();
		stateRepo.deleteAll();
		cityRepo.deleteAll();
		
		Country n1=new Country(1, "IND");
		Country n2=new Country(2, "USA");
		
		countryRepo.saveAll(Arrays.asList(n1,n2));
		
		State s1=new State(1, "MH", 1);
		State s2=new State(2, "MP", 1);
		State s3=new State(3, "Tex", 2);
		State s4=new State(4, "NY", 2);
		
		stateRepo.saveAll(Arrays.asList(s1,s2,s3,s4));
		
		City c1=new City(1, "mumbai", 1);
		City c2=new City(2, "pune", 1);
		City c3=new City(3, "Indore", 2);
		City c4=new City(4, "Bhopal", 2);
		City c5=new City(5, "Houston", 3);
		City c6=new City(6, "San Antonio", 3);
		City c7=new City(7, "Rochester", 4);
		City c8=new City(8, "New Rochelle", 4);

		cityRepo.saveAll(Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8));
		

	}

}
