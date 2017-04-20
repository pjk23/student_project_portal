import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import javax.servlet.annotation.*;

@MultipartConfig
public class FileUpload extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
	    Part filePart = request.getPart("file");
	    String projectId = request.getParameter("pid");
		String description = request.getParameter("des");
		String extension = request.getParameter("ext");
	    InputStream fileBytes = null;

	    try {	
		/*if (!filePart.getContentType().equals("application/pdf"))
     	    {
		    		   writer.println("<br/> Invalid File");
		    		   return;
     	    }
		    
     	   else if (filePart.getSize()>1048576 ) { //2mb
     		   {
     		  writer.println("<br/> File size too big");
     		  return;
     		   }
     	   }*/
			fileBytes = filePart.getInputStream();  // to get the body of the request as binary data
			final byte[] bytes = new byte[fileBytes.available()];
			fileBytes.read(bytes);  //Storing the binary data in bytes array.
	        Connection con=null;
	        Statement stmt=null;
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cse096","cse096");
				if (session == null || session.getAttribute("username") == null)
						response.sendRedirect("index.html");
		    }
			catch(Exception e)
			{
				System.out.println(e);
		        System.exit(0); 
		    }
			
		    try
			{     
		    	stmt = con.createStatement();
		    	//to create table with blob field (One time only)
		    	//stmt.executeUpdate("CREATE TABLE MAINT (BookId varchar (10) not null , BookContent MEDIUMBLOB, Primary key (BookId))");
		    }
			catch (Exception e)
			{
				System.out.println("Tables already created, skipping table creation process");
			}
		    int success=0;
		    PreparedStatement pstmt = con.prepareStatement("INSERT INTO MAINT VALUES(?,?,?,?,?)");
			pstmt.setString(1,username);
		    pstmt.setString(2,projectId);
			pstmt.setBytes(3,bytes);				//Storing binary data in blob field.
			pstmt.setString(4,extension);
			pstmt.setString(5,description);
			success = pstmt.executeUpdate();
		    //if(success>=1)
				//System.out.println("File Uploaded");
		    con.close();
		    //writer.println("User "+username+" File Uploaded successfully");
			writer.println("<script>alert(\"File Uploaded Successfully\");location.href='welcome.jsp';</script>");
	    }
		catch(FileNotFoundException fnf)
		{
	        writer.println("You  did not specify a file to upload");
	        writer.println("<br/> ERROR: " + fnf.getMessage());
	    }
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
	        if(fileBytes != null)
			{
	        	fileBytes.close();
	        }
	        if (writer != null)
			{
	            writer.close();
	        }
	    }
	}
}