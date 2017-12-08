package com.tje.cal_app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tje.cal_app.mappers.CalMapper;

@Service("com.tje.cal_app.service.CalServiceImpl")
public class CalServiceImpl implements CalService {
	
	@Autowired
	private CalMapper calMapper;

	@Override
	public void sign_up(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_user(map);
//		return calMapper.insert_new_user(map);
	}

	@Override
	public Map<String, Object> sign_in(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_login_user(map);
	}

	@Override
	public Map<String, Object> existIdCheck(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_exist_id(map);
	}

	@Override
	public List<Map<String, Object>> participantGroup(int user_id) {
		// TODO Auto-generated method stub
		return calMapper.select_user_group(user_id);
	}

	@Override
	public void createGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_group(map);
	}

	@Override
	public Map<String, Object> getCreateGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_group_by_id(map);
	}

	@Override
	public void inviteUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_participant(map);
	}

	@Override
	public List<Map<String, Object>> getAllUsers() {
		// TODO Auto-generated method stub
		return calMapper.select_all_user();
	}

	@Override
	public void createUserGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_participant_create(map);
	}

	@Override
	public List<Map<String, Object>> getAllAlert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_participant_by_user_id(map);
	}

	@Override
	public void createSchedule(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_schedule(map);
	}

	@Override
	public Map<String, Object> getScheduleById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_schedule_by_id(map);
	}

	@Override
	public List<Map<String, Object>> getAllScheduleByGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_schedule_by_group_id(map);
	}

	@Override
	public void createBoard(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_board(map);
	}

	@Override
	public Map<String, Object> getBoardById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_board_by_id(map);
	}

	@Override
	public Map<String, Object> getUserById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_user_by_id(map);
	}

	@Override
	public List<Map<String, Object>> getBoardByGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_board_by_group_id(map);
	}

	@Override
	public void updateGroupInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.update_group_name_and_comment(map);
	}

	@Override
	public void updateUserNickname(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.update_user_nickname(map);
	}

	@Override
	public List<Map<String, Object>> getAlert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_alert(map);
	}

	@Override
	public void updateParticipantStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.update_participant_status(map);
	}

	@Override
	public Map<String, Object> getCountAlert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_alert_count(map);
	}

	@Override
	public void insertLikeBoard(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_like(map);
	}

	@Override
	public List<Map<String, Object>> getLikeUserByGroupId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_like_user_by_board_id(map);
	}

	@Override
	public void deleteBoardLike(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.delete_like(map);
	}

	@Override
	public void insertNewComment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		calMapper.insert_new_commnet(map);
	}

	@Override
	public Map<String, Object> getCommentById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_comment_by_id(map);
	}

	@Override
	public List<Map<String, Object>> getAllCommentByBoardId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_comment_by_board_id(map);
	}

	@Override
	public List<Map<String, Object>> getAllParticipantUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_participant_user(map);
	}

	@Override
	public List<Map<String, Object>> getInviteUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return calMapper.select_invite_participant_user(map);
	}
	
}
