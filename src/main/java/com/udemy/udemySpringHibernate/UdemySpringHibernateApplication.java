package com.udemy.udemySpringHibernate;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udemy.udemySpringHibernate.domain.Categoria;
import com.udemy.udemySpringHibernate.repositories.CategoriaRepository;

@SpringBootApplication
public class UdemySpringHibernateApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(UdemySpringHibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//para guardar no BD, usa-se o repository que é o responsavel por comunicar com a BD
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		
		
	}

}
