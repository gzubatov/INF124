# INF124
Repo for our UCI IN4MATX 124 group project.

URL: http://circinus-4.ics.uci.edu:8080/project1/

Name:	        Email:			   ID:
Greg Zubatov    gzubatov@uci.edu   57854629
Genesis Garcia  genesirg@uci.edu   74825459
Swan Toma	    sktoma@uci.edu     32503923

1. An overview of your business, the products you sell, the management team, and any other information that you think makes sense for the customers to know about your company.

In the "About" HTML page you'll find a description of our arts and crafts eCommerce store. Here,
we also have information about each team member.

2. Display a list of products (at least 10) available for sale in a table with multiple rows and column, where each product is shown within a separate cell.
    
The table can be found on index.html (our home page). 

3. Display an image for each product available for sale in each cell.

All products can be found with their corresponding image on our home page (index.html).

4. Display the price and other key information (e.g., color, material, etc.) associated with each product in the corresponding table cell.

Each product has a name, image and a price on the home page. Clicking on any image will take you to the detailed information page for a product where a form will be found. 

5. The user should be able to choose a product from this table by clicking on the corresponding image, which should lead to a new page that provides additional details about the product, e.g., more images, detailed description, etc. 

Clicking on any image will take you to the detailed information page for a product where the user will find all relevant product details and a form to fill out to purchase the product. 

6. On the detailed description page, the user should be able to order a product by filling a form. The form should allow the user to enter the product identifier, quantity, first name, last name, phone number, shipping address, shipping method (e.g., overnight, 2-days expedited, 6-days ground), credit card information, and anything else that you think makes sense for your business.

Each product has its own description page that contains its image, name, price and description, and an ID. Users are able to fill a form out for the corresponding product with the necessary information to buy that product.

7. Upon submitting the form, the website should send an email with the purchase order information included in the body of the email. Note that to really send an email, one needs to connect to the SMTP server, which is not possible with the client-side software. Thus, this requirement simply requires bringing up the email client with the purchase order information included in the body of the email and allowing the user to send the email.

The email client will open if a form is submitted without any errors. We have a single alert message that appears. In the alert you'll find a corresponding error message to each field that was filled incorrectly. If there are errors a submission will not be allowed.

8. Before submitting the form, it should be checked for proper formatting, including whether all fields are filled properly, whether the phone number, address, and credit card are properly formatted, etc. An alarm should be raised if a field is empty or not properly formatted to prevent submission of bad data. 

An alert is raised if the required fields are not completed or are not the required length. The functions for this feature can be found in products.js.

9. Your website should use CSS to specify at least 10 stylistic properties for your website, such as the background for your table, the color and size of your font, the size of your images, and other stylistic preferences you may have.

We use well over 10 stylistic properties, all of which can be seen in our CSS files within the CSS folder.
Global.css - implements global stylings relevant to all web pages, such as styling for the header navigation
Index.css - Handles home page styling
Product.css - Handles styling for the product page template
About.css - Handles the styling for the 'About Us' page

10. Provide the ability to track the mouse movement, such that when the mouse moves over a product image, the size of the image is increased, and when the mouse moves out, the size of the image is returned back to normal. This feature can be implemented on either the page that displays the various products, or on the pages that show the details of each product, or both.

On the home page we use 'mouseover' and 'mouseleave' event listeners attached to the products on the homapage to track a user's mouse movement and to dynamically increase/decresase the size of the image to provide a hover zoom rollover effect. Relevant code can be found in the index.js file.

11. Print the name and the StudentID of group members on the webpage.

Our student ID numbers can be found at the top of the About page.
 