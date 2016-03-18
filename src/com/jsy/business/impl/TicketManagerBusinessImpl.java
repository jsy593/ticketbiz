package com.jsy.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsy.business.BaseBusiness;
import com.jsy.business.ITicketManagerBusiness;
import com.jsy.business.base.QuestionTypeBusinessImpl;
import com.jsy.business.base.QuickReplyBusinessImpl;
import com.jsy.business.base.ReplyBusinessImpl;
import com.jsy.business.base.TicketBusinessImpl;
import com.jsy.business.base.TicketMoveBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;
import com.jsy.util.common.CommonUtil;

@Service
public class TicketManagerBusinessImpl extends BaseBusiness implements ITicketManagerBusiness {

	@Autowired
	private TicketBusinessImpl ticketBusinessImpl;

	@Autowired
	private ReplyBusinessImpl replyBusinessImpl;

	@Autowired
	private TicketMoveBusinessImpl ticketMoveBusinessImpl;

	@Autowired
	private QuickReplyBusinessImpl quickReplyBusinessImpl;

	@Autowired
	private UserBusinessImpl userBusinessImpl;

	@Autowired
	private QuestionTypeBusinessImpl questionTypeBusinessImpl;

	@Override
	public Map<String, Object> getUserTicketList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		if (CommonUtil.isEmpty(dataMap.get("status")) || "".equals(dataMap.get("status"))) {
			dataMap.put("status", 1);
		}
		try {
			reMap = ticketBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> selectTicketList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = ticketBusinessImpl.selectList(dataMap);

		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getTicketReply(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = replyBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> addTicketReply(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = replyBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> addTicketMove(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = ticketMoveBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> updateTicketMove(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		String systemIndex = (String) dataMap.get("systemIndex");

		String ticketMoveUuid = (String) dataMap.get("uuid");
		// 修改 流转表 状态
		Map<String, Object> tempMap = ticketMoveBusinessImpl.update(dataMap);
		if (STATE_ONE.equals(tempMap.get("state"))) {
			/**
			 * 修改 工单表 问题持有人Id
			 */
			// 查询 流转表 流转目标人员Id 及工单Id
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("uuid", ticketMoveUuid);
			paramsMap.put("systemIndex", systemIndex);
			Map<String, Object> ticketMoveMap = ticketMoveBusinessImpl.selectOne(paramsMap);
			if (STATE_ONE.equals(ticketMoveMap.get("state"))) {
				// 修改 工单表 问题持有人Id
				// 拼装条件
				Map<String, Object> tMoveMap = (Map<String, Object>) ticketMoveMap.get("data");
				Map<String, Object> ticketParamsMap = new HashMap<String, Object>();
				ticketParamsMap.put("uuid", tMoveMap.get("ticketId"));
				ticketParamsMap.put("ownerId", tMoveMap.get("toUserId"));
				ticketParamsMap.put("pk", "uuid");
				ticketParamsMap.put("systemIndex", systemIndex);
				reMap = ticketBusinessImpl.update(ticketParamsMap);

			}
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTicketMove(Map<String, Object> dataMap) {
		String systemIndex = (String) dataMap.get("systemIndex");
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = ticketMoveBusinessImpl.selectList(dataMap);
			if (STATE_ONE.equals(reMap.get("state"))) {
				List<Map<String, Object>> moveList = (List<Map<String, Object>>) reMap.get("list");
				for (Map<String, Object> map : moveList) {

					// 添加问题分类
					Map<String, Object> moveParamsMap = new HashMap<String, Object>();
					moveParamsMap.put("uuid", map.get("ticketId"));
					moveParamsMap.put("systemIndex", systemIndex);
					Map<String, Object> ticketMap = (Map<String, Object>) ticketBusinessImpl.selectOne(moveParamsMap)
							.get("data");
					if (ticketMap != null) {
						// 添加工单名字
						Map<String, Object> questionParamsMap = new HashMap<String, Object>();
						questionParamsMap.put("uuid", ticketMap.get("questionTypeId"));
						Map<String, Object> questionMap = (Map<String, Object>) questionTypeBusinessImpl
								.selectOne(questionParamsMap).get("data");
						if (questionMap != null) {
							map.put("questionName", questionMap.get("typeName"));
						}
					}

					// 添加发起人
					Map<String, Object> userParamsMap = new HashMap<String, Object>();
					userParamsMap.put("uuid", map.get("fromUserId"));
					Map<String, Object> userMap = (Map<String, Object>) userBusinessImpl.selectOne(userParamsMap)
							.get("data");
					if (userMap != null) {
						map.put("fromUser", userMap.get("realName"));
					}
					// 接收人
					userParamsMap.put("uuid", map.get("toUserId"));
					userMap = (Map<String, Object>) userBusinessImpl.selectOne(userParamsMap).get("data");
					if (userMap != null) {
						map.put("toUser", userMap.get("realName"));
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTicketMoveOne(Map<String, Object> dataMap) {
		String systemIndex = (String) dataMap.get("systemIndex");
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = ticketMoveBusinessImpl.selectOne(dataMap);
			Map<String, Object> map = (Map<String, Object>) reMap.get("data");
			if (map != null) {
				// 添加问题分类
				Map<String, Object> moveParamsMap = new HashMap<String, Object>();
				moveParamsMap.put("uuid", map.get("ticketId"));
				moveParamsMap.put("systemIndex", systemIndex);
				Map<String, Object> ticketMap = (Map<String, Object>) ticketBusinessImpl.selectOne(moveParamsMap)
						.get("data");
				if (ticketMap != null) {
					// 添加工单名字
					map.put("question", ticketMap.get("question"));
					// 添加类型名字
					Map<String, Object> questionParamsMap = new HashMap<String, Object>();
					questionParamsMap.put("uuid", ticketMap.get("questionTypeId"));
					Map<String, Object> questionMap = (Map<String, Object>) questionTypeBusinessImpl
							.selectOne(questionParamsMap).get("data");
					if (questionMap != null) {
						map.put("questionName", questionMap.get("typeName"));
					}
					// 添加紧急程度
					map.put("level", ticketMap.get("level"));
					// 紧急理由
					map.put("tickeReason", ticketMap.get("reason"));
				}
				// 添加发起人
				Map<String, Object> userParamsMap = new HashMap<String, Object>();
				userParamsMap.put("uuid", map.get("fromUserId"));
				Map<String, Object> userMap = (Map<String, Object>) userBusinessImpl.selectOne(userParamsMap)
						.get("data");
				if (userMap != null) {
					map.put("fromUser", userMap.get("realName"));
				}
				// 接收人
				userParamsMap.put("uuid", map.get("toUserId"));
				userMap = (Map<String, Object>) userBusinessImpl.selectOne(userParamsMap).get("data");
				if (userMap != null) {
					map.put("toUser", userMap.get("realName"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> addQuickReply(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = quickReplyBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getQuickReply(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = quickReplyBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getQuickReplyOne(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = quickReplyBusinessImpl.selectOne(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> updateQuickReply(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = quickReplyBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;

	}

	@Override
	public Map<String, Object> rejectTicketMove(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = ticketMoveBusinessImpl.update(dataMap);

		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	@Transactional
	public Map<String, Object> distributionTicketMove(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String systemIndex = (String) dataMap.get("systemIndex");
		String ticketId = (String) dataMap.get("ticketId");

		paramsMap.put("ownerId", dataMap.get("ownerId"));
		paramsMap.put("systemIndex", systemIndex);
		paramsMap.put("uuid", ticketId);

		dataMap.remove("ownerId");
		dataMap.remove("ticketId");

		// 修改 流转表信息
		ticketMoveBusinessImpl.update(dataMap);

		// 修改 工单表信息
		reMap = ticketBusinessImpl.update(paramsMap);

		return reMap;
	}

	@Override
	public Map<String, Object> addTicket(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = ticketBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public Map<String, Object> selectTicket(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		Object systemIndex = dataMap.get("systemIndex");
		try {
			Object uId = dataMap.get("userId");
			dataMap.remove("userId");
			reMap = ticketBusinessImpl.selectOne(dataMap);
			if ("1".equals(reMap.get(KEY_STATE).toString())) {
				// 查询quickReply表
				Map<String, Object> quickMap = new HashMap<String, Object>();
				quickMap.put("userId", uId);
				Map<String, Object> qReplyMap = quickReplyBusinessImpl.selectList(quickMap);
				if ("1".equals(qReplyMap.get(KEY_STATE).toString())) {
					reMap.put("quickReplyList", qReplyMap.get("list"));
				}

				// 查询reply表
				Map<String, Object> tempMap = (Map<String, Object>) reMap.get("data");
				Object userId = tempMap.get("outerUserId");
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("userId", userId);
				Map<String, Object> replyMap = new HashMap<String, Object>();
				replyMap.put("ticketId", dataMap.get("uuid"));
				replyMap.put("systemIndex", systemIndex);
				Map<String, Object> repMap = replyBusinessImpl.selectList(replyMap);
				if ("1".equals(repMap.get(KEY_STATE).toString())) {
					//查询user表，获取回复人姓名及id
					List<Map<String, Object>> list = (List<Map<String, Object>>) repMap.get("list");
					Map<String, Object> temp = new HashMap<String, Object>();
					Map<String, Object> user = new HashMap<String, Object>();
					Map<String, Object> uMap = new HashMap<String, Object>();
					for(Map<String, Object> map : list){
						temp.put("uuid", map.get("userId"));
						user = userBusinessImpl.selectOne(temp);
						if("1".equals(user.get(KEY_STATE).toString())){
							uMap = (Map<String, Object>) user.get("data");
							Object workerNo = uMap.get("workerNo");
							map.put("workerNo",workerNo);
							
						}
						temp.clear();
						user.clear();
						uMap.clear();
					}
					
					reMap.put("list", repMap.get("list"));
				}

				// 查询问题分类
				Object questionTypeId = tempMap.get("questionTypeId");
				Map<String, Object> questionMap = new HashMap<String, Object>();
				questionMap.put("uuid", questionTypeId);
				Map<String, Object> queMap = questionTypeBusinessImpl.selectOne(questionMap);
				if ("1".equals(queMap.get(KEY_STATE).toString())) {
					Map<String, Object> data = (Map<String, Object>) queMap.get("data");
					tempMap.put("typeName", data.get("typeName"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getUserTicketOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = ticketBusinessImpl.selectOne(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> updateTicket(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = ticketBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

}
