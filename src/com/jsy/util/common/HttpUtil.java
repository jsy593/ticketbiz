package com.jsy.util.common;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 封装了采用HttpClient发送HTTP请求的方法
 * @author	schoff
 */
public class HttpUtil {
	
	private static Logger log = Logger.getLogger(HttpUtil.class);
	private DefaultHttpClient httpClient = null;
	private String respContent = "通信失败"; //响应内容
	
	
	
	public HttpUtil() {}
	
	
	/**
	 * 实例化HTTPclient，配置参数
	 * @author	schoff
	 * @return void
	 */
	public void initHttpClient() {
		httpClient = new DefaultHttpClient();//创建默认的httpClient实例
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); //连接超时10s
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);         //读取超时20s
	}
	
	       
    /**
     * 发送HTTP_GET请求
     * @author	schoff
     * @param url 		请求链接，包含条件
     * @return Map<String, Object> 	远程主机响应正文[respContent]和session[cookie]
     */
    public Map<String, Object> sendGetRequest(String url){
    	initHttpClient(); // 创建httpClient并设置参数
    	Map<String, Object> map = new HashMap<String, Object>();
    	
        HttpGet httpGet = new HttpGet(url); 
        try{
            HttpResponse response = httpClient.execute(httpGet); //执行GET请求
            HttpEntity entity = response.getEntity(); //获取响应实体 
            if(entity != null){ 
                Charset respCharset = ContentType.getOrDefault(entity).getCharset(); // 获取响应码
                respContent = EntityUtils.toString(entity, respCharset); 
                map.put("respContent", respContent);
                EntityUtils.consume(entity); // 销毁，释放内存
            }
            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            if (!cookies.isEmpty()) {
            	for (int i = 0; i < cookies.size(); i++) {
            		System.out.println(cookies.get(i));
            		if (cookies.get(i).getName().equals("JSESSIONID")) {
						map.put("cookie", cookies.get(i));
						break;
					}
            	}
			}
        } catch (ConnectTimeoutException cte){ 
            log.error("请求通信[" + url + "]时连接超时,堆栈轨迹如下", cte); 
        } catch (SocketTimeoutException ste){ 
            log.error("请求通信[" + url + "]时读取超时,堆栈轨迹如下", ste); 
        }catch(ClientProtocolException cpe){ 
            log.error("请求通信[" + url + "]时协议异常,堆栈轨迹如下", cpe); 
        }catch(ParseException pe){ 
            log.error("请求通信[" + url + "]时解析异常,堆栈轨迹如下", pe); 
        }catch(IOException ioe){ 
            log.error("请求通信[" + url + "]时网络异常,堆栈轨迹如下", ioe); 
        }catch (Exception e){ 
            log.error("请求通信[" + url + "]时偶遇异常,堆栈轨迹如下", e); 
        }finally{ 
            /**
             * 关闭连接,释放资源 
             */
            httpClient.getConnectionManager().shutdown(); 
        } 
        return map; 
    } 
       
       
    /**
     * 发送HTTP_POST请求
     * @author	schoff
     * @param url			请求地址
     * @param data       	请求参数,若有多个参数则应拼接为param 11=value11&22=value22&33=value33的形式
     * @param encodeCharset 编码字符集,编码请求数据时用,此参数为必填项(不能为""或null)
     * @return Map<String, Object>		远程主机响应正文[respContent]和session[cookie]
     */
    public Map<String, Object> sendPostRequest(String url, String data, String encodeCharset){
    	initHttpClient();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
        HttpPost httpPost = new HttpPost(url); 
        //手工指定CONTENT_TYPE头消息，编码
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + encodeCharset);
		try{ 
            httpPost.setEntity(new StringEntity(data == null ? "" : data, encodeCharset)); 
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                respContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                map.put("respContent", respContent);
                EntityUtils.consume(entity); 
            } 
            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            if (!cookies.isEmpty()) {
            	for (int i = 0; i < cookies.size(); i++) {
            		System.out.println(cookies.get(i));
            		if (cookies.get(i).getName().equals("JSESSIONID")) {
						map.put("cookie", cookies.get(i));
						break;
					}
            	}
			}
        } catch (ConnectTimeoutException cte){ 
            log.error("请求通信[" + url + "]时连接超时,堆栈轨迹如下", cte); 
        } catch (SocketTimeoutException ste){ 
            log.error("请求通信[" + url + "]时读取超时,堆栈轨迹如下", ste); 
        }catch(Exception e){ 
            log.error("请求通信[" + url + "]时偶遇异常,堆栈轨迹如下", e); 
        }finally{ 
            httpClient.getConnectionManager().shutdown(); 
        } 
        return map; 
    }
       
    /**
     * 发送HTTP_ENTITY_POST请求
     * @author	schoff
     * @param url			请求地址
     * @param params       	请求参数 Map<String,String>
     * @param encodeCharset 编码字符集,编码请求数据时用,此参数为必填项(不能为""或null)
     * @return Map<String, Object>		远程主机响应正文[respContent]和session[cookie]
     */
    public Map<String, Object> sendPostMapRequest(String url,Map<String, String> params, String encodeCharset){
    	initHttpClient();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	HttpPost httpPost = new HttpPost(url); 
    	//手工指定CONTENT_TYPE头消息，编码
    	httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + encodeCharset);
    	List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    	
    	//构建POST请求的表单参数
        if(null != params){
            for(Map.Entry<String,String> entry : params.entrySet()){
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
    	
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                respContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                map.put("respContent", respContent);
                EntityUtils.consume(entity); 
            } 
            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            if (!cookies.isEmpty()) {
            	for (int i = 0; i < cookies.size(); i++) {
            		System.out.println(cookies.get(i));
            		if (cookies.get(i).getName().equals("JSESSIONID")) {
						map.put("cookie", cookies.get(i));
						break;
					}
            	}
			}
        } catch (ConnectTimeoutException cte){ 
            log.error("请求通信[" + url + "]时连接超时,堆栈轨迹如下", cte); 
        } catch (SocketTimeoutException ste){ 
            log.error("请求通信[" + url + "]时读取超时,堆栈轨迹如下", ste); 
        }catch(Exception e){ 
            log.error("请求通信[" + url + "]时偶遇异常,堆栈轨迹如下", e); 
        }finally{ 
            httpClient.getConnectionManager().shutdown(); 
        } 
        return map; 
    }
       
    /**
     * 发送HTTP_POST_SSL请求
     * @param url			请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,当其为null时,则取HttpClient内部默认的ISO-8859-1编码请求参数
     * @return Map<String, Object>		远程主机响应正文[respContent]和session[cookie]
     */ 
    public Map<String, Object> sendPostSSLRequest(String url, Map<String, String> params, String encodeCharset){
    	initHttpClient();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
        //解决javax.net.ssl.SSLPeerUnverifiedException
        X509TrustManager trustManager = new X509TrustManager(){
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            @Override
            public X509Certificate[] getAcceptedIssuers() {return null;}
        };
        
        //用于解决javax.net.ssl.SSLException
        X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier(){
            @Override
            public void verify(String host, SSLSocket ssl) throws IOException {}
            @Override
            public void verify(String host, X509Certificate cert) throws SSLException {}
            @Override
            public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {}
            @Override
            public boolean verify(String arg0, SSLSession arg1) {return true;}
        };
        
        try {
            SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);
            //初始化该上下文
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            //创建SSLSocketFactory
            SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, hostnameVerifier);
            //通过SchemeRegistry将SSLSocketFactory注册到HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
            //创建HttpPost
            HttpPost httpPost = new HttpPost(url);
            //构建POST请求的表单参数
            if(null != params){
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for(Map.Entry<String,String> entry : params.entrySet()){
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
            }
            
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                respContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                map.put("respContent", respContent);
                EntityUtils.consume(entity);
            }
            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            if (!cookies.isEmpty()) {
            	for (int i = 0; i < cookies.size(); i++) {
            		System.out.println(cookies.get(i));
            		if (cookies.get(i).getName().equals("JSESSIONID")) {
						map.put("cookie", cookies.get(i));
						break;
					}
            	}
			}
        } catch (ConnectTimeoutException cte){
            log.error("请求通信[" + url + "]时连接超时,堆栈轨迹如下", cte);
        } catch (SocketTimeoutException ste){
            log.error("请求通信[" + url + "]时读取超时,堆栈轨迹如下", ste);
        } catch (Exception e) {
            log.error("请求通信[" + url + "]时偶遇异常,堆栈轨迹如下", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return map;
    }
	
    /**
     * 上传文件
     * @author	schoff
     * @param	url				请求地址
     * @param	file			文件File
     * @param	fileName		对方需要的file名字
     * @param	encodeCharset	编码
     * @return String			对方返回的消息
     */
    public String uploadFile(String url, File file ,String fileName, String encodeCharset) {
    	initHttpClient();
		HashMap<String, Object> params = new HashMap<String, Object>();
		MultipartEntity entity = new MultipartEntity();
		/**
		  * params 参数
		  */
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> mapEntry : params.entrySet()) {
				try {
					StringBody stringBody = new StringBody(mapEntry.getValue().toString());
					entity.addPart(mapEntry.getKey(), stringBody);
				} catch (UnsupportedEncodingException e) {
					log.error("获取params中[" + mapEntry.getKey() + "]值错误");
				}
			}
		}
		
		/**
		 * 文件
		 */
		if (file.exists()) {
			FileBody fileBody = new FileBody(file);
			entity.addPart(fileName, fileBody);
		}
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
            if (null != resEntity) {
                respContent = EntityUtils.toString(resEntity, ContentType.getOrDefault(resEntity).getCharset());
                EntityUtils.consume(resEntity);
            }
		} catch (IOException e) {
			log.error("请求通信[" + url + "]时偶遇异常,堆栈轨迹如下", e);
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
    	return respContent;
	}
}
