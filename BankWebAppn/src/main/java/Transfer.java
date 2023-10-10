

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

@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		int ano=Integer.parseInt(request.getParameter("ano"));
		String name=request.getParameter("n");
		String password=request.getParameter("password");
		int tano=Integer.parseInt(request.getParameter("tano"));
		int withdraw=Integer.parseInt(request.getParameter("amt"));
		int fold_bal,fnew_bal,sold_bal = 0,snew_bal;
		
		
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
				fold_bal=rs.getInt(1);
			
				if(withdraw>fold_bal)
				{
					out.print("Insufficient Balance To Trasfer");
				}
				else
				{
					PreparedStatement ps1=con.prepareStatement("select amount from accounts where ano=?");
					ps1.setInt(1, tano);
					
					ResultSet rs1=ps1.executeQuery();
					if(rs1.next())
					{
						sold_bal=rs1.getInt(1);
					}
					
					fnew_bal=fold_bal-withdraw;
					snew_bal=sold_bal+withdraw;
					
					PreparedStatement ps2=con.prepareStatement("update accounts set amount=? where ano=?");
					ps2.setInt(1, fnew_bal);
					ps2.setInt(2, ano);
					
					ps2.executeUpdate();
					
					PreparedStatement ps3=con.prepareStatement("update accounts set amount=? where ano=?");
					ps3.setInt(1, snew_bal);
					ps3.setInt(2, tano);
					
					ps3.executeUpdate();
					
					out.print("<b>My Original Balance is: </b>"+fold_bal+"<br>");
					out.print("<b>Transfered Balance is: </b>"+withdraw+"<br>");
					out.print("<b>Target Account Balance is: </b>"+sold_bal+"<br>");
					out.print("<b>After Transfer,Target Account Balance is: </b>"+snew_bal+"<br>");
					
				}
				
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


