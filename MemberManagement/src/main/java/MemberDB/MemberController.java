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
	// DAO�� ����ó���� �Ǿ������Ƿ� throws�� Exception����
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
	// URI Ȯ���ϱ�
	String uri = request.getRequestURI();
	String context = request.getContextPath();
	String command = uri.substring(context.length());
	
	MemberDAO member = new MemberDAO();
	
	String sit = null;		
	
	// ��ũ Ŭ�� ��  Consoleâ���� �׼� ���� ���� Ȯ��
	System.out.println("command : " + command);
	
	// ȸ����� ȭ�鿡�� ȸ����� ��ư Ŭ�� �� ����
	if(command.equals("/joinAction.me")) {
		// member�� joinAction �޼ҵ� ����		
		sit = member.joinAction(request, response);
		
	}
	
	// ȸ����� ȭ�� ������ ��
	else if(command.equals("/joinNum.me")) {
		// member�� joinNum �޼ҵ� ����
		sit = member.joinNum(request, response);		
		
		// main ȭ�� ����� ��Ÿ����. ȸ�������� �ٽ� ȸ������ ȭ������ ������.
		// Action���� �Ѿ�� ���� �����Ų�� JSP �������� �Ѱ��ֱ� ���ؼ��� request.setAttribute() �� �Ἥ �Ѱ��ְ� 
		// JSP ������������ request.getAttribute()�� �Ἥ �޾ƾ��Ѵ�.
		request.setAttribute("home", "join");
	}
	
	// ȸ�� ��� �ҷ�����
	else if(command.equals("/joinSelectAll.me")) {
		// member�� joinSelectAll �޼ҵ� ����		
		sit = member.joinSelectAll(request, response);
		
		request.setAttribute("home", "joinList");
	}
	
	// ȸ�� ���� �ҷ�����
	else if(command.equals("/joinSelectOne.me")) {
		// member�� joinSelectOne �޼ҵ� ����
		sit = member.joinSelectOne(request, response);
				
		request.setAttribute("home", "update");
		
	}
	
	// ȸ�� ���� ���� ��ư Ŭ����
	else if(command.equals("/updateAction.me")) {
		// member�� updateAction �޼ҵ� ����
		sit = member.updateAction(request, response);

	}
	
	// ȸ�� ���� ���� Ȯ��
	else if(command.equals("/sales.me")) {
		// member�� sales �޼ҵ� ����
		sit = member.sales(request, response);
		
		request.setAttribute("home", "sales");
	}
	
	// ȸ�� ���� ����
	else if(command.equals("/memberDelete.me")) {
		sit = member.memberDelete(request, response);

	}
	
	// ȸ�� ���� üũ ����
	else if(command.equals("/multiDelete.me")) {
		sit = member.multiDelete(request, response);

	}
		
	// ��� ����
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

