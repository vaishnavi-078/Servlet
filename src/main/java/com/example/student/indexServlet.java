package com.example.student;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class indexServlet
 */
@WebServlet("/indexServlet")
public class indexServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public indexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String sname = request.getParameter("sname");
		String sid = request.getParameter("sid");
		String branch = request.getParameter("branch");
		String pass = request.getParameter("pass");
		String rpass = request.getParameter("rpass");
		
		if(rpass.equals(pass)) {
			Connection con;
			PreparedStatement stmt;
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","qwerty");
				stmt = con.prepareStatement("insert into student values (?,?,?,?)");
				System.out.println("Connectionn succewssful");
				stmt.setString(1, sid);
				stmt.setString(2, sname);
				stmt.setString(3, branch);
				stmt.setString(4, pass);
				
				int r= stmt.executeUpdate();
				System.out.println(r);
				out.println("REGISTRATION SUCCESSFULL");
				response.sendRedirect("login.html");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			out.println("password didnt match");
		}
	}

}
