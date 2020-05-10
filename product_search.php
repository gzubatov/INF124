<?php
/*
	This file loads all of the products in the DB into the table on the home page.
	It also provides search/filter functionality by checking if the ajax request
	sent to this page has query parameters.
*/
	// MySQL connection
	require_once "connection.php";

	// If the GET request had a query paramater of search, and the search is not blank
	// then search for all products that contain the search text within their name
	if (isset($_GET['search']) && $_GET['search'] != '') {
		$query = "SELECT * FROM products WHERE name LIKE '%".$_GET['search']."%'";
		$stmt_search = $pdo->query($query);
		display_query( $stmt_search );
		$stmt_search = null;
	}
	// else return all products
	else {
		$stmt = $pdo->query('SELECT * FROM products');
		display_query( $stmt );
		$stmt = null;
	}
	
	// This function takes the results of a query and iterates through the rows
	// to format an HTML response
	function display_query( $query_result ){
		global $pdo;
		$product = '';
		while ( $row = $query_result->fetch(PDO::FETCH_ASSOC) ) {
			// Fetch categories
			$stmt2 = $pdo->query('SELECT c.category 
			FROM products as p, categories as c, product_has_category as pc 
			WHERE p.pid = pc.pid AND c.cid = pc.cid AND p.pid = ' . $row['pid']);
			
			$product_categories = '';
			while( $row2 = $stmt2->fetch(PDO::FETCH_ASSOC))
			{
				$product_categories .= $row2['category'] . ' ';
			}

			$pid = $row['pid'];
			$name = $row['name'];
			$price = $row['price'];
			$image = "<img src='".$row["image"]."'/>";
			$product .= "<a><div id='".$pid."' class='".$product_categories. "'>".$image."<div><p>".$name."</p><p>$".$price."</p></div></div></a>";
		}
		echo $product;
	}

	$pdo = null;
?>