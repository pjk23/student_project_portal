import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;

public class Logout extends HttpServlet {  
        protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {  
            response.setContentType("text/html");
            PrintWriter out=response.getWriter();
              
            HttpSession session=request.getSession();
			if (session == null || session.getAttribute("username") == null)
						response.sendRedirect("index.html");
            session.invalidate();  
            
            response.sendRedirect("index.html");  
              
            out.close();  
    }  
}  