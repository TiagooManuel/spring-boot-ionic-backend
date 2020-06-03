package com.udemy.udemySpringHibernate.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.udemySpringHibernate.domain.Categoria;
import com.udemy.udemySpringHibernate.repositories.CategoriaRepository;
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

}
