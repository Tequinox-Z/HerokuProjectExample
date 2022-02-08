
/*
    ================================= Interactividad web de catálogo =================================
*/

    // Obtenemos el texto donde se mostrará el número de productos a comprar
        
        let text = document.querySelector('#products-selected');

    // Obtenemos el botón de comprar 

        let buyBottom = document.querySelector('#buy-buttom');

		buyBottom.addEventListener('click', (event) => {
			event.preventDefault();
			let inputs = document.querySelectorAll('input[type=number]');               // Seleccionamos todos los inputs de tipo number

			for (let input of inputs) {
				input.value = 0;
			}
			buyBottom.classList.add('invisibility');                                               // Ocultamos el botón de comprar

		})
		

    // Añadimos un evento al contenedor para que escuche los cambios de los inputs

        document.querySelector('#products').addEventListener('mouseover', (event) => {
            if (event.target.matches('.product')) {                               					// Comprobamos que sea un input de tipo number
                let amount = event.target.querySelector('input[type=number]').value;                // Seleccionamos todos los inputs de tipo number
				
				let result;

                if (amount == 0) {                                                				// Si no tenemos productos...
                    result = 'Seleccione cantidad a comprar';                                  // Indicamos que seleccione algún producto
                }
                else {
                    result = (amount == 1)? '1 producto seleccionado': `${amount} productos seleccionados`;         // Si ha seleccionado algun producto, indicamos la cantidad y adaptamos el mensaje a la cantidad seleccionada
                    buyBottom.classList.remove('invisibility');                                               // Mostramos el botón de comprar
                }
                text.innerHTML = result;                                                    // Mostramos el resultado en el párrafo HTML
            }
        });