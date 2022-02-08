package com.example.demo.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Pedido")
public class Order implements Comparable<Order> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;								// Id del pedido
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;							// Fecha del pedido
	private int progress;						// Progreso
	private String sendMethod;					// Tipo envío
	private String address;						// Dirección
	private String email;						// Email	
	private String telefone;					// Telefono
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<OrderProduct> ListDePedidos;
	
	/**
	 * Constructor por defecto de pedido
	 * @param address	Dirección
	 * @param email	Correo
	 * @param telefone Teléfono
	 */
	public Order(String address, String email, String telefone) {
		super();
		this.progress = new Random().nextInt(10);
		this.address = address;
		this.email = email;
		this.telefone = telefone;
		this.date = new Date();
	}
	/**
	 * Constructor para búsquedas y para funciones
	 */
	public Order() {
		super();
		this.progress = new Random().nextInt(10);
		this.date = new Date();
	}
	/**
	 * Constructor para buscar un pedido
	 * @param id ID del pedido
	 */
	public Order(int id) {
		super();
		this.id = id;
		this.date = new Date();
	}
	/**
	 * Obtiene la fecha
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Establece la fecha
	 * @param Date fecha
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * Compara este pedido con otro
	 */
	public int compareTo(Order order) {
		return order.getDate().compareTo(this.date);
	}
	/**
	 * Obtiene el id del pedido
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", progress=" + progress + ", sendMethod=" + sendMethod
				+ ", address=" + address + ", email=" + email + ", telefone=" + telefone + "]";
	}
	/** Obtiene la dirección
	 * @return String dirección
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Obtiene la fecha en string
	 * @return
	 */
	public String getDateString() {
		
		// Este StringBuilder almacenará temporalmente la información	
		
			StringBuilder result = new StringBuilder("");				

		// Este array nos permitirá obtener el mes desde el número, lo mejor para esto sería tener una clase donde se almacenen todos los nombres lo que nos permite cambiar el idioma fácilmente, usar este asignamiento desde otros puntos de la aplicación entre otras cosas
		
			String []meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		
		// Generamos un nuevo calendar y le asignamos la fecha del pedido
		
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);

		// Añadimos los datos
			
			result.append(calendar.get(Calendar.DAY_OF_MONTH) + " de " + meses[Calendar.MONTH] + " de " + calendar.get(Calendar.YEAR) + " a las " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +  ((calendar.get(Calendar.MINUTE) > 9) ? calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE)));
		
		// Retornamos el resultado
			
			return result.toString();
	}
	/**
	 * Establece la fecha
	 * @param address Dirección
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Obtiene el correo
	 * @return String email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Establece el correo
	 * @param email Correo
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Obtiene el teléfono
	 * @return String Teléfono
	 */
	public String getTelefone() {
		return telefone;
	}
	/**
	 * Establece el teléfono
	 * @param telefone Teléfono
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	/**
	 * Obtiene el progreso del envío
	 * @return Progreso
	 */
	public int getProgress() {
		return progress;
	}
	/**
	 * Establece el progreso del pedido
	 * @param progress Progreso
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}
	/**
	 * Establece el ID del pedido
	 * @param id ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Obtiene el método de envío
	 * @return String Método de envio
	 */
	public String getSendMethod() {
		return sendMethod;
	}
	/**
	 * Establece el método de envío
	 * @param sendMethod de envío
	 */
	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}
	public List<OrderProduct> getListDePedidos() {
		return ListDePedidos;
	}
	public void setListDePedidos(List<OrderProduct> listDePedidos) {
		ListDePedidos = listDePedidos;
	}
}
