<?php
	require_once "../connection.php";

	// Close and open PHP tage to modify CSS for error
	?>
	<style type="text/css">
	#errorBlock {
		display: none;
	}
	</style>
	<?php

	// Validate form again on server side
	function validateForm(){
		if( !isset($_POST['prod_id']) || !is_numeric( $_POST['prod_id']) ) { return False; }
		if( !isset($_POST['quantity']) || !is_numeric($_POST['quantity']) && $_POST['quantity'] <= 0 ) { return False; }
		if( !isset($_POST['fname']) || !isset($_POST['lname']) ){ return False; }
		if( !isset($_POST['phonenum']) || preg_match("/^[0-9]{3}-[0-9]{4}-[0-9]{4}$/", $_POST['phonenum'])) { return False; }
		if( !isset($_POST['addr'])) { return False; }
    	if( !isset($_POST['zipcode']) || !is_numeric($_POST['zipcode']) &&  strlen( strval($_POST['zipcode'])) != 5 ) { return False; }
		if( !isset($_POST['shipping']) ) { return False; }
		if( !isset($_POST['ccn']) || !is_numeric($_POST['ccn']) || strlen(strval($_POST['ccn'])) != 16) { return False; }
		if( !isset($_POST['expmo']) || !is_numeric($_POST['expmo']) ) { return False; }
		if( !isset($_POST['expyr']) || !is_numeric($_POST['expyr'])) { return False; }
		if( !isset($_POST['security']) || strlen(strval($_POST['security'])) != 3 ) { return False; }
		if( !isset($_POST['total'])) { return False; } 
		return True;
		}

	$stmt = $pdo->query('SELECT * FROM products WHERE pid = ' . $_GET['pid']);
	$row = $stmt->fetch(PDO::FETCH_ASSOC);
	$status = validateForm();
  	// if it's a post request, submit order form
  	if ($_SERVER['REQUEST_METHOD'] === 'POST' && $status) {
		$insert_stmt = $pdo->prepare("INSERT INTO orders (pid, quantity, first_name, last_name, phone_number, shipping_address, zip_code, shipping_method, credit_card, expiration_month, expiration_year, security_code, price_total)
								VALUES (:pid, :quantity, :first_name, :last_name, :phone_number, :shipping_address, :zip_code, :shipping_method, :credit_card, :expiration_month, :expiration_year, :security_code, :price_total)");
			
			$insert_stmt->execute( array(
			":pid" => $_POST['prod_id'], 
			":quantity" => $_POST['quantity'], 
			":first_name" => $_POST['fname'],
			":last_name" => $_POST['lname'], 
			":phone_number" => $_POST['phonenum'], 
			":shipping_address" => $_POST['addr'], 
			":zip_code" => $_POST['zipcode'], 
			":shipping_method" => $_POST['shipping'],
			":credit_card" => $_POST['ccn'],
			":expiration_month" => $_POST['expmo'], 
			":expiration_year" => $_POST['expyr'], 
			":security_code" => $_POST['security'], 
			":price_total" => number_format(substr($_POST['total'], 1), 2)
			));

			$prev_oid = $pdo->lastInsertId();
			header("Location: ./confirmation.php?oid=$prev_oid");
	  }	
	  else if( $_SERVER['REQUEST_METHOD'] === 'POST' && $status == FALSE) {
		?>
		<style type="text/css">
		#errorBlock {
			display: block;
		}
		</style>
		<?php
	  }

	$pdo = null;
	$stmt = null;
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
  <link rel="stylesheet" href="../css/products.css">
  <script src="./../js/products.js"></script>
  

  
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

    
    
  <div id="main">
    <div id = "info">
      <img id="product_image" src="<?php echo '.'.$row['image'];?>" alt="Product image">
      <div>
      <h3 id="name">
        <?php echo $row['name'];?> - $<?php echo $row['price'];?> 
      </h3>
      <h5 id="pid"><?php echo 'PID: '.$row['pid'];?></h5>
      <p id="description"><?php echo $row['description'];?></p>
      <p id="details">
        Details:
          <ul>
            <?php 
              $details = explode(',', $row['details']);
              foreach ($details as $detail) {
                echo "<li> $detail</li>";
              }
            ?>
          <ul>
        </p>  
    </div>
    
    </div>

    <div id = "form">

	<div id="errorBlock">
			<h1 style="color: red">Error processing your request</h1>
	</div>

      <h3>Order Form:</h3>
		<form method="post">
      <label for = "prodid" >Product Identifier</label>
     
	  	<input type="text" id="prod_id" name="prod_id" placeholder="ID #">
	  <label for="Quantity">Quantity</label>
    <input type="text" id="quantity" name="quantity" value="1">
    
    <div class="name">
      <div>
        <label for="fname">First name</label>
        <input type="text" id="fname" name="fname">
      </div>
    <div>
    <label for="lname">Last name</label>
    <input type="text" id="lname" name="lname">
  </div>
  </div>
  
  <div class="phone">
  <label for="phonenum">Phone Number</label>
  <input placeholder="123-456-7890" type="tel" id="phonenum" name="phonenum" pattern="[0-9]{3}[-][0-9]{3}[-][0-9]{4}" title = "Format: 123-456-7891">

