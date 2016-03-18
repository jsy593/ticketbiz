package com.jsy.util.common;

/**
 * 常量池,所有相关常量都写在这
 *
 * @author 张强 2014年9月22日 下午5:26:31
 * @version 0.0.1
 */
public interface ConstantUtil {
	/**
	 * CookieUtil工具类,默认生命时间
	 */
	public Integer COOKIE_AGE = 30 * 60 * 60;
	public String COOKIE_USERINFO = "userinfo";
	public String COOKIE_USER_ROLES = "userroles";
	public String COOKIE_USER_DETAIL = "userdetail";
	
	public String JSONUTILE_ISNULL = "(:null,|:NULL,)";
	public String JSONUTILE_REPLACE = 	":\"\",";
	public String JSONUTILE_JSONNULL= "{}" ;
	
	/**
	 * 操作常量,增
	 */
	public String OPERATE_OPER = "oper";
	
	/**
	 * 操作常量,增
	 */
	public String OPERATE_ADD = "add";
	
	/**
	 * ueditor 上传相关变量
	 */
	public String UEDITOR_UPLOAD_URL = "admin/uedit";

	/**
	 * 操作常量,编辑
	 */
	public String OPERATE_EDIT = "edit";
	
	/**
	 * 操作常量,删除
	 */
	public String OPERATE_DEL = "del";
	
	/**
	 * 操作常量,查询
	 */
	public String OPERATE_SEL = "view";
	
	/**
	 * 字典工具类缓存
	 */
	public String CACHE_DICT_KEY="DictionCache";
	
	/**
	 * 字典信息缓存KEY
	 */
	public String Diction_KEY="allDict";
	
	/**
	 * 配置文件 路径 /config.properties
	 */
	public String CONFIG_PATH = "/config.properties";
	
	/**
	 * 配置文件子项 loginWait
	 */
	public String CONFIG_LOGINWAIT = "loginwait";
	
	/**
	 * 错误的状态
	 */
	public String RETURN_PARAME_ERROR = "-1";
	
	/**
	 * 成功的状态
	 */
	public String RETURN_SUCCESS = "1";
	
	/**
	 * 失败的状态
	 */
	public String RETURN_FAIL = "0";
	
	// 以下为常量数字,无任何意义,方便使用
	public Integer DIGITAL_ZERO = 0;
	public Integer DIGITAL_ONE = 1;
	public Integer DIGITAL_TWO = 2;
	public Integer DIGITAL_THREE = 3;
	public Integer DIGITAL_FOURE = 4;
	public Integer DIGITAL_FIVE = 5;
	public Integer DIGITAL_SIX = 6;
	public Integer DIGITAL_SEVEN = 7;
	public Integer DIGITAL_EIGHT = 8;
	public Integer DIGITAL_NINE = 9;
	public Integer DIGITAL_TEN = 10;
	
	
	
	// 字典表
	// 字段表所有的最顶级节点的parentId
	public String DICT_ROOT_PARENTID = "0";
	// 学校分类
	public Integer DICT_ORG_SCHOOLE = 1;
	
	// 字典表大学
	public String DICT_SCHOOLE_UNIVERSITY = "00001";
	
	//后台 登陆的用户
	public String SESSION_LOGIN_USER = "sessionLoginUserInfo";
	
	// 后台导航
	public String SESSION_NAVIGATEBAR = "navigateBar";
	//前台 登陆的用户
	public String SESSION_LOGIN_BEFOREUSER = "sessionLoginBeforeUser";
	// 前台导航
	public String SESSION_NAVIGATE = "navigate";
	
	//登陆之前的访问路径    不带参
	public String LOGIN_LAST_URL = "loginLastUrl";
	
	//登陆之前的访问路径 带参
	public String LOGIN_LAST_URL_PARAMS = "loginLastUrlParams";
	
	//前台登陆之前的访问路径    不带参
	public String BEFORE_LOGIN_LAST_URL = "beforeLoginLastUrl";
	
	//前台登陆之前的访问路径 带参
	public String BEFORE_LOGIN_LAST_URL_PARAMS = "beforeLoginLastUrlParams";
	
	/**
	 * 登录时用户不存在
	 */
	public String USER_LOGIN_USERNAMENOTHINGNESS = "1";
	
	/**
	 * 登录成功
	 */
	public String USER_LOGIN_SUCCESS = "2";
	
	/**
	 * 密码错误
	 */
	public String USER_LOGIN_USERNAMEORPWDNOT = "3";	
	
	/**
	 * 系统设置 站点设置  英文名称
	 */
	public String SYSTEM_SETTING_SITE_SETTING = "siteSetting";
	
