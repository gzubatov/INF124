# INF124
Repo for our UCI IN4MATX 124 group project.

URL: http://circinus-4.ics.uci.edu:8081/

Name:	        Email:			   ID:
Greg Zubatov    gzubatov@uci.edu   57854629
Genesis Garcia  genesirg@uci.edu   74825459
Swan Toma	    sktoma@uci.edu     32503923

REVISED DB PASSWORD = 'mytest'

1. Our home page starts with index.php, which sends an Ajax reqeust to 'product_search.php'   
   which then queries all the products in our 'products' table. The php file parses the query result, and generates echoed HTML code. 


2. Our products.js file continues to validate the user's input into text fields. Once    
   JavaScript validates a user's input, prepared statements in product.php are used to protect from SQL Injections. All information put into these fields are stored in our orders table. This same submitted order is queried in our table when we load the confirmation page (confirmation.php) based on an order id that was assigned by our MySQL databse.

   We also validate the form on the server side. In product.php we have a function
   validateForm which will be called to validate the fields before committing to our
   database. If this fails, we present an error message to the user of a processing
   request and we do not submit any data to the database.


3. After successful insertion into the 'orders' table, the user is redirected to 	
   confirmation. php where they will be shown a confirmation that their order went through and they will see all of their order details.


4. Uses of Ajax:
   * On product.php the order form will automatically fill in the city and state. When the user enters a zip code our products.js file sends a request to our getZip.php file which queries the 'zip_codes' table which returns the city and state in as JSON encoded data which we parse and insert into the form.
   
   * After the user enters the above zip code, we utilize a table join statement with 'tax_rates' table which returns their area's current tax rates. This is done in getZip.php. We then use this information to dynamically update the total cost of their order.

   * On index.php we have an event listener set on the search bar, when a user enters in any characters we send an Ajax request (index.js) to the product_search.php file, which searches our 'products' table to find any products that contain the text within their name. The php file generates and returns HTML code so that index.php can show only the products that matched the search.