</div>
  
  <label for="addr">Shipping Address</label>
  <input type="text" id="addr" name="addr">

  <div class="addr">
    <div>
      <label for="city">City</label>
      <input type = "text" id = "city" name = "city"/>
    </div>
    <div>
      <label for="state">State</label>
      <select id="state" name="state">
        <option value="AL">AL</option>
        <option value="AK">AK</option>
        <option value="AZ">AZ</option>
        <option value="AR">AR</option>
        <option value="CA">CA</option>
        <option value="CO">CO</option>
        <option value="CT">CT</option>
        <option value="DE">DE</option>
        <option value="DC">DC</option>
        <option value="FL">FL</option>
        <option value="GA">GA</option>
        <option value="HI">HI</option>
        <option value="ID">ID</option>
        <option value="IL">IL</option>
        <option value="IN">IN</option>
        <option value="IA">IA</option>
        <option value="KS">KS</option>
        <option value="KY">KY</option>
        <option value="LA">LA</option>
        <option value="ME">ME</option>
        <option value="MD">MD</option>
        <option value="MA">MA</option>
        <option value="MI">MI</option>
        <option value="MN">MN</option>
        <option value="MS">MS</option>
        <option value="MO">MO</option>
        <option value="MT">MT</option>
        <option value="NE">NE</option>
        <option value="NV">NV</option>
        <option value="NH">NH</option>
        <option value="NJ">NJ</option>
        <option value="NM">NM</option>
        <option value="NY">NY</option>
        <option value="NC">NC</option>
        <option value="ND">ND</option>
        <option value="OH">OH</option>
        <option value="OK">OK</option>
        <option value="OR">OR</option>
        <option value="PA">PA</option>
        <option value="RI">RI</option>
        <option value="SC">SC</option>
        <option value="SD">SD</option>
        <option value="TN">TN</option>
        <option value="TX">TX</option>
        <option value="UT">UT</option>
        <option value="VT">VT</option>
        <option value="VA">VA</option>
        <option value="WA">WA</option>
        <option value="WV">WV</option>
        <option value="WI">WI</option>
        <option value="WY">WY</option>
      </select>
    </div>
    <div>
      <label for="zipcode">Zip Code</label>
      <input type = "text" id = "zipcode" name = "zipcode" />
    </div>
  </div>

  <label for="shipping">Shipping Method</label>
  
  <select id="shipping" name="shipping">
    <option value="overnight">Overnight</option>
    <option value="expedited">2-days Expedited</option>
    <option value="ground">6-days Ground</option>
  </select>

  
	<label for="ccn">Credit Card Number</label>
	<input type="text" id="ccn" name="ccn">
  
  <div class="expDate">
  <label for="expdate">Expiration Date</label>
    
    <select id="expmo" name="expmo">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
      <option value="5">5</option>
      <option value="6">6</option>
      <option value="7">7</option>
      <option value="8">8</option>
      <option value="9">9</option>
      <option value="10">10</option>
      <option value="11">11</option>
      <option value="12">12</option>
    </select>

    <select id="expyr" name="expyr">
      <option value="2020">2020</option>
      <option value="2021">2021</option>
      <option value="2022">2022</option>
      <option value="2023">2023</option>
      <option value="2024">2024</option>
      <option value="2025">2025</option>
      <option value="2026">2026</option>
      <option value="2027">2027</option>
      <option value="2028">2028</option>
      <option value="2029">2029</option>
      <option value="2030">2030</option>
      <option value="2031">2031</option>
    </select>
  </div>
  <label for="security">Security Code</label>
  <input type ="text" id="security" name = "security" placeholder="CVV"/>
  <label for="price">Price</label>
  <input 
    type ="text" 
    id="price" 
    name="price" 
    value=<?php echo '$'.$row['price'] ?>    
    readonly
    />
  <label for="tax">Tax</label>
  <input type ="text" id="tax" name="tax" readonly/>
  <label for = "total">Total</label>
  <input type ="text" id="total" name="total" readonly/>
  <input type = "submit" value = "Purchase"/>
</form>
    </div>
  </div>

</body>
</html>

