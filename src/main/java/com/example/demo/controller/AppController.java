package com.example.demo.controller;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.OrdersService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UsersService;

@Controller
public class AppController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpSession session;
	
	// =========== Strings ===========
	
	private String newOrderString = "newOrder";
	private String redirectLogin = "redirect:/login";
	private String redirectmenu = "redirect:/menu";
	private String redirectOrders = "redirect:/orders";
	
	/**
	 * Menu
	 * @param model
	 * @return
	 */
	@GetMapping("/menu")
	public String mainMenu(Model model) {
		String result = "menu";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else {
			if (session.getAttribute(newOrderString) != null) {
				model.addAttribute("orderPending", true);
			}
		}
		
		return result;
	}
	/**
	 * Lista de pedidos
	 * @param model
	 * @return
	 */
	@GetMapping("/orders")
	public String orders(Model model) {
		String result = "orders";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else {
			List<Order> orders = ordersService.getOrdersByUsername((String) session.getAttribute("user"));

			Collections.sort(orders);
			model.addAttribute("orders", orders);
			
			if (session.getAttribute(newOrderString) != null) {
				model.addAttribute("orderPending", true);
			}
		}
	
		return result;
	}
	/**
	 * Edicion de pedidos
	 * @param id ID pedido
	 * @param model 
	 * @return
	 */
	
	@GetMapping("/order/edit/{id}")
	public String orders(@PathVariable String id, Model model) {
		String result = "orderEdit";
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else if (ordersService.existsOrder((String) session.getAttribute("user"), Integer.parseInt(id))) {
			try {
				Integer.parseInt(id);
				
				Order orderEdit = ordersService.getOrderById((String) session.getAttribute("user"), Integer.parseInt(id));
				model.addAttribute("order", orderEdit);
				model.addAttribute("listOfProducts", orderEdit.getListDePedidos());
				
				model.addAttribute("id", id);
			}
			catch (Exception e) {
				result = redirectOrders;
			}
		}
		else {
			result = redirectOrders;
		}
		
		return result;
	}
	/**
	 * Edicion de pedido (envio)
	 * @param orderEdit
	 * @param model
	 * @return
	 */
	@PostMapping("/order/edit/")
	public String edit(@ModelAttribute("order") Order newOrder, Model model) {
		String result = redirectOrders;
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else {

			ordersService.edit((String) session.getAttribute("user"), newOrder, newOrder);
		}
		
		return result;
	}
	/**
	 * Borrado de pedido
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/order/confirmDelete/{id}")
	public String confirmDelete(@PathVariable String id, Model model) {
		String result = "confirmDelete";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else {
			model.addAttribute("id", id);
		}
		
		return result;
	}
	/**
	 * Borrar pedido
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/order/delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		String result = redirectOrders;
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else {
			try {
				int idValue = Integer.parseInt(id);
				if (ordersService.existsOrder((String) session.getAttribute("user"), idValue)) {
					ordersService.remove((String) session.getAttribute("user"), ordersService.getOrderById((String) session.getAttribute("user"), idValue));
				}
				session.removeAttribute(newOrderString);
			}
			catch(Exception e) {
				System.err.println(e);
			}
		}
		
		return result;
	}
	/**
	 * Muestra el catálogo
	 * @param model
	 * @return
	 */
	@GetMapping("/order/catalogue")
	public String catalogue(Model model, @RequestParam(name="q", required=false) String queryProduct ,@RequestParam(name = "productAdd", required = false) Boolean isAddedProduct) {
		String result = "catalogue";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else {
			if (session.getAttribute(newOrderString) == null) {
				User user = usersService.getByUsername((String) session.getAttribute("user"));
				Order newOrder = new Order(user.getAddress(), user.getEmail(), user.getTelefone());
				
				ordersService.addOrder((String) session.getAttribute("user"), newOrder);
				session.setAttribute(newOrderString, newOrder.getId());
			}
			model.addAttribute("products", (queryProduct == null) ? productService.getAllProducts() : productService.searchProduct(queryProduct));
			
			if (isAddedProduct != null) {
				model.addAttribute("isAddedProduct", true);
			}
		}
		
		return result;
	}
	/**
	 * Añade el producto (Envio)
	 * @param amount
	 * @param productRef
	 * @param model
	 * @return
	 */
	@PostMapping("/order/catalogue/add/{productRef}")
	public String addProduct(@RequestParam("amount") Integer amount, @PathVariable Integer productRef) {
		String result = "redirect:/order/catalogue";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}		
		else if (session.getAttribute(newOrderString) == null) {
			result = redirectmenu;
		}
		else {
			if (amount > 0) {
				productService.addProduct((String) session.getAttribute("user"), (Integer) session.getAttribute(newOrderString), productRef, amount);
				result = result.concat("?productAdd=true");
			}
		}
		
		return result;
	}
	/**
	 * Borra un producto de un pedido
	 * @param id
	 * @return
	 */
	@GetMapping("/order/summary/delete/{id}")
	public String deleteProduct(@PathVariable Integer id) {
		String result = "redirect:/order/summary";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else if (session.getAttribute(newOrderString) == null) {
			result = redirectmenu;
		}
		else if (productService.existProduct(id) && ordersService.existsOrder((String) session.getAttribute("user"), (Integer) session.getAttribute(newOrderString)) && productService.existProductInOrder((Integer) session.getAttribute(newOrderString), id)) {
				productService.removeProductInOrder((Integer) session.getAttribute(newOrderString), id);
		}

		return result;
	}
	/**
	 * Muestra el resumen del pedido
	 * @param model
	 * @return
	 */
	@GetMapping("/order/summary")
	public String summary(Model model) {
		String result = "summary";
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else if (session.getAttribute(newOrderString) == null) {
			result = redirectmenu;
		}
		else {
			String username = (String) session.getAttribute("user");
			Order order = ordersService.getOrderById(username, (int) session.getAttribute(newOrderString));
			
			model.addAttribute(newOrderString, order);
			model.addAttribute("totalAmount", new DecimalFormat("#.00").format(ordersService.getTotal(order)));

		}	
		return result;
	}
	/**
	 * Finaliza el pedido (Envio)
	 * @param newOrder
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/order/finish")
	public String finishOrder(/* @Valid */ @ModelAttribute("newOrder") Order newOrder, BindingResult bindingResult, Model model) {
		String result = redirectOrders;
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else if (session.getAttribute(newOrderString) == null) {
			result = redirectmenu;
		}
		else { 
			String username = (String) session.getAttribute("user");
			Order oldOrder = ordersService.getOrderById(username, (int) session.getAttribute(newOrderString));
			
			if (bindingResult.hasErrors()) {
				result = "summary";
				
				model.addAttribute(newOrderString, oldOrder);
				model.addAttribute("totalAmount", new DecimalFormat("#.00").format(ordersService.getTotal(oldOrder)));
			}
			else {
				session.removeAttribute(newOrderString);
				ordersService.edit(username, newOrder, oldOrder);
			}
		}
		
		return result;
	}
	/**
	 * Edita la cantidad (Envio)
	 * @param id
	 * @param ref
	 * @param amount
	 * @return
	 */
	@PostMapping("/order/editamount/{idPedido}/{refProducto}")
	public String editProduct(@PathVariable Integer idPedido, @PathVariable Integer refProducto, @RequestParam("amount") Integer amount) {
		String result = "redirect:/order/edit/" + idPedido;
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else if (amount > 0) {
			productService.editAmount(idPedido, refProducto, amount);
		}
		return result;
	}
	/**
	 * Borra el pedido actual
	 * @return
	 */
	@GetMapping("/order/deleteTempOrder")
	public String deleteTempOrder() {
		String result = redirectmenu;
		
		if (session.getAttribute("user") == null) {
			result = redirectLogin;
		}
		else if (session.getAttribute(newOrderString) == null) {
			result = "redirect:/catalogue";
		}
		else {
			int idValue = (int) session.getAttribute(newOrderString);
			if (ordersService.existsOrder((String) session.getAttribute("user"), idValue)) {
				ordersService.remove((String) session.getAttribute("user"), ordersService.getOrderById((String) session.getAttribute("user"), idValue));	
			}
			session.removeAttribute(newOrderString);
		}
		return result;
	}
}