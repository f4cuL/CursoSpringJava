package com.cursojava.cursospring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.cursospring.dao.UsuarioDao;
import com.cursojava.cursospring.models.Usuario;
import com.cursojava.cursospring.utils.JWTUtil;

@RestController
public class AuthController {
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value="api/login", method = RequestMethod.POST)
	public String iniciarSesion(@RequestBody Usuario usuario) {
		Usuario usuarioLoggeado = usuarioDao.obtenerUsuarioPorVerificacion(usuario);
		if (usuarioLoggeado!=null) {
			String tokenJwt =jwtUtil.create(String.valueOf(usuarioLoggeado.getId()), usuarioLoggeado.getEmail());
			return tokenJwt;
		}
		return "fail";
	}
}
