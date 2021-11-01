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
	
	// DBCP 방식 DB연결
	public static Connection getConnection() throws SQLException, NamingException, 
        ClassNotFoundException {
		    // initialcontext 생성, 이름으로 객체를 찾을 수 있도록 도와준다.
            Context initCtx = new InitialContext();
            
            // 톰캣(서버)를 먼저 찾는다. 톰캣을 찾을 때는 java:comp/env로 찾는다.
            // initCtx의 lookup메서드를 이용해서 "java:comp/env" 에 해당하는 객체를 찾아서 evnCtx에 삽입
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
  
            // 톰캣의 server.xml에 등록한 db이름(orcl)을 찾는다. type은 DataSource type
            // envCtx의 lookup메서드를 이용해서 "orcl"에 해당하는 객체를 찾아서 ds에 삽입
            DataSource ds = (DataSource) envCtx.lookup("orcl");
                 
            // getConnection메서드를 이용해서 커넥션 풀로 부터 커넥션 객체를 얻어내어 conn변수에 저장
            Connection conn = ds.getConnection();
            
            System.out.println("데이터 소스 룩업 성공 : " + ds);
            System.out.println("con : " + conn);
            
            return conn;

    }	
	
	// 회원가입
	public String joinAction(HttpServletRequest request, HttpServletResponse response) {
		String sql = "insert into member_tbl_02 values(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			// PreparedStatement를 사용해서 ?에 값 지정 가능
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
			
			// 완료되었으면 값을 지정한다.
			int count = pstmt.executeUpdate();
			
			if(count>0) { // 값이 0이면 실패, 값이 1이면 성공
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
	
	// 회원가입 시 회원번호 자동 생성
	public String joinNum(HttpServletRequest request, HttpServletResponse response) {
		String sql = "select CUSTNO from member_tbl_02 where rownum = 1 order by CUSTNO DESC";
			
		try {
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery(); // executeQuery(String sql)을 통해 쿼리 실행하면 ResultSet타입으로 반환을 해주어 결과값을 저장할 수 있다.

			int count = 100001;

			// custno 컬럼의 값을 가져온다.
			if (rs.next()) {
				count = rs.getInt(1);
			}

		    // 결과값을 request에 담는다.
		    // count의 결과값에서 +1을 진행한다.
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
	
	// 회원 목록 불러오기
	public String joinSelectAll(HttpServletRequest request, HttpServletResponse response)
	{
		try	{
				
			// 회원번호 내림차순
			String sql = "select CUSTNO, CUSTNAME, PHONE, ADDRESS, JOINDATE, GRADE, CITY from member_tbl_02 ORDER BY CUSTNO ASC";
				
			pstmt = con.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			// 결과값을 담기 위해 ArrayList를 준비한다.
			List<MemberVO> list = new ArrayList<MemberVO>();
				
				
			// 검색된 개수만큼 반복문이 돌아간다.
			while(rs.next()) {
			    //Member객체 하나를 생성한다.
			    MemberVO member = new MemberVO();
					
			    int custno = rs.getInt(1);
			    // 조회되는 모든 회원번호 Console창에 출력
			    System.out.println(custno);
			 
			    String custname = rs.getString(2);
			    String phone = rs.getString(3);
					
			    String address = rs.getString(4);
					
			    // date타입을 불러올 경우, 년월일시분초 로 가져온다. 여기서 년월일은 총 yyyy-mm-dd 이므로 10자리까지만 가져오면 된다.
			    String date = rs.getString(5).substring(0,10);
					
			    String grade = rs.getString(6);
			    
			    // 등급에 따라 표시를 변경한다			    
			    // A = VIP, B = 일반, C = 직원
			    if(grade.equals("A"))
			        grade = "VIP";
			    else if(grade.equals("B"))
				    grade = "일반";
			    else if(grade.equals("C"))
				    grade = "직원";
					
					
			    String city = rs.getString(7);
			   		
			    // 위에 가져온 것을 Member타입으로 모두 넣는다.
			    // Member의 객체 num은 String 타입이고, custno는 int타입이다. 따라서 int에서 string으로 형변환이 필요하다.
			    member.setCustno(Integer.toString(custno));
			    member.setCustname(custname);
			    member.setPhone(phone);
			    member.setAddress(address);
			    member.setJoindate(date);
			    member.setGrade(grade);
			    member.setCity(city);
					
			    //member를 list에 담는다.
			    list.add(member);
					
			}
				

			//list의 결과값을 list에 담는다.
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
	
	// 회원 한명의 정보 가져오기
	public String joinSelectOne(HttpServletRequest request, HttpServletResponse response) {
		try {
			
		    String sql = "select CUSTNO, CUSTNAME, PHONE, ADDRESS, JOINDATE, GRADE, CITY from member_tbl_02 where CUSTNO = ?";

			pstmt = con.prepareStatement(sql);

			String num = request.getParameter("custno");
			// 조회를 하기 위해서 ResultSet 하기 전에 가져온다. custno : joinlist.jsp에서 회원번호를 클릭했을 때 custno가 파라미터로 넘어옴
			pstmt.setInt(1, Integer.parseInt(num));

			rs = pstmt.executeQuery();

			MemberVO member = new MemberVO();

			// 검색된 개수만큼 반복문이 돌아간다.
			if (rs.next()) {
			    // Member객체 하나를 생성한다.

				int custno = rs.getInt(1);
				System.out.println(custno);
			    String custname = rs.getString(2);
				String phone = rs.getString(3);
				String address = rs.getString(4);

				// date타입을 불러올 경우, 년월일시분초 로 가져온다. 여기서 년월일은 총 yyyy-mm-dd 이므로 10자리까지만 가져오면 된다.
				String joindate = rs.getString(5).substring(0, 10);

				String grade = rs.getString(6);

				String city = rs.getString(7);

				// 위에 가져온 것을 Member타입으로 모두 넣는다.
				// Member의 객체 num은 String 타입이고, custno는 int타입이다. 따라서 int에서 string으로 형변환이 필요하다.
				member.setCustno(Integer.toString(custno));
				member.setCustname(custname);
				member.setPhone(phone);
				member.setAddress(address);
				member.setJoindate(joindate);
				member.setGrade(grade);
				member.setCity(city);

			}

			// list의 결과값을 list에 담는다.
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
	
	// 회원 정보 수정
	public String updateAction(HttpServletRequest request, HttpServletResponse response)
	{
		try	{
				
			// 회원번호기준으로 내림차순으로 주어져있으므로 내림차순을 주었다.
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
			
			// 완료되었으면 값을 지정한다.
			int count = pstmt.executeUpdate();
			
			if(count>0) { // 값이 0이면 실패, 값이 1이면 성공
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
	
	// 회원 매출 조회 불러오기
	public String sales(HttpServletRequest request, HttpServletResponse response) {
		try {

			// join을 사용하여 테이블 2개를 연결
			String sql = "select A.CUSTNO, A.CUSTNAME, A.GRADE, SUM(B.SALES) as total from member_tbl_02 A join sales_tbl_02 B on A.CUSTNO = B.CUSTNO GROUP BY(A.CUSTNO, A.CUSTNAME, A.GRADE, B.SALES) order by SALES desc";
					
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			// 결과값을 담기 위해 ArrayList를 준비한다.
			List<MemberVO> list = new ArrayList<MemberVO>();

			// 검색된 개수만큼 반복문이 돌아간다.
			while (rs.next()) {
				// Member객체 하나를 생성한다.
				MemberVO member = new MemberVO();

				int custno = rs.getInt(1);
				
				String custname = rs.getString(2);
				String grade = rs.getString(3);
				
				// 등급에 따라 표시를 변경한다				
				// A = VIP, B = 일반, C = 직원
				if (grade.equals("A"))
					grade = "VIP";
				else if (grade.equals("B"))
					grade = "일반";
				else if (grade.equals("C"))
					grade = "직원";

				int sales = rs.getInt(4);

				// 위에 가져온 것을 Member타입으로 모두 넣는다.
				// Member의 객체 num은 String 타입이고, custno는 int타입이다. 따라서 int에서 string으로 형변환이 필요하다.
				member.setCustno(Integer.toString(custno));
				member.setCustname(custname);
				member.setGrade(grade);
				member.setSales(Integer.toString(sales));

				// member를 list에 담는다.
				list.add(member);

			}

			// list의 결과값을 list에 담는다.
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
	
	// 회원 정보 삭제
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
				System.out.println("삭제 성공");
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
