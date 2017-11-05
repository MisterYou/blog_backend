package com.youjiniot.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpClient访问webservice,适用于soap1.1版本
 * 
 *
 */
 @SuppressWarnings("all")
public class HttpClient1Util {

	private static final HttpClient1Util single = new HttpClient1Util();

	private HttpClient1Util() {

	}

	public static HttpClient1Util getInstance() {
		return single;
	}

	HttpClient httpclient = new DefaultHttpClient();

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public String post(String url, String respEncoding) {
		return post(url, "UTF-8", respEncoding, new ArrayList<NameValuePair>());
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public String post(String url, String reqEncoding, String respEncoding, List<NameValuePair> param) {
		String resStr = "";
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = param;

		UrlEncodedFormEntity uefEntity;

		try {
			uefEntity = new UrlEncodedFormEntity(formparams, reqEncoding);
			httppost.setEntity(uefEntity);
			HttpResponse response;
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resStr = EntityUtils.toString(entity, respEncoding);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// httpclient.getConnectionManager().shutdown();
		}
		return resStr;
	}

	/**
	 * HttpClient发送GET请求
	 * @param url
	 *            GET请求url(包含参数)
	 * @return 响应字符串
	 */
	public String get(String url) {
		// httpclient = new DefaultHttpClient();
		String resStr = "";
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			HttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			// System.out.println(response.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
				// System.out.println("Response content length: "
				// + entity.getContentLength());
				// 打印响应内容
				// System.out.println("Response content: "
				// + EntityUtils.toString(entity));
				resStr = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// httpclient.getConnectionManager().shutdown();
		}
		return resStr;
	}


}