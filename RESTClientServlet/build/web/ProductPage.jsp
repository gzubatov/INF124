<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta name="author" content="co-authored by Greg Zubatov, Swan Toma, Genesis Garcia">
	<meta name="description" content="ECommerce Website">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,500&amp;display=swap"
		rel="stylesheet">
	<link rel="stylesheet" href="./css/global.css">
	<link rel="stylesheet" href="./css/products.css">
	<title>Ants-R-Us</title>

</head>

<body>
	<%@ page language="java" %>
	<!-- Navbar -->

	<jsp:include page="NavBar.jsp" flush="true" />
	<!--Product Information-->

	<%! String url = ""; %>
	<%
        url += "/ProductPageServlet?pid=" + request.getParameter("pid"); 
        %>
	<jsp:include page="<%= url %>" flush="true" />


</body>

</html>