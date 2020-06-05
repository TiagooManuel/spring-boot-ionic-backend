package com.udemy.udemySpringHibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.udemy.udemySpringHibernate.domain.Cliente;
import com.udemy.udemySpringHibernate.domain.Cliente;
import com.udemy.udemySpringHibernate.dto.ClienteDTO;
import com.udemy.udemySpringHibernate.repositories.ClienteRepository;
import com.udemy.udemySpringHibernate.services.exceptions.DataIntegrityException;
import com.udemy.udemySpringHibernate.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	
	public Cliente update(Cliente obj) {
		//procurar o cliente no BD e depois chamar updateData para atualizar apenas os novos valores que se quer (e os outros nao aparecer a null)
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	

	
	
	public void delete(Integer id) {
		//verificar se o id existe
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluír porque há entidades relacionadas");
		}
		
	}
	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	//page vai encapsular informações sobre a paginação
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, org.springframework.data.domain.Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//a partir do DTO, construir um objeto Cliente
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	
	

}
