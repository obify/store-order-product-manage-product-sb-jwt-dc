package com.assignment.api.service;

import com.assignment.api.entity.Productprice;
import com.assignment.api.repository.ProductPrizeRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.api.entity.Order;
import com.assignment.api.entity.Product;
import com.assignment.api.model.ReportModel;
import com.assignment.api.model.ReportModelPerDay;
import com.assignment.api.repository.OrderRepository;
import com.assignment.api.repository.ProductRepository;

@Service
public class ReportingService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private ProductPrizeRepository productPrizeRepo;

	public List<ReportModel> topSellingProduct(){
		List<Order> orders =  orderRepository.findTop5ByCreatedAtOrderByQuantityDesc(new Date());
		return convertObject(orders);
	}

	public List<ReportModelPerDay> sellingPerDayWithDateRange(String fromDate, String todate)
			throws ParseException {
		Date start=new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		Date end=new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		List<Order> orders =  orderRepository.findByCreatedAtBetween(start, end);

		return convertObjectPerDay(orders);
	}

	public List<ReportModel> sellingProductOfMonth(int month){
		List<Order> orders =  orderRepository.findAllByCreatedAt(month);
		return convertObject(orders);
	}

	private List<ReportModel> convertObject(List<Order> orders) {

		List<ReportModel> reportModels = new ArrayList();
		Set<Integer> productIds = new HashSet<>();

		for(Order order : orders) {

			for(int i=0; i<order.getProductIds().split(",").length; i++ ) {

				productIds.add(Integer.parseInt(order.getProductIds().split(",")[i]));
			}
		}

		for(int productId : productIds) {

			ReportModel reportModel = new ReportModel();
			Product product = productRepository.findById(productId).get();
			Productprice productprice = productPrizeRepo.findByProductId(product);

			setReportModel(reportModel,product, productprice);

			reportModels.add(reportModel);

		}

		return reportModels;
	}

	private List<ReportModelPerDay> convertObjectPerDay(List<Order> orders){
		List<ReportModelPerDay> reportModels = new ArrayList();

		for(Order order : orders) {
			ReportModelPerDay oddr = reportModels.stream().filter(o -> order.getCreatedAt().toString().equals(o.getDay())).findAny().orElse(null);
			if(oddr != null) {

				oddr.setTotalSale(String.valueOf(Float.parseFloat(oddr.getTotalSale())+(order.getOrderamount())));

				reportModels.add(oddr);

			}else {
				reportModels.add(new ReportModelPerDay(order.getCreatedAt().toString(), String.valueOf(order.getOrderamount())));
			}


		}

		return reportModels;
	}

	private void setReportModel(ReportModel reportModel, Product product, Productprice productprice) {

		reportModel.setProductname(product.getProductname());
		reportModel.setQuantity(Integer.parseInt(product.getProductPrice().getQuantity()));
		reportModel.setProductPrize(String.valueOf(product.getProductPrice().getPrice()));
		reportModel.setInStock(productprice.isInStock());

	}


}
