import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
	
public class Login extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
	try
	{
		int flag=0;
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		out.println("<html>\n<head>\n<title>Login Page</title>\n</head>");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection=null;
		connection =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
		//connection =DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.99:1521:cse14","cse_106","cse_106");
		Statement st=connection.createStatement();
		//out.println("Connected to database succesfully");
		ResultSet rs=st.executeQuery("select * from LOGIN");
		String username=req.getParameter("rollno");
		String password=req.getParameter("pwd");
		HttpSession session = req.getSession();
		session.setAttribute("username",username);
		session.setAttribute("password",password);
		//out.println("<table border=1>");
		while(rs.next())
		{
			if(username.equals(rs.getString(3))&&password.equals(rs.getString(2)))
				flag=1;	
			//out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td></tr>");
		}
		//out.println("</table>");
		if(flag==1)
		{
			/*String temp = (String) session.getAttribute("username");
			out.println("Session username="+temp);
			out.println("<body><h1> Login successful </h1><form method=\"post\" action=\"index.html\"> <input type=\"submit\" name=\"Logout\" value=\"Logout\"></form>");
			out.println("<form method=\"post\" action=\"UploadFile\" enctype=\"multipart/form-data\">");
			out.println("");
			out.println("</form></body></html>");*/
			res.sendRedirect("welcome.jsp");
		}
		else
		{
			out.println("<script>alert(\"Invalid username or password\");location.href='index.html';</script>");
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	}
}
