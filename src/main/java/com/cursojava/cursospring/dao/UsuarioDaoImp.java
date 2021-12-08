package com.cursojava.cursospring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursojava.cursospring.models.Usuario;
import com.fasterxml.jackson.core.sym.Name;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public List<Usuario> getUsuarios() {
		String query = "FROM Usuario";
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public void eliminar(Long id) {
		Usuario usuario = entityManager.find(Usuario.class, id);
		entityManager.remove(usuario);	 
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		entityManager.merge(usuario);
		
	}

	@Override
	public Usuario obtenerUsuarioId(Long id) {
		return entityManager.find(Usuario.class, id);
		
	}

	@Override
	public Usuario obtenerUsuarioPorVerificacion(Usuario usuario) {
		String query = "FROM Usuario WHERE email= :email";
		List<Usuario> lista=  entityManager.createQuery(query)
							.setParameter("email", usuario.getEmail())
							.getResultList();
		
		if (lista.isEmpty()){
			return null;
		}
		String passwordHashed = lista.get(0).getPassword();
		
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
		
		if (argon2.verify(passwordHashed,usuario.getPassword())) {
			return lista.get(0);
		}else{
			return null;
		}
}
}
