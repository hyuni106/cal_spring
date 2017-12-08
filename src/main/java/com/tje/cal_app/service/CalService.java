package com.tje.cal_app.service;

import java.util.List;
import java.util.Map;

public interface CalService {
	public void sign_up(Map<String, Object> map);
	public Map<String, Object> sign_in(Map<String, Object> map);
	public Map<String, Object> existIdCheck(Map<String, Object> map);
	public List<Map<String, Object>> participantGroup(int user_id);
	public void createGroup(Map<String, Object> map);
	public Map<String, Object> getCreateGroup(Map<String, Object> map);
	public void createUserGroup(Map<String, Object> map);
	public void inviteUser(Map<String, Object> map);
	public List<Map<String, Object>> getAllUsers();
	public List<Map<String, Object>> getAllAlert(Map<String, Object> map);
	public void createSchedule(Map<String, Object> map);
	public Map<String, Object> getScheduleById(Map<String, Object> map);
	public List<Map<String, Object>> getAllScheduleByGroup(Map<String, Object> map);
	public void createBoard(Map<String, Object> map);
	public Map<String, Object> getBoardById(Map<String, Object> map);
	public Map<String, Object> getUserById(Map<String, Object> map);
	public List<Map<String, Object>> getBoardByGroup(Map<String, Object> map);
	public void updateGroupInfo(Map<String, Object> map);
	public void updateUserNickname(Map<String, Object> map);
	public List<Map<String, Object>> getAlert(Map<String, Object> map);
	public void updateParticipantStatus(Map<String, Object> map);
	public Map<String, Object> getCountAlert(Map<String, Object> map);
	public void insertLikeBoard(Map<String, Object> map);
	public List<Map<String, Object>> getLikeUserByGroupId(Map<String, Object> map);
	public void deleteBoardLike(Map<String, Object> map);
	public void insertNewComment(Map<String, Object> map);
	public Map<String, Object> getCommentById(Map<String, Object> map);
	public List<Map<String, Object>> getAllCommentByBoardId(Map<String, Object> map);
	public List<Map<String, Object>> getAllParticipantUser(Map<String, Object> map);
	public List<Map<String, Object>> getInviteUser(Map<String, Object> map);
}