	/**
	 * 站点设置 基本信息 英文名称
	 */
	public String SYSTEM_SETTING_BASIC_INFO = "basicInfo";

	/**
	 * 站点设置 基本信息 英文名称
	 */
	public String SYSTEM_SETTING_COMPANY_INFO = "companyInfo";
	
	/**
	 * 站点设置 轮播信息 英文名称
	 */
	public String SYSTEM_SETTING_FLASHVIEW = "flashview";
	
	/**
	 * 全局设置 登陆设置  英文名称
	 */
	public String SYSTEM_SETTING_LOGIN_SETTING = "loginSetting";
	
	/**
	 * 全局设置 会员设置  英文名称
	 */
	public String SYSTEM_SETTING_MEMBER_SETTING = "memberSetting";
	
	/**
	 * 全局设置 水印设置  英文名称
	 */
	public String SYSTEM_SETTING_WATERMARK_SETTING = "watermarkSetting";
	
	/**
	 * 全局设置 接口设置  英文名称
	 */
	public String SYSTEM_SETTING_PORT_SETTING = "portSetting";
	
	/**
	 * 全局设置 其他设置  英文名称
	 */
	public String SYSTEM_SETTING_OTHER_SETTING = "otherSetting";
	
	/**
	 * 全局设置 返回数据
	 */
	public String SYSTEM_DATA_RETURN = "returned";
	
	/**
	 * 文章设置 文章类型  英文名称
	 */
	public String ARTICLE_ARTICLE_TYPE = "articleType";
	
	/**图片格式：JPG*/
	public  String PICTRUE_FORMATE_JPG = "jpg";
	
	/**添加管理员账号默认密码*/
	public  String USER_SETTING_DEFAULT_PWD = "a123456";
	
	/**
	 * 在FTP中产品相关文件储存的根目录
	 */
	public String FTP_PRODUCTROOTDIR_NAME = "product";
	
	/**
	 * 在FTP中文章相关文件储存的根目录
	 */
	public String FTP_ARTICLEROOTDIR_NAME = "article";

	/**
	 * 在FTP中用户相关文件储存的根目录
	 */
	public String FTP_USERROOTDIR_NAME = "user";

	/**
	 * 在FTP中编辑器相关文件储存的根目录
	 */
	public String FTP_UEIDORROOTDIR_NAME = "uedit";
	
	/**
	 * FTP中上传的一些其他类型的文件（logo...）
	 */
	public String FTP_OTHERROOTDIR_NAME = "other";
	
	public String FTP_NOTRESOURCE_DEFAULT = "/res/jsycms/images/proimg_03.png";
	
	//------------------------------产品相关
	/**
	 * 产品基本信息图片类型 
	 */
	public String PRO_FILE_PROINFO = "productInfoImg";
	
	/**
	 * 产品基本信息  规格 类型
	 */
	public String PRO_FILE_SPEC = "productSpecImg";
	
	
	//------------------------------前台相关
	
	//关于我们
	public String ABOUT_US = "aboutUs";
	
	
	//联系方式
	public String CONTACT_WAY = "contactWay";
	
	// 公司介绍
	public String COMPANY_PROFILE = "companyprofile";
	// 公司文化
	public String COMPANY_CULTURE = "companyculture";
	// 合作品牌
	public String COOPERATION_BRAND = "cooperationbrand";
	//新闻中心
	public String NEWS_CENTER = "newsCenter";
	
	//------------------------------跳转状态
	
	/**
	 * 
	 */
	public String VIEW_FROS_TOUPDATE = "1";
	
	/**
	 * 
	 */
	public String VIEW_FROS_TOADD = "0";
	
	//前台导航
	public String WEB_NAV_LISTMAP = "navListMap";
	
	
	//推荐产品
	public Integer WEB_NAV_RECOMENDPRODUCT = 1;
	
	/*返回的map包含的数据*/
	public String RESULT_PRIMARYKEY = "primaryKey";
	
	public String RESULT_ROWS = "rows";
	
	public String RESULT_PAGEINDEX = "pageIndex";
	
	public String RESULT_PAGESIZE = "pageSize";
	
	public String RESULT_ORDERPARAM = "orderParam";
	
	public String RESULT_ORDERRANK = "orderRank";
	
	public String RESULT_ERRORCODE = "errorCode";
	
	public String RESULT_DATA = "list";
	
	public String RESULT_TOTAL = "total";
	
	public String RESULT_COUNTPAGE = "countpage";
	
	public String RESULT_FUZZY = "fuzzy_query";
	
	
}
