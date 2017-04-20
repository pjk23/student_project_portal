import java.sql.*;
import javax.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Register extends HttpServlet {
public void doPost(HttpServletRequest req,HttpServletResponse res) 
throws ServletException,IOException {
  try {
 	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	try {
		//PrintWriter out=res.getWriter();
		//out.println("<html>\n<head>\n<title>Login Page</title>\n</head>");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection=null;
		connection =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("username") == null)
			res.sendRedirect("index.html");
		Statement statement = connection.createStatement();
		String rollno = req.getParameter("rollno");
		String name = req.getParameter("name");
		String pwd = req.getParameter("psw");
		String sql = "insert into LOGIN (NAME,PASSWORD,ROLLNO) values('"+ name + "' , '" + pwd +"' , '"+ rollno +"')";
	        int i = statement.executeUpdate(sql);
		if(i == 1 ){
			//out.println("one record inserted ");
			out.println("<script>alert(\"Registered successfully\");location.href='index.html';</script>");
		}
		
	} catch (ClassNotFoundException cnfe) {
		out.println("class not found ");
	  }
  } catch (SQLException e)  {
	    throw new RuntimeException("Cannot connect the database!", e);
    } 
}

}

