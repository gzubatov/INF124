<?php
	$html = new DOMDocument(); 
	libxml_use_internal_errors(true);
	$html->loadHTMLFile('./index.html'); 
	$src = $html->getElementById('table');
	$fragment = $html->createDocumentFragment();
	
	// MySQL connection
	$pdo = new PDO('mysql:host=localhost;dbname=test', 'root');
	$stmt = $pdo->query('SELECT * FROM products');

	// Dynamically push products to index.html
	while ( $row = $stmt->fetch(PDO::FETCH_ASSOC) ) {
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
		$product = "<a><div id='".$pid."' class='".$product_categories. "'>".$image."<div><p>".$name."</p><p>$".$price."</p></div></div></a>";
		$fragment->appendXML($product);
		$src->appendChild($fragment);

	}
	
	echo $html->saveHTML();
?>