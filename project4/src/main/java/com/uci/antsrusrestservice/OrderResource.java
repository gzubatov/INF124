/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.antsrusrestservice;

import com.uci.antsrusrestservice.model.Order;
import com.uci.antsrusrestservice.service.ProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

/**
 * This class contains the methods that will respond to the various endpoints
 * that you define for your RESTful API Service.
 *
 */
// todos will be the pathsegment that precedes any path segment specified by
// @Path on a method.
@Path("/orders")
public class OrderResource {
	// This method represents an endpoint with the URL /todos/{id} and a GET request
	// ( Note that {id} is a placeholder for a path parameter)
	@Path("{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOrderResourceById(@PathParam("id") int id/* The {id} placeholder parameter is resolved */) {
		// invokes the DB method which will fetch a todo_list item object by id
		Order order = ProductService.getOrderByOid(id);

		// Respond with a 404 if there is no such todo_list item for the id provided
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Respond with a 200 OK if you have a todo_list_item object to return as
		// response
		return Response.ok(order).build();
	}

	// This method represents an endpoint with the URL /todos and a POST request.
	// Since there is no @PathParam mentioned, the /todos as a relative path and a
	// POST request will invoke this method.
	@POST
	@Consumes({ MediaType.APPLICATION_JSON }) // This method accepts a request of the JSON type
	public Response addOrder(Order order) {

		// The todo object here is automatically constructed from the json request.
		// Jersey is so cool!
		int oid = ProductService.AddOrder(order);
		if (oid != -1) {

			return Response.ok().entity(Integer.toString(oid)).build();
		}

		// Return an Internal Server error because something wrong happened. This should
		// never be executed
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

	}

	// Similar to the method above.
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED }) // This method accepts form parameters.
	// If you were to send a POST through a form submit, this method would be
	// called.
	public Response addOrder(@FormParam("fname") String fname, @FormParam("lname") String lname,
			@FormParam("phonenum") String phonenum, @FormParam("addr") String addr, @FormParam("zipcode") int zipcode,
			@FormParam("shipping") String shipping, @FormParam("ccn") long ccn, @FormParam("expmo") int expmo,
			@FormParam("expyr") int expyr, @FormParam("security") int security, @FormParam("total") Double total,
			@FormParam("pids") String pids, @FormParam("city") String city, @FormParam("state") String state) {
		// "pid:quantity" ---> "1:1,2:1,3:2"
		Order order = new Order();
		order.setFirstName(fname);
		order.setLastName(lname);
		order.setPhoneNumber(phonenum);
		order.setShippingAddress(addr);
		order.setZipCode(zipcode);
		order.setShippingMethod(shipping);
		order.setCreditCard(ccn);
		order.setExpMonth(expmo);
		order.setExpYear(expyr);
		order.setSecurityCode(security);
		order.setPriceTotal(total);
		order.setPids(pids);
		order.setCity(city);
		order.setState(state);

		int oid = ProductService.AddOrder(order);
		if (oid != -1) {
			return Response.ok().entity(Integer.toString(oid)).build();
		}

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}