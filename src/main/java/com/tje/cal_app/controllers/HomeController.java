package com.tje.cal_app.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.tje.cal_app.service.CalService;
import com.tje.cal_app.service.S3Service;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource(name = "com.tje.cal_app.service.S3ServiceImpl")
	private S3Service s3Service;
	

	@Resource(name = "com.tje.cal_app.service.CalServiceImpl")
	private CalService calService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/user/sign_up")
	@ResponseBody
	public Map<String, Object> signUp(@RequestParam(value = "") Map<String, Object> map) {
		calService.sign_up(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("message", "가입완료");

		return result;
	}

	@RequestMapping(value = "/user/sign_in")
	@ResponseBody
	public Map<String, Object> signIn(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (calService.sign_in(map) != null) {
			result.put("result", "OK");
			Map<String, Object> user = calService.sign_in(map);
			result.put("user", user);
			int user_id = (Integer) user.get("id");
			result.put("group", calService.participantGroup(user_id));
		} else {
			result.put("result", "FALSE");
		}

		return result;
	}

	@RequestMapping(value = "/user/check_dupl_id")
	@ResponseBody
	public Map<String, Object> existIdCheck(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (calService.existIdCheck(map) != null) {
			result.put("result", "FALSE");
		} else {
			result.put("result", "OK");
		}

		return result;
	}

	@RequestMapping(value = "/group/create")
	@ResponseBody
	public Map<String, Object> createGroup(@RequestParam(value = "") Map<String, Object> map) {
		calService.createGroup(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("message", "생성 완료");

		Map<String, Object> group = new HashMap<String, Object>();
		group.put("group_id", map.get("id"));
		result.put("group", calService.getCreateGroup(group));

		String invite_users = (String) map.get("participant_user_id");

		Map<String, Object> invite = new HashMap<String, Object>();
		invite.put("user_id", map.get("user_id"));
		invite.put("group_id", map.get("id"));

		String[] users = invite_users.split(",");
		for (int i = 0; i < users.length; i++) {
			invite.put("participant_user_id", users[i]);
			if (i == 0) {
				calService.createUserGroup(invite);
			} else {
				calService.inviteUser(invite);
			}
		}

		return result;
	}

	@RequestMapping(value = "/group/allUsers")
	@ResponseBody
	public Map<String, Object> getAllUsers() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("users", calService.getAllUsers());

		return result;
	}
	
	@RequestMapping(value = "/user/getAllAlert")
	@ResponseBody
	public Map<String, Object> getAllAlert(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("alert", calService.getAllAlert(map));

		return result;
	}
	
	@RequestMapping(value = "/group/createSchedule")
	@ResponseBody
	public Map<String, Object> createSchedule(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		calService.createSchedule(map);
		result.put("result", "OK");
		result.put("schedule", calService.getScheduleById(map));

		return result;
	}
	
	@RequestMapping(value = "/group/getSchedule")
	@ResponseBody
	public Map<String, Object> getSchedule(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("schedule", calService.getScheduleById(map));

		return result;
	}
	
	@RequestMapping(value = "/group/getAllSchedule")
	@ResponseBody
	public Map<String, Object> getAllSchedule(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("schedule", calService.getAllScheduleByGroup(map));
		List<Map<String, Object>> boards = calService.getBoardByGroup(map);
		for (Map<String, Object> boardMap : boards) {
			Map<String, Object> user = calService.getUserById(boardMap);
			boardMap.put("user", user);
			List<Map<String, Object>> like_user = calService.getLikeUserByGroupId(boardMap);
			boardMap.put("like_user", like_user);
		}
		result.put("board", boards);

		return result;
	}
	
	@RequestMapping(value = "/group/createBoard")
	@ResponseBody
	public Map<String, Object> createBoard(@RequestParam(value = "") Map<String, Object> map) {
		calService.createBoard(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		Map<String, Object> board = calService.getBoardById(map);
		board.put("user", calService.getUserById(map));
		result.put("board", board);
		

		return result;
	}
	
	@RequestMapping(value = "/group/updateGroupInfo")
	@ResponseBody
	public Map<String, Object> updateGroupInfo(@RequestParam(value = "") Map<String, Object> map) {
		calService.updateGroupInfo(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("group", calService.getCreateGroup(map));
		
		return result;
	}
	
	@RequestMapping(value = "/user/updateNickname")
	@ResponseBody
	public Map<String, Object> updateNickname(@RequestParam(value = "") Map<String, Object> map) {
		calService.updateUserNickname(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");

		return result;
	}
	
	@RequestMapping(value = "/user/userGroup")
	@ResponseBody
	public Map<String, Object> participantGroup(@RequestParam(value = "") Map<String, Object> map) {
//		calService.updateUserNickname(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		int user_id = Integer.parseInt((String)map.get("user_id"));
		result.put("group", calService.participantGroup(user_id));

		return result;
	}
	
	@RequestMapping(value = "/user/get_alert")
	@ResponseBody
	public Map<String, Object> getAlert(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		List<Map<String, Object>> alerts = calService.getAlert(map);
		for (Map<String, Object> alertMap : alerts) {
			Map<String, Object> user = calService.getUserById(alertMap);
			alertMap.put("user", user);
			Map<String, Object> group = calService.getCreateGroup(alertMap);
			alertMap.put("group", group);
		}
		result.put("alert", alerts);

		return result;
	}
	
	@RequestMapping(value = "/user/participant_status")
	@ResponseBody
	public Map<String, Object> updateParticipantStatus(@RequestParam(value = "") Map<String, Object> map) {
		calService.updateParticipantStatus(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		int user_id = Integer.parseInt((String)map.get("user_id"));
		result.put("group", calService.participantGroup(user_id));

		return result;
	}
	
	@RequestMapping(value = "/user/alert_count")
	@ResponseBody
	public Map<String, Object> getAlertCount(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		result.put("count", calService.getCountAlert(map));

		return result;
	}
	
	@RequestMapping(value = "/board/new_like")
	@ResponseBody
	public Map<String, Object> insertLikeBoard(@RequestParam(value = "") Map<String, Object> map) {
		calService.insertLikeBoard(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");

		return result;
	}
	
	@RequestMapping(value = "/board/delete_like")
	@ResponseBody
	public Map<String, Object> deleteLikeBoard(@RequestParam(value = "") Map<String, Object> map) {
		calService.deleteBoardLike(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");

		return result;
	}
	
	@RequestMapping(value = "/board/new_comment")
	@ResponseBody
	public Map<String, Object> insertNewComment(@RequestParam(value = "") Map<String, Object> map) {
		calService.insertNewComment(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		Map<String, Object> comment = calService.getCommentById(map);
		comment.put("write_user", calService.getUserById(comment));
		result.put("comment", comment);

		return result;
	}
	
	@RequestMapping(value = "/board/all_comment")
	@ResponseBody
	public Map<String, Object> getAllCommentByBoardId(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		
		List<Map<String, Object>> comment = calService.getAllCommentByBoardId(map);
		for (Map<String, Object> commentMap : comment) {
			Map<String, Object> user = calService.getUserById(commentMap);
			commentMap.put("write_user", user);
		}
		result.put("comment", comment);

		return result;
	}
	
	@RequestMapping(value = "/group/all_user")
	@ResponseBody
	public Map<String, Object> getParticipantUser(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "OK");
		
		List<Map<String, Object>> participant = calService.getAllParticipantUser(map);
		for (Map<String, Object> partyMap : participant) {
			Map<String, Object> user_id = new HashMap<String, Object>();
			user_id.put("user_id", partyMap.get("id"));
			Map<String, Object> user = calService.getUserById(user_id);
			partyMap.put("user", user);
			partyMap.put("group", calService.getCreateGroup(partyMap));
		}
		
		result.put("participant", participant);
		
		List<Map<String, Object>> invite = calService.getInviteUser(map);
		for (Map<String, Object> inviteMap : invite) {
			Map<String, Object> user_id = new HashMap<String, Object>();
			user_id.put("user_id", inviteMap.get("id"));
			Map<String, Object> user = calService.getUserById(user_id);
			inviteMap.put("user", user);
			inviteMap.put("group", calService.getCreateGroup(inviteMap));
		}
		
		result.put("invite", invite);

		return result;
	}
	
	@RequestMapping(value = "/file_upload_test")
	@ResponseBody
	public Map<String, Object> file_upload_test(@RequestParam(value = "") Map<String, Object> map,
			@RequestParam("file") MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<PutObjectResult> upload = s3Service.upload(file);
		
		result.put("upload", upload);
		result.put("original_name", file.getOriginalFilename());
		
		return result;
	}
	
	
	@RequestMapping(value = "/push_test")
	@ResponseBody
	public Map<String, Object> pushTest(@RequestParam(value = "") Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> userInfo = calService.getUserById(map);
		
		String title = "알림제목";
		String content = map.get("content").toString();
		String token = userInfo.get("device_token").toString();
//		String token = "토큰값";
		
		sendPush(title, content, token);
		
		return result;
	}
	
	void sendPush(String title, String content, String inputToken) {
		String token = inputToken;
		String apiKey = "AAAA_xx40aI:APA91bHZEKD-HPJN_DhrVyW_NXRaZWwKkf4NMpaWJ4EFXfOrphNB_R2fwUm_7Q22qEj2D-dxVeD0skv4csmcph0UX2hUvCMvcdHpZH-cZ0JpSoGPAuBrImZWiM8zAblkUdRAF10Whf9L";
		
		try {
			// 파이어베이스 서버 접속 url
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			
			// 주소를 기반으로 실제 연결 준비
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			// 연결 관련 세팅
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "key="+apiKey);
			
			// 보내줄 내용 정리
			String input = "{\"notification\" : { \"title\" : \""+ title +"\","
					+ " \"body\" : \""+ content +"\"},"
					+ " \"to\" : \"" + token + "\" }";
			
			System.out.println(input);
			
			OutputStream os = con.getOutputStream();
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			int responseCode = con.getResponseCode();
			
			System.out.println("응답코드 : " + responseCode);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
