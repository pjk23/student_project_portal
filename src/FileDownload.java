import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import javax.servlet.annotation.*;
 

public class FileDownload extends HttpServlet {
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{     
        String projectId=null;
        ServletOutputStream sos=null;
        Connection con=null;
        PreparedStatement pstmt=null;
		ResultSet rs=null;
		String ext=null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
			//con =DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.121:1521:xe","cse_106","cse_106");
			//out.println("Connected to database successfully");
			HttpSession session = request.getSession();
			if (session == null || session.getAttribute("username") == null)
						response.sendRedirect("index.html");
			Statement st=con.createStatement();
			rs=st.executeQuery("select * from MAINT");
			while(rs.next())
			{
				if (request.getParameter(rs.getString(2))!=null)
				{
					projectId=rs.getString(2);
					ext=rs.getString(4);
				}
			}
			//out.println("BookId="+bookId);
		}
		catch (Exception e)
		{
			System.out.println(e);
            System.exit(0); 
        }
        response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment;filename="+projectId+"."+ext);
		sos=response.getOutputStream();
		ResultSet rset=null;
        try
		{
			pstmt = con.prepareStatement("Select FILEDATA from MAINT where PROJECTID=?");
            pstmt.setString(1,projectId.trim());
            rset = pstmt.executeQuery();
            if (rset.next())
                sos.write(rset.getBytes("FILEDATA"));
            else
                return;
        }
		catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        sos.flush();
        sos.close();
    }
}