

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

@WebServlet("/NewAccount")
public class NewAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		int ano=Integer.parseInt(request.getParameter("ano"));
		String name=request.getParameter("n");
		String password=request.getParameter("pwd");
		double amount=Double.parseDouble(request.getParameter("amt"));
		String address=request.getParameter("address");
		int mobileno=Integer.parseInt(request.getParameter("mob"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "pkdb", "pkdb");
			
			PreparedStatement ps=con.prepareStatement("insert into accounts values(?,?,?,?,?,?)");
			ps.setInt(1, ano);
			ps.setString(2,name);
			ps.setString(3,password);
			ps.setDouble(4,amount);
			ps.setInt(6, mobileno);
			ps.setString(5,address);
			
			
			int i=ps.executeUpdate();
			out.print(i+"  User sucessfully registered....");
			con.close();
		}
		catch(Exception e)
		{
			out.print(e);
		}
	}
}
