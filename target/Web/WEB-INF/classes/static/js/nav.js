//
// ============================================ Funcionalidad de menú en nav ================================================== 
//

	let menu = document.querySelector("#optionsNav");			// Menú
	let user = document.querySelector("#user");					// Imagen del usuario
	let document_element = document.querySelector("html");		// Documento

// Este evento ocultará el menú automáticamente si se pulsa fuera de él

	document_element.addEventListener("click", (event) => {
		if (event.target != user && !menu.classList.contains('hidden')) {			// Si se pulsa en cualquier elemento que no sea la imagen del usuario y el menu no está oculto
			menu.classList.toggle('hidden');										// Cambiamos la visualización del menú
		}
	})

// Este evento mostrará el menú al pulsar click en la imagen del usuario

	user.addEventListener("click", () => {
		menu.classList.toggle('hidden');											// Cambiamos la visualización del menú
	})

