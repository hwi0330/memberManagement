package MemberDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import MemberVO.MemberVO;

// DAO
public class MemberDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	MemberDAO() throws Exception {
		con = getConnection();
	}	
	
	// DBCP ��� DB����
	public static Connection getConnection() throws SQLException, NamingException, 
        ClassNotFoundException {
		    // initialcontext ����, �̸����� ��ü�� ã�� �� �ֵ��� �����ش�.
            Context initCtx = new InitialContext();
            
            // ��Ĺ(����)�� ���� ã�´�. ��Ĺ�� ã�� ���� java:comp/env�� ã�´�.
            // initCtx�� lookup�޼��带 �̿��ؼ� "java:comp/env" �� �ش��ϴ� ��ü�� ã�Ƽ� evnCtx�� ����
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
  
            // ��Ĺ�� server.xml�� ����� db�̸�(orcl)�� ã�´�. type�� DataSource type
            // envCtx�� lookup�޼��带 �̿��ؼ� "orcl"�� �ش��ϴ� ��ü�� ã�Ƽ� ds�� ����
            DataSource ds = (DataSource) envCtx.lookup("orcl");
                 
            // getConnection�޼��带 �̿��ؼ� Ŀ�ؼ� Ǯ�� ���� Ŀ�ؼ� ��ü�� ���� conn������ ����
            Connection conn = ds.getConnection();
            
            System.out.println("������ �ҽ� ��� ���� : " + ds);
            System.out.println("con : " + conn);
            
            return conn;

    }	
	
	// ȸ������
	public String joinAction(HttpServletRequest request, HttpServletResponse response) {
		String sql = "insert into member_tbl_02 values(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			// PreparedStatement�� ����ؼ� ?�� �� ���� ����
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			String custno = request.getParameter("custno");
			String custname = request.getParameter("custname");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String joindate = request.getParameter("joindate");
			String grade = request.getParameter("grade");
			String city = request.getParameter("city");
			
			pstmt.setString(1, custno);
			pstmt.setString(2, custname);
			pstmt.setString(3, phone);
			pstmt.setString(4, address);
			pstmt.setString(5, joindate);
			pstmt.setString(6, grade);
			pstmt.setString(7, city);
			
			// �Ϸ�Ǿ����� ���� �����Ѵ�.
			int count = pstmt.executeUpdate();
			
			if(count>0) { // ���� 0�̸� ����, ���� 1�̸� ����
				System.out.println(count);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(con != null)
				    con.close();				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "joinComplete.jsp";
	}
	
	// ȸ������ �� ȸ����ȣ �ڵ� ����
	public String joinNum(HttpServletRequest request, HttpServletResponse response) {
		String sql = "select CUSTNO from member_tbl_02 where rownum = 1 order by CUSTNO DESC";
			
		try {
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery(); // executeQuery(String sql)�� ���� ���� �����ϸ� ResultSetŸ������ ��ȯ�� ���־� ������� ������ �� �ִ�.

			int count = 100001;

			// custno �÷��� ���� �����´�.
			if (rs.next()) {
				count = rs.getInt(1);
			}

		    // ������� request�� ��´�.
		    // count�� ��������� +1�� �����Ѵ�.
			request.setAttribute("count", ++count);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "index.jsp";
	
	}		
	
	// ȸ�� ��� �ҷ�����
	public String joinSelectAll(HttpServletRequest request, HttpServletResponse response)
	{
		try	{
				
			// ȸ����ȣ ��������
			String sql = "select CUSTNO, CUSTNAME, PHONE, ADDRESS, JOINDATE, GRADE, CITY from member_tbl_02 ORDER BY CUSTNO ASC";
				
			pstmt = con.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			// ������� ��� ���� ArrayList�� �غ��Ѵ�.
			List<MemberVO> list = new ArrayList<MemberVO>();
				
				
			// �˻��� ������ŭ �ݺ����� ���ư���.
			while(rs.next()) {
			    //Member��ü �ϳ��� �����Ѵ�.
			    MemberVO member = new MemberVO();
					
			    int custno = rs.getInt(1);
			    // ��ȸ�Ǵ� ��� ȸ����ȣ Consoleâ�� ���
			    System.out.println(custno);
			 
			    String custname = rs.getString(2);
			    String phone = rs.getString(3);
					
			    String address = rs.getString(4);
					
			    // dateŸ���� �ҷ��� ���, ����Ͻú��� �� �����´�. ���⼭ ������� �� yyyy-mm-dd �̹Ƿ� 10�ڸ������� �������� �ȴ�.
			    String date = rs.getString(5).substring(0,10);
					
			    String grade = rs.getString(6);
			    
			    // ��޿� ���� ǥ�ø� �����Ѵ�			    
			    // A = VIP, B = �Ϲ�, C = ����
			    if(grade.equals("A"))
			        grade = "VIP";
			    else if(grade.equals("B"))
				    grade = "�Ϲ�";
			    else if(grade.equals("C"))
				    grade = "����";
					
					
			    String city = rs.getString(7);
			   		
			    // ���� ������ ���� MemberŸ������ ��� �ִ´�.
			    // Member�� ��ü num�� String Ÿ���̰�, custno�� intŸ���̴�. ���� int���� string���� ����ȯ�� �ʿ��ϴ�.
			    member.setCustno(Integer.toString(custno));
			    member.setCustname(custname);
			    member.setPhone(phone);
			    member.setAddress(address);
			    member.setJoindate(date);
			    member.setGrade(grade);
			    member.setCity(city);
					
			    //member�� list�� ��´�.
			    list.add(member);
					
			}
				

			//list�� ������� list�� ��´�.
			request.setAttribute("list", list);
				
						
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		finally	{
			try	{
					
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(con != null)
				con.close();
		    }
			catch(Exception e)	{
					e.printStackTrace();
			}
			
			
		}
			
		return "index.jsp";
	}
	
	// ȸ�� �Ѹ��� ���� ��������
	public String joinSelectOne(HttpServletRequest request, HttpServletResponse response) {
		try {
			
		    String sql = "select CUSTNO, CUSTNAME, PHONE, ADDRESS, JOINDATE, GRADE, CITY from member_tbl_02 where CUSTNO = ?";

			pstmt = con.prepareStatement(sql);

			String num = request.getParameter("custno");
			// ��ȸ�� �ϱ� ���ؼ� ResultSet �ϱ� ���� �����´�. custno : joinlist.jsp���� ȸ����ȣ�� Ŭ������ �� custno�� �Ķ���ͷ� �Ѿ��
			pstmt.setInt(1, Integer.parseInt(num));

			rs = pstmt.executeQuery();

			MemberVO member = new MemberVO();

			// �˻��� ������ŭ �ݺ����� ���ư���.
			if (rs.next()) {
			    // Member��ü �ϳ��� �����Ѵ�.

				int custno = rs.getInt(1);
				System.out.println(custno);
			    String custname = rs.getString(2);
				String phone = rs.getString(3);
				String address = rs.getString(4);

				// dateŸ���� �ҷ��� ���, ����Ͻú��� �� �����´�. ���⼭ ������� �� yyyy-mm-dd �̹Ƿ� 10�ڸ������� �������� �ȴ�.
				String joindate = rs.getString(5).substring(0, 10);

				String grade = rs.getString(6);

				String city = rs.getString(7);

				// ���� ������ ���� MemberŸ������ ��� �ִ´�.
				// Member�� ��ü num�� String Ÿ���̰�, custno�� intŸ���̴�. ���� int���� string���� ����ȯ�� �ʿ��ϴ�.
				member.setCustno(Integer.toString(custno));
				member.setCustname(custname);
				member.setPhone(phone);
				member.setAddress(address);
				member.setJoindate(joindate);
				member.setGrade(grade);
				member.setCity(city);

			}

			// list�� ������� list�� ��´�.
			request.setAttribute("member", member);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "index.jsp";
	}
	
	// ȸ�� ���� ����
	public String updateAction(HttpServletRequest request, HttpServletResponse response)
	{
		try	{
				
			// ȸ����ȣ�������� ������������ �־��������Ƿ� ���������� �־���.
			String sql = "update member_tbl_02 set CUSTNAME = ?, PHONE = ?, ADDRESS = ?, JOINDATE = ?, GRADE = ?, CITY= ? where CUSTNO= ?";
				
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			String custno = request.getParameter("custno");
			String custname = request.getParameter("custname");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String joindate = request.getParameter("joindate");
			String grade = request.getParameter("grade");
			String city = request.getParameter("city");
			
			pstmt.setString(1, custname);
			pstmt.setString(2, phone);
			pstmt.setString(3, address);
			pstmt.setString(4, joindate);
			pstmt.setString(5, grade);
			pstmt.setString(6, city);
				
			pstmt.setInt(7, Integer.parseInt(custno));
			
			// �Ϸ�Ǿ����� ���� �����Ѵ�.
			int count = pstmt.executeUpdate();
			
			if(count>0) { // ���� 0�̸� ����, ���� 1�̸� ����
				System.out.println(count);
			}
			
		} catch(Exception e)	{
			e.printStackTrace();
		} finally	{
			try	{
					
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(con != null)
				con.close();
		    }
			catch(Exception e)	{
					e.printStackTrace();
			}
		}
			
		return "joinSelectAll.me";
	}
	
	// ȸ�� ���� ��ȸ �ҷ�����
	public String sales(HttpServletRequest request, HttpServletResponse response) {
		try {

			// join�� ����Ͽ� ���̺� 2���� ����
			String sql = "select A.CUSTNO, A.CUSTNAME, A.GRADE, SUM(B.SALES) as total from member_tbl_02 A join sales_tbl_02 B on A.CUSTNO = B.CUSTNO GROUP BY(A.CUSTNO, A.CUSTNAME, A.GRADE, B.SALES) order by SALES desc";
					
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			// ������� ��� ���� ArrayList�� �غ��Ѵ�.
			List<MemberVO> list = new ArrayList<MemberVO>();

			// �˻��� ������ŭ �ݺ����� ���ư���.
			while (rs.next()) {
				// Member��ü �ϳ��� �����Ѵ�.
				MemberVO member = new MemberVO();

				int custno = rs.getInt(1);
				
				String custname = rs.getString(2);
				String grade = rs.getString(3);
				
				// ��޿� ���� ǥ�ø� �����Ѵ�				
				// A = VIP, B = �Ϲ�, C = ����
				if (grade.equals("A"))
					grade = "VIP";
				else if (grade.equals("B"))
					grade = "�Ϲ�";
				else if (grade.equals("C"))
					grade = "����";

				int sales = rs.getInt(4);

				// ���� ������ ���� MemberŸ������ ��� �ִ´�.
				// Member�� ��ü num�� String Ÿ���̰�, custno�� intŸ���̴�. ���� int���� string���� ����ȯ�� �ʿ��ϴ�.
				member.setCustno(Integer.toString(custno));
				member.setCustname(custname);
				member.setGrade(grade);
				member.setSales(Integer.toString(sales));

				// member�� list�� ��´�.
				list.add(member);

			}

			// list�� ������� list�� ��´�.
			request.setAttribute("list", list);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
					e.printStackTrace();
			}
		}

		return "index.jsp";
	}
	
	// ȸ�� ���� ����
	public String memberDelete(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String sql = "delete from member_tbl_02 where CUSTNO = ?";
					
			pstmt = con.prepareStatement(sql);
			
			//int custno = rs.getInt(1);
			String num = request.getParameter("custno");
			System.out.println(num);
			pstmt.setInt(1, Integer.parseInt(num));		
			

			int count = pstmt.executeUpdate();;
			
			if(count > 0) {
				System.out.println("���� ����");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
					e.printStackTrace();
			}
		}

		return "joinSelectAll.me";
	}

}
