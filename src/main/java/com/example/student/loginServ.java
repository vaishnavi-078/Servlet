package com.example.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class loginServ
 */
@WebServlet("/loginServ")
public class loginServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public loginServ() {
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
		Boolean f=false;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String sid = request.getParameter("sid");
		String pass = request.getParameter("pass");
		Connection con;
		Statement stmt;
		ResultSet rs;
		String name=null;
		Date logint =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","qwerty");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select id,password,name from student");
			
			while(rs.next()) {
			String id = rs.getString(1);
			String passr = rs.getString(2);
			
			if(sid.equals(id)&& pass.equals(passr)) {
				f= true;
				name = rs.getString(3);
				System.out.println(name);
				HttpSession hs = request.getSession();
				logint = new Date(hs.getCreationTime());
				hs.setAttribute("name", name);
			}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
				if(f) {
					out.println("Logged in time:"+ logint);
					out.println("Welcome :"+name);
					RequestDispatcher rd = request.getRequestDispatcher("NewFile.html");
					rd.include(request, response);
					
				}else {
					out.println("Please register first");
					RequestDispatcher rd = request.getRequestDispatcher("index.html");
					rd.include(request, response);
				}
	}

}
