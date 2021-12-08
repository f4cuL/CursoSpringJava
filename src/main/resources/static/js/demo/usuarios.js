// Call the dataTables jQuery plugin
$(document).ready(function() {
	cargarUsuarios()
	document.getElementById("txtEmailUsuario").outerHTML = localStorage.email;
  $('#usuarios').DataTable();
});

async function cargarUsuarios(){
  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders(),
  });
  const usuarios = await request.json();
  let listadoHtml='';
  
  for (let us of usuarios){
  	let telefono = us.telefono;
  	if (telefono == null) { telefono = '-';}
    let botonEliminar='<a href="#" onclick="eliminarUsuario('+us.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
  	let usuarioHTML= '<tr> <td>'+us.id+'</td> <td>'+us.nombre+' '+us.apellido+'</td> <td>'+us.email+'</td> <td>'+telefono+'</td><td>'+botonEliminar+'</td></tr>';
 	listadoHtml += usuarioHTML; 
  }
  document.querySelector('#usuarios tbody').outerHTML = listadoHtml;

}

async function eliminarUsuario(id) {
	if (confirm('¿Desea eliminar este usuario?')) {
		const request = await fetch('api/usuarios/' + id, {
			method: 'DELETE',
			headers: getHeaders(),
		});
	const respuesta = await request.json();
	if (respuesta==true){
	location.reload();
	}
	else{
	}
	}
}

function getHeaders(){
 return {'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': localStorage.token
}
}
