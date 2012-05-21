package com.zyj.qiqile.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpConnectionHelper {
	private static HttpConnectionHelper instance;
	private static String TAG="HttpConnectionHelper";

	public static HttpConnectionHelper getInstance() {
		if (instance == null) {
			instance = new HttpConnectionHelper();
		}
		return instance;
	}

	/**
	 * http����ͨ��
	 * 
	 * @param httpurl
	 * @return
	 */
	private HttpURLConnection getHttpConnection(String httpurl) {
		HttpURLConnection con = null;
		try {
			URL url = new URL(httpurl);
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);// ʹ�� URL ���ӽ������
			con.setDoInput(true);// ʹ�� URL ���ӽ�������
			con.setUseCaches(false);// ���Ի���
			con.setRequestMethod("POST");// ����URL���󷽷�
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-type", "application/json");
			con.connect();
			con.setConnectTimeout(1);
			con.setReadTimeout(1);
		} catch (IOException e1) {
			Log.e(TAG,"HttpConManager getJsonData IOE", e1);
		} catch (Exception e2) {
			Log.e(TAG,"HttpConManager getJsonData E", e2);
		}
		return con;
	}

	/**
	 * �ͻ��˴ӷ���˽���json��ʽ����
	 * 
	 * @param httpurl
	 * @return
	 */
	public JSONObject getJsonData(String httpurl) {
		JSONObject jsonData = null;
		try {
			HttpURLConnection con = getHttpConnection(httpurl);
			int rescode = con.getResponseCode();
			if (HttpURLConnection.HTTP_OK == rescode) {
				InputStream input = con.getInputStream();
				int inputLength = input.available();
				byte[] buffer = new byte[inputLength];
				int offset = 0;
				int length = 0;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				while ((offset = input.read(buffer, 0, inputLength)) != -1)
					length += offset;
				output.write(buffer, 0, length);
				String str = new String(output.toByteArray());
				jsonData = new JSONObject(str);
				output.close();
				input.close();
				buffer = null;
				con.disconnect();
			}
		} catch (IOException e1) {
			Log.e(TAG,"HttpConManager getJsonData IOE", e1);
		} catch (JSONException e2) {
			Log.e(TAG,"HttpConManager getJsonData JSONE",  e2);
		} catch (Exception e3) {
			Log.e(TAG,"HttpConManager getJsonData E", e3);
		}
		return jsonData;
	}

	/**
	 * �ͻ��˴ӷ���˽���String��������
	 * 
	 * @param httpurl
	 * @return
	 */
	public String getStringData(String httpurl) {
		String value = "";
		try {
			HttpURLConnection con = getHttpConnection(httpurl);
			int rescode = con.getResponseCode();
			if (HttpURLConnection.HTTP_OK == rescode) {
				InputStream input = con.getInputStream();
				int inputLength = input.available();
				byte[] buffer = new byte[inputLength];
				int offset = 0;
				int length = 0;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				while ((offset = input.read(buffer, 0, inputLength)) != -1)
					length += offset;
				output.write(buffer, 0, length);
				value = new String(output.toByteArray());
				output.close();
				input.close();
				buffer = null;
				con.disconnect();
			}
		} catch (IOException e1) {
			Log.e(TAG,"HttpConManager getStringData IOE", e1);
		} catch (Exception e2) {
			Log.e(TAG,"HttpConManager getStringData E", e2);
		}
		return value;
	}

	/**
	 * �ͻ��˷������ݸ������
	 * 
	 * @param httpurl
	 * @param strkey
	 * @param strValue
	 *            StringBuffer����
	 */
	public void sendPostData(String httpurl, String strkey,
			StringBuffer strValue) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(httpurl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(strkey, strValue
					.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ͻ��˷������ݸ������
	 * 
	 * @param httpurl
	 * @param strkey
	 * @param strValue
	 *            �ַ���
	 */
	public void sendPostData(String httpurl, String strkey, String strValue) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(httpurl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(strkey, strValue));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * postͨ�ŷ�ʽ���ҷ���JSON��ʽ������
	 * 
	 * @param httpUrl
	 *            ���ʵ�URL��ַ
	 * @param strKey
	 *            �൱��&key=�е�key
	 * @param strValue
	 *            StringBuffer���͵Ĳ���
	 * @return
	 */
	public JSONObject sendPostDataAndResult(String httpUrl, String strKey,
			StringBuffer strValue) {
		JSONObject jsonObj = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(httpUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(strKey, strValue
					.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String str = EntityUtils.toString(httpEntity);
			try {
				jsonObj = new JSONObject(str);
			} catch (JSONException e) {
				Log.e(TAG,"sendPostDataAndResult", e);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	/***
	 * postͨ�ŷ�ʽ���ҷ���JSON��ʽ������
	 * 
	 * @param httpUrl
	 *            ���ʵ�URL��ַ
	 * @param strKey
	 *            �൱��&key=�е�key
	 * @param strValue
	 *            String���͵Ĳ���
	 * @return
	 */
	public JSONObject sendPostDataAndResult(String httpUrl, String strKey,
			String strValue) {
		JSONObject jsonObj = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(httpUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(strKey, strValue));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String str = EntityUtils.toString(httpEntity);
			try {
				jsonObj = new JSONObject(str);
			} catch (JSONException e) {
				Log.e(TAG,"sendPostDataAndResult", e);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	/***
	 * postͨ�ŷ�ʽ���ҷ���JSON��ʽ������
	 * 
	 * @param httpUrl ���ʵ�URL��ַ
	 * @param params ����
	 * @return
	 * @throws HttpException 
	 */
	public JSONObject sendPostDataAndResult(String httpUrl, Map<String,String> params) throws HttpException {
		JSONObject jsonObj = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(httpUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if(params!=null&&params.size()>0){
				for(Entry<String,String> entry:params.entrySet()){
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String str = EntityUtils.toString(httpEntity);
			try {
				jsonObj = new JSONObject(str);
			} catch (JSONException e) {
				Log.e(TAG,"sendPostDataAndResult", e);
				throw new HttpException(TAG+",sendPostDataAndResult", e);
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG,"sendPostDataAndResult", e);
			throw new HttpException(TAG+",sendPostDataAndResult", e);
		} catch (IOException e) {
			Log.e(TAG,"sendPostDataAndResult", e);
			throw new HttpException(TAG+",sendPostDataAndResult", e);
		}
		return jsonObj;
	}
	
	/**
	 * ��������Ƿ����
	 * 
	 * @return
	 */
	public boolean checkNetwork() {
//		HttpURLConnection con = getHttpconnect(StoreConstUtils.Store_Url_Domain);
//		try {
//			int rescode = con.getResponseCode();
//			if (HttpURLConnection.HTTP_OK == rescode)
//				return true;
//			else
//				return false;
//		} catch (IOException e) {
//			Log.e("checkNetwork", 4, e);
//		}
		return false;
	}

}
