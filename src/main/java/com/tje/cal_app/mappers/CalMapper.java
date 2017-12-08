package com.tje.cal_app.mappers;

import java.util.List;
import java.util.Map;

public interface CalMapper {
	public void insert_new_user(Map<String, Object> map);
	public Map<String, Object> select_login_user(Map<String, Object> map);
	public Map<String, Object> select_exist_id(Map<String, Object> map);
	public List<Map<String, Object>> select_user_group(int user_id);
	public void insert_new_group(Map<String, Object> map);
	public Map<String, Object> select_group_by_id(Map<String, Object> map);
	public void insert_new_participant_create(Map<String, Object> map);
	public void insert_new_participant(Map<String, Object> map);
	public List<Map<String, Object>> select_all_user();
	public List<Map<String, Object>> select_participant_by_user_id(Map<String, Object> map);
	public void insert_new_schedule(Map<String, Object> map);
	public Map<String, Object> select_schedule_by_id(Map<String, Object> map);
	public List<Map<String, Object>> select_schedule_by_group_id(Map<String, Object> map);
	public void insert_new_board(Map<String, Object> map);
	public Map<String, Object> select_board_by_id(Map<String, Object> map);
	public Map<String, Object> select_user_by_id(Map<String, Object> map);
	public List<Map<String, Object>> select_board_by_group_id(Map<String, Object> map);
	public void update_group_name_and_comment(Map<String, Object> map);
	public void update_user_nickname(Map<String, Object> map);
	public List<Map<String, Object>> select_alert(Map<String, Object> map);
	public void update_participant_status(Map<String, Object> map);
	public Map<String, Object> select_alert_count(Map<String, Object> map);
	public void insert_new_like(Map<String, Object> map);
	public List<Map<String, Object>> select_like_user_by_board_id(Map<String, Object> map);
	public void delete_like(Map<String, Object> map);
	public void insert_new_commnet(Map<String, Object> map);
	public Map<String, Object> select_comment_by_id(Map<String, Object> map);
	public List<Map<String, Object>> select_comment_by_board_id(Map<String, Object> map);
	public List<Map<String, Object>> select_participant_user(Map<String, Object> map);
	public List<Map<String, Object>> select_invite_participant_user(Map<String, Object> map);
}
