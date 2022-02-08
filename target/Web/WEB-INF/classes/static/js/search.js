
// ===================================================== Search change =====================================================
// Envía la búsqueda automáticamente al cambiar la misma
 
	let searchInput = document.querySelector("#searchInput");		// Campo de búsqueda
	let form = document.querySelector("#search");					// Formulario

	searchInput.addEventListener("change", () => {					
		form.submit();												// Enviamos el evento al cambiar el campo de entrada
	})