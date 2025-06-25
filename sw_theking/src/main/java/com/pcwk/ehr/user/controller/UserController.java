package com.pcwk.ehr.user.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController implements PLog {
	
	@Autowired
	private UserService userService;
	
	public UserController() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ UserController()                │");
		log.debug("└─────────────────────────────────┘");
	}
	
	//로그인 후 화면
	@GetMapping("/main.do")
	public String mainPage() {
		return "user/main";
	}
	
	 //회원가입 화면
    @GetMapping("/signUP.do")
    public String signUPPage() {
        return "user/signUp";
    }
    
    //회원가입
    @PostMapping("/signUP")
    public String doSave(UserDTO user, Model model) throws SQLException {
    	try {
            // 1. 서비스 호출
            userService.doSave(user);

            // 2. 성공 시 성공 페이지로 이동
            model.addAttribute("message", "회원가입 성공!");
            return "user/main";

        } catch (IllegalArgumentException | SQLException e) {
            // 3. 유효성 검사 실패 또는 DB 오류 시
            model.addAttribute("user", user); // 사용자가 입력한 데이터 유지를 위해 다시 모델에 추가
            model.addAttribute("error", e.getMessage()); // 서비스에서 던진 에러 메시지를 모델에 추가

            // 4. 다시 회원가입 폼으로 돌아감
            return "user/signUP"; // join.jsp
        }
    }
    
	@GetMapping("/doSaveView.do")
	public String doSaveView() {
		String viewName = "user/user_mng";
		log.debug("┌─────────────────────────┐");
		log.debug("│ doSaveView()            │");
		log.debug("└─────────────────────────┘");
		
		return viewName;
	}
	
	@PostMapping(value = "/doSave.do")
	public String doSave(@RequestParam String userId, @RequestParam String name, Model model) throws SQLException {
		String viewName ="user/user_mng";
		log.debug("┌─────────────────────────┐");
		log.debug("│ doSave()                │");
		log.debug("└─────────────────────────┘");
		//http://localhost:8080/ehr/user/doSave.do?userId=pcwk01
		//String userId = req.getParameter("userId");
		log.debug("userId:" + userId);
		log.debug("name:" + name);
		
		//화면(vies)로 데이터 전달
		model.addAttribute("userId", userId);
		model.addAttribute("name", name);
		
		// /WEB-INF/views/+viewName+.jsp -> /WEB-INF/views/user/user_mng.jsp
		return viewName;
		
	}
}
