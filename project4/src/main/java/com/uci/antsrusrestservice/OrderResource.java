/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.antsrusrestservice;

import com.uci.antsrusrestservice.model.Order;
import com.uci.antsrusrestservice.model.Product;
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

}