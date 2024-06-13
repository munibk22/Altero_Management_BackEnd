package com.munib.coding.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@Service
public class DatabaseService {
	
	@Value("${database.username}")
	private String username;
	
	@Value("${database.password}")
	private String password;
	

}
