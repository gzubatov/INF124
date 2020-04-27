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
		$name = $row['name'];
		$price = $row['price'];
		$image = "<img src='".$row["image"]."'/>";
		$product = "<a><div class='product'>".$image."<div><p>".$name."</p><p>$".$price."</p></div></div></a>";
		$fragment->appendXML($product);
		$src->appendChild($fragment);
	}
	echo $html->saveHTML();
?>