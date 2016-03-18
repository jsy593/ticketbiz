package com.jsy.business;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public interface IUserManagerBusiness {

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> insertUserInfo(Map<String, Object> userMap);

	/**
	 * 添加部门
	 * 
	 * @param dept
	 * @return
	 */
	public Map<String, Object> insertDeptInfo(Map<String, Object> deptMap);

	/**
	 * 审核管理员
	 * 
	 * @param User
	 * @return
	 */
	public Map<String, Object> checkUser(Map<String, Object> userMap);

	/**
	 * 审核接入系统
	 * 
	 * @param User
	 * @return
	 */
	public Map<String, Object> checkSystem(Map<String, Object> sysMap);

	/**
	 * 维护接入系统
	 * 
	 * @param User
	 * @return
	 */
	public Map<String, Object> manageSys(Map<String, Object> sysMap);

	/**
	 * 用户单条查询
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectUserOne(Map<String, Object> dataMap);

	/**
	 * 用户多条查询
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectUserList(Map<String, Object> dataMap);

	/**
	 * 用户登陆
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> dataMap);

	/**
	 * 查询单条角色表
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectRoleOne(Map<String, Object> dataMap);

	/**
	 * 修改个人信息
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> updateUserOne(Map<String, Object> dataMap);

	/**
	 * 查询该部门下的所有人
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectDeptUser(Map<String, Object> dataMap);
	/**
	 * 查询单条部门表
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> setDepartmentOne(Map<String, Object> dataMap);

	/**
	 * 查询单条系统表
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectAccessSystemOne(Map<String, Object> dataMap);

	/**
	 * 易川
	 * 
	 * 验证用户提交的 appId & appKey
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> getAccessSysStatus(Map<String, Object> dataMap);

	/**
	 * 查询单条系统表
	 * 
	 * @param dataMap
	 * @return
	 */
	@Deprecated
	Map<String, Object> selectSysOne(Map<String, Object> dataMap);

	/**
	 * 为部门添加部门管理者
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addDeptAdminForDept(Map<String, Object> deptMap);

	/**
	 * 添加部门管理者
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addUser(Map<String, Object> deptMap);

	/**
	 * 删除部门管理者
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> delDeptAdmin(Map<String, Object> deptMap);

	/**
	 * 恢复部门管理者
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> becomeDeptAdmin(Map<String, Object> deptMap);

	/**
	 * 恢复职员
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> recoverStaff(Map<String, Object> dataMap);

	/**
	 *查询该系统的该类角色包含的所有人
	 * 
	 * @param dataMap
	 * @return
	 */
	List<Map<String, Object>> selectRoleList(Map<String, Object> deptMap);

	/**
	 * 根据用户uuid或者用户名查询角色
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectRoleByUser(Map<String, Object> dataMap);

	/**
	 * 根据用户uuid或者username查询部门
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectDeptByUser(Map<String, Object> dataMap);

	/**
	 * 根据departmentId和systemId查询user表
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectUserBydept(Map<String, Object> dataMap);

	/**
	 * 根据传入的问题分类查询 部门管理员
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectUserByQuestionType(Map<String, Object> dataMap);

	/**
	 * 根据传入的code查询roleId
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectRoleIdByCode(String code);

	/**
	 * 查询system管理员列表
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectAdminList(Map<String, Object> dataMap);
	/**
	 * 查询system管理员列表
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectDeptAdminList(Map<String, Object> dataMap);

	/**
	 * 查询部门 list
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectDept(Map<String, Object> dataMap);

	/**
	 * 查询部门one
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectDeptOne(Map<String, Object> dataMap);

	/**
	 * 查询部门管理员列表
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> selectDeptAdmin(Map<String, Object> userMap);

	/**
	 * 修改部门
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> updateManageDept(Map<String, Object> dataMap);

	/**
	 * 查询员工列表
	 * 
	 * @param userMap
	 * @return
	 */
	public Map<String, Object> selectStaffList(Map<String, Object> userMap);
	
	/**
	 * 发送验证码
	 * @param userMap
	 * @return
	 */
	public Map<String, Object> sendCode(Map<String, Object> userMap);

}
