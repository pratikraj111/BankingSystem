

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Closeac")
public class Closeac extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		int ano=Integer.parseInt(request.getParameter("ano"));
		String name=request.getParameter("n");
		String password=request.getParameter("password");
		;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "pkdb", "pkdb");
			
			PreparedStatement ps=con.prepareStatement("delete from accounts where ano=? and name=? and password=?");
			ps.setInt(1, ano);
			ps.setString(2,name);
			ps.setString(3,password);
		
			int i=ps.executeUpdate();
			if(i==1)
			{
				out.print("<b>Account is successfully deleted... </b><br>");
			}
			else
			{
				out.print("Invalid Input...");
			}
			con.close();
		}
		catch(Exception e)
		{
			out.print(e);
		}

	}

}
