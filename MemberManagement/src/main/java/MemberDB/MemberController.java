package MemberDB;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Controller
@SuppressWarnings("serial")
@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	// DAO는 예외처리가 되어있으므로 throws를 Exception으로
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
	// URI 확인하기
	String uri = request.getRequestURI();
	String context = request.getContextPath();
	String command = uri.substring(context.length());
	
	MemberDAO member = new MemberDAO();
	
	String sit = null;		
	
	// 링크 클릭 시  Console창에서 액션 실행 여부 확인
	System.out.println("command : " + command);
	
	// 회원등록 화면에서 회원등록 버튼 클릭 시 실행
	if(command.equals("/joinAction.me")) {
		// member의 joinAction 메소드 실행		
		sit = member.joinAction(request, response);
		
	}
	
	// 회원등록 화면 시작할 때
	else if(command.equals("/joinNum.me")) {
		// member의 joinNum 메소드 실행
		sit = member.joinNum(request, response);		
		
		// main 화면 출력을 나타내기. 회원가입후 다시 회원가입 화면으로 보낸다.
		// Action으로 넘어온 값을 변경시킨후 JSP 페이지로 넘겨주기 위해서는 request.setAttribute() 를 써서 넘겨주고 
		// JSP 페이지에서는 request.getAttribute()를 써서 받아야한다.
		request.setAttribute("home", "join");
	}
	
	// 회원 목록 불러오기
	else if(command.equals("/joinSelectAll.me")) {
		// member의 joinSelectAll 메소드 실행		
		sit = member.joinSelectAll(request, response);
		
		request.setAttribute("home", "joinList");
	}
	
	// 회원 정보 불러오기
	else if(command.equals("/joinSelectOne.me")) {
		// member의 joinSelectOne 메소드 실행
		sit = member.joinSelectOne(request, response);
				
		request.setAttribute("home", "update");
		
	}
	
	// 회원 정보 수정 버튼 클릭시
	else if(command.equals("/updateAction.me")) {
		// member의 updateAction 메소드 실행
		sit = member.updateAction(request, response);

	}
	
	// 회원 매출 정보 확인
	else if(command.equals("/sales.me")) {
		// member의 sales 메소드 실행
		sit = member.sales(request, response);
		
		request.setAttribute("home", "sales");
	}
	
	// 회원 정보 삭제
	else if(command.equals("/memberDelete.me")) {
		sit = member.memberDelete(request, response);

	}
	
	// 회원 정보 체크 삭제
	else if(command.equals("/multiDelete.me")) {
		sit = member.multiDelete(request, response);

	}
		
	// 결과 수행
	RequestDispatcher dispatcher = request.getRequestDispatcher(sit);
	dispatcher.forward(request, response);
		
}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			doProcess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			doProcess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

