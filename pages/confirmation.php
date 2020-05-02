<?php
	require_once "../connection.php";

	$oid = $_GET['oid'];
	$stmt = $pdo->query('SELECT * FROM orders WHERE oid = ' . $oid);
	$row = $stmt->fetch(PDO::FETCH_ASSOC);

	// Get zip code from order form by oid
	$zip_code = $row['zip_code'];
	$stmp_shipping = $pdo->query('SELECT city, state FROM zip_codes as z WHERE z.zip = ' . $zip_code);
	$shipping_row = $stmp_shipping->fetch(PDO::FETCH_ASSOC);
	$city = $shipping_row['city'];
	$state = $shipping_row['state'];
?>

<!doctype html>
<html lang="en">

<!--
WRITTEN BY: Greg Zubatov, Swan Toma, Genesis Garcia 
EMAIL: gzubatov@uci.edu, sktoma@uci.edu, genesirg@uci.edu
-->

<head>
  <meta charset="utf-8">
  <meta name="author" content="co-authored by Greg Zubatov, .., .., .., Genesis Garcia">
  <meta name="description" content="ECommerce Website">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <link rel="stylesheet" href="../css/global.css">
  <link rel="stylesheet" href="../css/confirmation.css">

  <title>Ant-R-Us</title>

</head>

<body>
  <!-- Navbar -->
    <div class="navbar">
      <div class="logo"><img src="../imgs/ant_logo.png" alt="Ants R Us Logo"></div>
      <div class="pages">
        <ul class="navigation">
          <li><a href="../index.php">Home</a></li>
          <li><a href="about.html">About</a></li>
        </ul>
      </div>
    </div>

    <!--Confirmation Info-->
    <div class = "conf_info">
        <div class="thanks">
			<h1>Thank you, <?php echo $row['first_name']?> <?php echo $row['last_name']?>!</h1>
		</div>
        <div class="details">
            <div>
                <p><span>Order ID:</span> <?php echo $row['oid']?></p>
                <p><span>Product ID:</span> <?php echo $row['pid']?></p>
                <p><span>Quantity:</span> <?php echo $row['quantity']?></p>
                <p><span>Total:</span> $<?php echo $row['price_total']?></p>
                <p><span>Contact Number:</span> <?php echo $row['phone_number']?></hp>
			</div>
			
            <div>
                <p><span>Shipping Address:</span></p>
                <p><?php echo $row['shipping_address']?></p>
                <p><?php echo "$city, $state $zip_code"?></p>
                <p><span>Shipping Method:</span> <?php echo $row['shipping_method']?></p>
                <p><span>Payment Method: </span><?php echo '**** **** **** '.substr($row['credit_card'], -4)?></p>
            </div>
        </div>
    </div>

    

</body>
</html>