

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Deposite")
public class Deposite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		int ano=Integer.parseInt(request.getParameter("ano"));
		String name=request.getParameter("n");
		String password=request.getParameter("password");
		int deposite=Integer.parseInt(request.getParameter("amt"));
		int old_bal,new_bal;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "pkdb", "pkdb");
			
			PreparedStatement ps=con.prepareStatement("select amount from accounts where ano=? and name=? and password=?");
			ps.setInt(1, ano);
			ps.setString(2,name);
			ps.setString(3,password);
		
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				old_bal=rs.getInt(1);
				new_bal=old_bal+deposite;
				PreparedStatement ps2=con.prepareStatement("update accounts set amount=? where ano=?");
				ps2.setInt(1, new_bal);
				ps2.setInt(2, ano);
				
				ps2.executeUpdate();
				
				out.print("<b>My Original Balance is: </b>"+old_bal+"<br>");
				out.print("<b>My Deposite Balance is: </b>"+deposite+"<br>");
				out.print("<b>My New Balance is: </b>"+new_bal+"<br>");
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
