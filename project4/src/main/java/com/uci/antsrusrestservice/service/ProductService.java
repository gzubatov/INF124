package com.uci.antsrusrestservice.service;

import com.uci.antsrusrestservice.db.DatabaseConnector;
import com.uci.antsrusrestservice.db.DatabaseUtils;
import com.uci.antsrusrestservice.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

	private final static String ALL_PRODUCTS_QUERY = "SELECT * FROM products";

	public static Product getProductByPid(int pid) {
		// Get a new connection object before going forward with the JDBC invocation.
		Connection connection = DatabaseConnector.getConnection();
		ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection,
				ALL_PRODUCTS_QUERY + " WHERE pid = " + pid);

		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					Product product = new Product();

					product.setPid(resultSet.getInt("pid"));
					product.setDescription(resultSet.getString("description"));
					product.setDetails(resultSet.getString("details"));
					product.setImage(resultSet.getString("image"));
					product.setPrice(resultSet.getDouble("price"));
					product.setName(resultSet.getString("name"));

					return product;

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {

					// We will always close the connection once we are done interacting with the
					// Database.
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;

	}

	public static List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();

		Connection connection = DatabaseConnector.getConnection();
		ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_PRODUCTS_QUERY);

		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					Product p = new Product();
					p.setPid(resultSet.getInt("pid"));
					p.setDescription(resultSet.getString("description"));
					p.setDetails(resultSet.getString("details"));
					p.setImage(resultSet.getString("image"));
					p.setPrice(resultSet.getDouble("price"));
					p.setName(resultSet.getString("name"));

					products.add(p);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return products;
	}

	public static boolean AddProduct(Product p) {

		String sql = "INSERT INTO TODOS  (TODO_SUMMARY, TODO_DESC)" + "VALUES (?, ?)";
		Connection connection = DatabaseConnector.getConnection();
		return DatabaseUtils.performDBUpdate(connection, sql, p.getDescription(), p.getDescription());

	}

	public static boolean updateProduct(Product p) {

		String sql = "UPDATE TODOS SET TODO_SUMMARY=?, TODO_DESC=? WHERE TODO_ID=?;";

		Connection connection = DatabaseConnector.getConnection();

		// FIX BELOW **
		boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, p.getDescription(), p.getDescription(),
				String.valueOf(p.getPid()));

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updateStatus;

	}

	public static boolean deleteProduct(Product p) {

		String sql = "DELETE FROM TODOS WHERE TODO_ID=?;";

		Connection connection = DatabaseConnector.getConnection();

		boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, String.valueOf(p.getPid()));

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updateStatus;
	}
}
