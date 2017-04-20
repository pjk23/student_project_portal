<%@page import="java.sql.*"%>
<%@page import="oracle.jdbc.driver.*" %>
<%@page import="oracle.sql.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="javax.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Student Project Portal</title>
	<link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
	<div id="header">
		<div>
			<div class="logo"><a></a></div>
			<ul id="navigation">
				<li>
					<h1>CBIT STUDENT PROJECT PORTAL</h1>
				</li>
				<li class="active">
					<a href="Logout">Logout</a>
				</li>
			</ul>
		</div>
	</div>
	<div id="search-box">
		<div class="clearfix">
			<div id="contents">
				<h2 style="font-style : Lato-Thin ;">Search by Project Name...</h2>
				<div>
				<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search here !!" title="Type in a name">
					<%try
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection connection=null;
						connection =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
						if (session == null || session.getAttribute("username") == null)
							response.sendRedirect("index.html");
						Statement st=connection.createStatement();
						ResultSet rs=st.executeQuery("select * from MAINT");
						out.println("<form method='post' action='FileDownload'>");
						out.println("<table id='customers'>");
						while(rs.next())
						{
							out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(5)+"</td><td><button type=\"submit\" name="+rs.getString(2)+">Download File</button></td></tr>");	
						}
						out.println("</table>");
						out.println("</form>");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}%>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<div class="clearfix">
			<div id="connect">
				<a href="www.cbit.ac.in" class="cbit"></a>
			</div>
			<p>
				developed by P Jitendra Kalyan, R Pranith Kumar.
			</p>
		</div>
	</div>

	<script>
		function myFunction() {
		var input, filter, table, tr, td, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("customers");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} 
				else {
					tr[i].style.display = "none";
				}
			}       
		}
	}
	</script>
</body>
</html>