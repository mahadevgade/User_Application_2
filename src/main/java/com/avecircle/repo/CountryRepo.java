package com.avecircle.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avecircle.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer>{

}
