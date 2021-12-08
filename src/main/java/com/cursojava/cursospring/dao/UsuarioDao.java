package com.cursojava.cursospring.dao;

import java.util.List;

import com.cursojava.cursospring.models.Usuario;

public interface UsuarioDao {
	public List<Usuario> getUsuarios();

	public void eliminar(Long id);

	public void registrarUsuario(Usuario usuario);

	public Usuario obtenerUsuarioId(Long id);

	public Usuario obtenerUsuarioPorVerificacion(Usuario usuario);
}
