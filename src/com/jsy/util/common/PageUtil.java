package com.jsy.util.common;

/**
 * 计算开始页和结束页
 *
 * @author 张宇 2014年9月24日 下午3:35:52
 * <ul>
 * 	<li>张宇 2014年9月24日 下午3:35:52新增</li>
 * </ul>
 * @version 0.0.1
 */
public class PageUtil {

	/**
	 * 
	 * 得到分页开始位置
	 *
	 * @author 张宇 2014年9月24日 下午3:36:21
	 * <ul>
	 * 	<li>张宇 2014年9月24日 下午3:36:21新增</li>
	 * 	<li>张张强 2015年9月6日 重构此方法</li>
	 * </ul>
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @version 0.0.2
	 */
	public static Integer getStart(Integer pageIndex,Integer pageSize) throws Exception{
		Integer newPageIndex = 0;
		if (!CommonUtil.isEmpty(pageIndex, pageSize)) {
			newPageIndex = ((pageIndex - 1) * pageSize);
		}
		return newPageIndex;
	}
	
	/**
	 * 
	 * 计算结束位置   针对于mysql
	 *
	 * @author 张宇 2014年9月24日 下午3:36:48
	 * <ul>
	 * 	<li>张宇 2014年9月24日 下午3:36:48新增</li>
	 * </ul>
	 * @param pageno
	 * @param pagesize
	 * @return
	 * @version 0.0.1
	 */
	public static int getEnt(int pageno,int pagesize) throws Exception{
		return pagesize;
	}
	
	/**
	 * 返回总的页数
	 * @param count 总条数
	 * @param pageSize 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public static int getAllPage(int count,int pageSize) throws Exception{
		if(count == 0 || pageSize == 0){
			return 1;
		}
		int allpage = count % pageSize == 0 ? count /pageSize : count / pageSize + 1;
		
		return allpage;
	}
	
}
