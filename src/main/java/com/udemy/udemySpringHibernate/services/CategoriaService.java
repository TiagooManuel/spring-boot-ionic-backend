package com.udemy.udemySpringHibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.udemy.udemySpringHibernate.domain.Categoria;
import com.udemy.udemySpringHibernate.dto.CategoriaDTO;
import com.udemy.udemySpringHibernate.repositories.CategoriaRepository;
import com.udemy.udemySpringHibernate.services.exceptions.DataIntegrityException;
import com.udemy.udemySpringHibernate.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		//pq se já houver id, o metodo save vai considerar como uma atualizacao e nao insert
		//assim, apenas quando o id for null é que insere.
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		//é chamado o metodo find para verificar se já existe este id
		find(obj.getId());
		return repo.save(obj);
	}

	
	public void delete(Integer id) {
		//verificar se o id existe
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluír uma categoria porque possuí produtos");
		}
		
	}
	
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	//page vai encapsular informações sobre a paginação
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, org.springframework.data.domain.Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//a partir do DTO, construir um objeto Categoria
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
