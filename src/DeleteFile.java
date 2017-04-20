import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import javax.servlet.annotation.*;

public class DeleteFile extends HttpServlet {
	public DeleteFile() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String projectId=null;
		Statement st=null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
			HttpSession session = request.getSession();
			if (session == null || session.getAttribute("username") == null)
						response.sendRedirect("index.html");
			//out.println("Connected to database successfully");
			st=connection.createStatement();
			rs=st.executeQuery("select * from MAINT");
			while(rs.next())
			{
				if(request.getParameter(rs.getString(2))!=null)
				{
					projectId=rs.getString(2);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
            System.exit(0); 
        }
		try
		{
			pstmt = connection.prepareStatement("delete from MAINT where PROJECTID=?");
            pstmt.setString(1,projectId.trim());
			int i = pstmt.executeUpdate();
			if(i!=0)
				pw.println("<script>alert(\"Record Deleted\");location.href='welcome.jsp';</script>");
			else if (i==0)
			{
				pw.println("<script>alert(\"Record deleted\");location.href='welcome.jsp';</script>");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
}