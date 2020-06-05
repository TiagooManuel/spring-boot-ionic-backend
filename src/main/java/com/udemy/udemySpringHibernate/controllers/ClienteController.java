package com.udemy.udemySpringHibernate.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.udemySpringHibernate.domain.Categoria;
import com.udemy.udemySpringHibernate.domain.Cliente;
import com.udemy.udemySpringHibernate.dto.ClienteDTO;
import com.udemy.udemySpringHibernate.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente>find(@PathVariable Integer id) {
		Cliente obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO);
		// para confirmar se o objeto atualizado é mesmo o que queremos
		// mais para a frente isto vai ser mudado para DTO
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		//1º percorre a lista da classe Cliente
		List<Cliente> list = service.findAll();
		//percorrer a lista e para cada elemento instancar o dto correspondente
		//converte uma lista para outra lista
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}
	
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			//aqui nao se vai fazer com PathVariable, mas sim com parametros opcionais para aparecer
			// /categorias/page?page=0&linesPerPage=20...
		
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}
}
