package com.assignment.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.assignment.api.constants.OrderConstants;
import com.assignment.api.entity.Cart;
import com.assignment.api.entity.Order;
import com.assignment.api.entity.Product;
import com.assignment.api.entity.User;
import com.assignment.api.exception.OrderPlaceFailed;
import com.assignment.api.model.OrderDTO;
import com.assignment.api.model.ProductListDTO;
import com.assignment.api.repository.CartRepository;
import com.assignment.api.repository.OrderRepository;
import com.assignment.api.repository.ProductRepository;
import com.assignment.api.repository.UserRepository;
import com.assignment.api.utility.CommonUtil;
import com.assignment.api.utility.SendEmail;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductService productService;

	@Autowired
	SendEmail mailService;

	@Value("${mail.body}")
	String mailBody;

	@Value("${mail.subject}")
	String mailSubject;

	public List<OrderDTO> listAllNewOrders() {
		List<String> statuses = new ArrayList<>();
		statuses.add(OrderConstants.ORDER_INITIAL);
		return changeOrderModelToOrderDTO(orderRepository.findByOrderStatusIn(statuses));
	}

	public List<OrderDTO> searchOrderByOrderId(String orderId) {
		List<String> statuses = new ArrayList<>();
		statuses.add(OrderConstants.ORDER_INITIAL);
		return changeOrderModelToOrderDTODetails(orderRepository.findByOrderId(orderId));
	}

	public void placeOrder(int[] cartIds) throws OrderPlaceFailed {

		Order order = new Order();
		List<String> productIds = new ArrayList();
		List<String> quantity = new ArrayList();
		User user = null;

		float orderAmount = 0;

		for (int cartId : cartIds) {
			Cart cart = cartRepository.findById(cartId).get();

			orderAmount += cart.getProductId().getProductPrice().getPrice() * cart.getCount();
			productIds.add(String.valueOf(cart.getProductId().getId()));
			quantity.add(String.valueOf(cart.getCount()));

			productService.updateQuantity(cart.getProductId().getId(), cart.getCount());
			cartRepository.delete(cart);

		}

		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			user = userRepository.findByUserName(userName);
		}

		setOrderData(order, user,productIds,quantity,orderAmount);
		order = orderRepository.save(order);

		String orderId = CommonUtil.getOrderIdBusiness(order.getId());
		order.setOrderId(orderId);
		order.setOrderStatus(OrderConstants.ORDER_INITIAL);
		orderRepository.save(order);

		mailService.sendSimpleMail(user.getEmail(), mailBody + orderId, mailSubject);

	}

	public List<OrderDTO> changeOrderModelToOrderDTO(List<Order> orders) {
		List<OrderDTO> orderLists = new ArrayList<>();
		for (Order order : orders) {
			OrderDTO orderDTO = new OrderDTO();
			setOrderDto(order, orderDTO);

			OrderDTO sDto = orderLists.stream().filter(dto -> order.getOrderId().equalsIgnoreCase(dto.getOrderId()))
					.findAny().orElse(null);
			if (sDto == null) {
				orderLists.add(orderDTO);
			}

		}

		return orderLists;
	}

	public List<OrderDTO> changeOrderModelToOrderDTODetails(List<Order> orders) {
		List<OrderDTO> orderLists = new ArrayList<>();
		for (Order order : orders) {
			OrderDTO orderDTO = new OrderDTO();
			List<ProductListDTO> productLists = new ArrayList<>();
			setOrderDto(order, orderDTO);

			int productIds = order.getProductIds().split(",").length;
			for(int i = 0; i<productIds; i++) {
				ProductListDTO productList = new ProductListDTO();
				Product product = productRepository.findById(Integer.parseInt(order.getProductIds().split(",")[i])).get();

				productList.setProductname(product.getProductname());
				productList.setQuantity(order.getQuantity().split(",")[i]);
				productList.setPrize(product.getProductPrice().getPrice()*Float.parseFloat(order.getQuantity().split(",")[i]));

				productLists.add(productList);

			}
			orderDTO.setProductList(productLists);


			OrderDTO sdto = orderLists.stream().filter(dto -> order.getOrderId().equalsIgnoreCase(dto.getOrderId()))
					.findAny().orElse(null);
			if (sdto == null) {
				orderLists.add(orderDTO);
			}

		}

		return orderLists;
	}

	private void setOrderData(Order order, User user, List<String> productIds,
			List<String> quantity, float orderAmount) {

		order.setUserId(user);
		order.setProductIds(String.join(",", productIds));
		order.setQuantity(String.join(",", quantity));
		order.setCount(productIds.size());
		order.setOrderamount(orderAmount);

	}

	private void setOrderDto(Order order, OrderDTO orderDTO) {

		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setOrderDate(order.getCreatedAt());
		orderDTO.setCustomerName(order.getUserId().getFullname());
		orderDTO.setCustomerMobileNo(order.getUserId().getPhoneno());
		orderDTO.setOrderamount(order.getOrderamount());
		orderDTO.setCustomerEmail(order.getUserId().getEmail());

		orderDTO.setOrderStatus(order.getOrderStatus());

	}

}
