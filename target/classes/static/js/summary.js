
// ======================================== Animación del envío ========================================


	let methods = document.querySelectorAll(".method");					// Métodos de envío
	let text = document.querySelector("#textSummary");					// Campo de texto o título
	let form = document.querySelector("#dataForm");						// Formulario
	let sendMethodDiv = document.querySelector("#sendMethodDiv");		// Contenedor de métodos

	let MESSAGE = "Si‏‏‎‏‏‎‎ lo‏‏‎‏‏‎‎ necesita,‏‏‎‏‏‎‎ modifique‏‏‎‏‏‎‎ los‏‏‎‏‏‎‎ siguientes‏‏‎‏‏‎‎ datos";		// Mensaje final


// Iteramos cada uno de los métodos y añadimos sus eventos

	for (let method of methods) {

		method.addEventListener("click", (event) => {
			
		// Almacenamos el método pulsado en una variable termporal
			
			let elementSelected = event.target;									
			
		// Añadimos la clase seleccionado
		
			elementSelected.classList.add("animatedSelected");
			
		// Añadimos la clase no seleccionado a los demás
			
			for (let currentMethod of methods) {
				if (currentMethod != elementSelected) {
					currentMethod.classList.add("animatedNoSelected")		// Si el elemento actual es distinto del pulsado añadimos su correspondiente clase
				}
			}
			
		// Un segundo después, quitamos de la vista los métodos para mostrar la siguiente parte del formulario
		
			setTimeout(() => {
				sendMethodDiv.classList.add("hidden");
				form.classList.remove("hidden");
			}, 1000)
		
		// Esta variable almacenará el intervalo del segundo efecto (Delay: 50 ms)
		
			let textEfectAdd;
			
		// Esta variable almacenará el intervalo del primer efecto (Delay: 50 ms)
			
			let textEfect = setInterval(() => {
				
				// Si aún tenemos letras en el campo...
				
				if (text.innerText.length != 0) {
					text.innerText = text.innerText.substring(0, text.innerText.length - 1);		// Quitamos la última letra
				}
				else {
					
				// Si ya no tenemos letras, activamos el segundo efecto
					
					textEfectAdd = setInterval(() => {
						
					// Si tenemos letras...
						
						if (MESSAGE.length != 0) {
							
						// Si no tenemos un espacio...
							
							if (MESSAGE.charAt(0) != " ") {
								
							// Añadimos la primera letra
								
								text.innerText = text.innerText + MESSAGE.charAt(0);
								
							// Borramos esa misma letra del mensaje
								
								MESSAGE = MESSAGE.substring(1, MESSAGE.length);
							}
							else {
								
							// Si no, un espacio no lo podemos añadir puesto que HTML hará un trim por lo que añadimos dos carácteres
															
								text.innerText = text.innerText + MESSAGE.substring(0, 2);
								
							// Borramos ambas letras
								
								MESSAGE = MESSAGE.substring(2, MESSAGE.length);
							}
						}
						else {
							
						// Si no tenemos letras borramos el segundo efecto
							
							clearInterval(textEfectAdd);
						}
					}, 50);
					
				// Eliminamos el primer efecto
		
					clearInterval(textEfect);
				}
			}, 50);
		})
		
	}