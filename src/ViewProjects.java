import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
	
public class ViewProjects extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
	try
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		out.println("<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Student Project Portal</title><link rel='stylesheet' href='css/style.css' type='text/css'></head><body><div id='header'><div><div class='logo'><a></a></div><ul id='navigation'><li><h1>CBIT STUDENT PROJECT PORTAL</h1></li><li class='active'><a href='Logout'>Logout</a></li></ul></div></div><div id='upload-box'><div class='u-box'><h1>Perform Actions on your Project here ...</h1><div id='form-style'>");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection=null;
		connection =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("username") == null)
				res.sendRedirect("index.html");
		//connection =DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.99:1521:cse14","cse_106","cse_106");
		Statement st=connection.createStatement();
		//out.println("Connected to database succesfully");
		String projectId=(String)req.getParameter("param");		
		PreparedStatement pstmt = connection.prepareStatement("Select * from MAINT where PROJECTID=?");
		pstmt.setString(1,projectId.trim());
		ResultSet rs = pstmt.executeQuery();
		out.println("<form method=\"post\" action=\"FileDownload\">");
		if(rs.next())
		{
			out.println	("<label><h3>ROLLNO</h3></label>"+rs.getString(1)+"</br>");
			out.println("<label><h3>Project-ID</h3></label>"+rs.getString(2)+"</br>");
			out.println	("<label><h3>Description</h3></label>"+rs.getString(5)+"</br>");
			out.println	("</br><label><h3>Download your project</h3></label></br>");
			out.println("<button type=\"submit\" name="+rs.getString(2)+">Download here</button>");
		}
		out.println("</form>");
		out.println("<form method=\"post\" action=\"DeleteFile\">");
		out.println	("</br><label><h3>Do you want to remove your project?</h3></label></br>");
		out.println("<button type=\"submit\" name="+rs.getString(2)+">Delete Here !!!</button>");
		out.println("</form>");
		out.println("<div id='footer'><div class='clearfix'><div id='connect'><a href='https\\:www.cbit.ac.in' class='cbit'></a></div><p>developed by P Jitendra Kalyan, R Pranith Kumar.</p></div></div></body></html>");
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	}
}
