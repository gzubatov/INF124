# INF124
Repo for our UCI IN4MATX 124 group project.

Name:	        Email:			   ID:
Greg Zubatov    gzubatov@uci.edu   57854629
Genesis Garcia  genesirg@uci.edu   74825459
Swan Toma	    sktoma@uci.edu     32503923


1. In the Index servlet, we display the products from our database. We display the user's product search history through the use of cookies. A cookie is added in our Product servlet with the date that the user has reached that page and the pid of the product that is being viewed. We use include in Index servlet to include History servlet which displays up to 5 recent items. History servlet takes care of outputting information for the recently viewed items. 

2. Our product details can be found in our Product servlet. This page can be reached by clicking on a product in Index (home page), which uses a link with a pid parameter. This pid is used to get the necessary details in our Product servlet. We added an 'Add to Cart' button that invokes a POST request to the Cart servlet which adds to our session object which contains a list of product ids. The user is then redirected back to the product page so that they may click the add to cart button multiple times to add a larger quantity. 

3. Our Checkout servlet, when receiving a GET request, displays a list of the products the customer has in their cart along with a form that they can fill out to place an order. The form also displays the total amount that the customer will pay. The form sends an AJAX request to the GetZipCode servlet to auto-populate the city, state, and tax rate. 

When submitting the form, a POST request is sent to the Checkout servlet. Upon receiving the POST request, the Checkout servlet validates the form data, inserts the order into the database (using prepared statement), and uses a request dispatcher to forward to the Confirmation servlet with the order id as a query parameter.

The Confirmation servlet takes the order parameter it received in the request from the Checkout servlet and pulls the order information from the database to display order confirmation details such as name, shipping address, and a list of the products that the user ordered. 