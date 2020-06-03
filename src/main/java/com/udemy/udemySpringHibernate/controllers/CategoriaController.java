package com.udemy.udemySpringHibernate.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udemy.udemySpringHibernate.domain.Categoria;
import com.udemy.udemySpringHibernate.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?>find(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);

		return ResponseEntity.ok().body(obj);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	//@RequestBody - Json é convertido para objeto JAVA automaticamente
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		//vai buscar o current request(/categorias) e definir o path /{id},
		//buildAndExpand - para atribuir o valor
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
	
		//created - metodo já predefinido equivalente ao insert(codigo 201 httpstatus)
	return ResponseEntity.created(uri).build();
	}
}
