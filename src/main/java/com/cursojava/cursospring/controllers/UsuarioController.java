package com.cursojava.cursospring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.cursospring.dao.UsuarioDao;
import com.cursojava.cursospring.models.Usuario;
import com.cursojava.cursospring.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UsuarioController{ 
	
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value="api/usuarios/{id}", method = RequestMethod.GET)
	public Usuario getUsuario(@PathVariable Long id) {
		return usuarioDao.obtenerUsuarioId(id);
	}
	@RequestMapping(value="api/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios(@RequestHeader(value="Authorization")String token) {
		if (validarToken(token)) {
			return usuarioDao.getUsuarios();
		}
		return new ArrayList<>();
	}
	
	@RequestMapping(value="api/usuarios", method = RequestMethod.POST)
	public void registrarusuario(@RequestBody Usuario usuario) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		usuario.setPassword(argon2.hash(1,1024,1,usuario.getPassword()));
		usuarioDao.registrarUsuario(usuario);	
	}
	
	
	@RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE)
	public boolean eliminarUsuario(@RequestHeader(value="Authorization")String token, @PathVariable Long id) {
		if (validarToken(token)) {
		usuarioDao.eliminar(id);
		}
		return true;
	}
	private boolean validarToken(String token) {
		String idUsuario = jwtUtil.getKey(token);
			return !(idUsuario == null);
	}
}
