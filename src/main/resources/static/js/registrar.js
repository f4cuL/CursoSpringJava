// Call the dataTables jQuery plugin
$(document).ready(function() {
});

async function registrarUsuarios() {
	let datos = {};
	datos.nombre = document.querySelector("#txtNombre").value;
	datos.apellido = document.querySelector("#txtApellido").value;
	datos.email = document.querySelector("#txtEmail").value;
	datos.telefono= ' ';
	datos.password = document.querySelector("#txtPassword").value;

	let repetirPassword = document.querySelector("#txtRepetirPassword").value;
	if (repetirPassword != datos.password) {
		alert("La contraseña es diferente");
		return;
	}

	const request = await fetch('api/usuarios', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(datos)
	})
	alert("Se ha registrado con éxito");
	location.reload();	
}

