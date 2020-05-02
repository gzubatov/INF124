<?php
	require_once "../connection.php";

	$zip = $_GET["zip"];
    $stmt = $pdo->query('SELECT z.city, z.state, t.combined_rate FROM zip_codes as z, tax_rates as t WHERE z.zip = ' . $zip.' AND z.zip = t.zip');
	$row = $stmt->fetch(PDO::FETCH_ASSOC);
	if($row) {
        echo json_encode($row);
		//print $row['state'];
	}else {

		echo json_encode(['city' => -1, 'state' => -1]);
	}
?>