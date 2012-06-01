package com.zyj.qiqile.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

public class LocationHelper {
	public final static String TAG = "LocationHelper";

	public final static String URL = "http://map.google.com/maps/geo?";
	public final static String STATUS = "Status";
	public final static String CODE = "code";
	public final static String PLACKMARK = "Placemark";
	public final static String ID = "id";
	public final static String ADDRESS = "Address";
	public final static String ADDRESS_DETAILS = "AddressDetails";
	public final static String COUNTRY = "Country";
	public final static String COUNTRY_NAME = "CountryName";
	public final static String COUNTRY_NAME_CODE = "CountryNameCode";
	public final static String ADMINISTATIVEAREA = "AdministrativeArea";
	public final static String LOCALITY = "Locality";
	public final static String LOCALITY_NAME = "LocalityName";
	public final static String DEPENDENT_LOCALITY = "DependentLocality";
	public final static String ADDRESSLINE = "AddressLine";
	public final static String DEPENDENT_LOCALITY_NAME = "DependentLocalityName";

	public final static double MILE_TO_KILOMETER = 1.609344;

	public static Location getCurrentLocation(Context context) {
		Location location = null;
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (lm != null) {
//			List<String> lp = lm.getAllProviders();
//			for (String item : lp) {
//				Log.i("8023", "����λ�÷���" + item);
//			}
			Criteria criteria = new Criteria();
			criteria.setCostAllowed(false);
			// ����λ�÷������
			criteria.setAccuracy(Criteria.ACCURACY_COARSE); // ����ˮƽλ�þ���
			// getBestProvider ֻ��������ʵ��û��λ�ù�Ӧ�̽�������
			String providerName = lm.getBestProvider(criteria, true);
			Log.i("8023", "------λ�÷���" + providerName);
			if (providerName != null) {
				location = lm.getLastKnownLocation(providerName);
			}
		}
		return location;
	}

	public static String getCurrentCity(Context context) {
		String city = "������";
		Location location = getCurrentLocation(context);
		if (location != null) {
			// ��ȡά����Ϣ
			double latitude = location.getLatitude();
			// ��ȡ������Ϣ
			double longitude = location.getLongitude();
			try {
				city = LocationHelper.getCity(String.valueOf(latitude),
						String.valueOf(longitude));
			} catch (JSONException e) {
				Log.e(TAG, "", e);
			}
		}
		return city;
	}

	public static JSONObject getLocation(String latitude, String longitude) {
		String url = String.format(URL + "output=json&q=%s,%s", latitude,
				longitude);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse httpResponse;
		JSONObject jsonObject = null;
		try {
			httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String str = EntityUtils.toString(httpEntity);
			str = str.replace("\n", "");
			str = str.replace("  ", "");
			jsonObject = new JSONObject(str);
		} catch (ClientProtocolException e1) {
			Log.e(TAG, "getLocation", e1);
		} catch (IOException e1) {
			Log.e(TAG, "getLocation", e1);
		} catch (JSONException e) {
			Log.e(TAG, "getLocation", e);
		}
		return jsonObject;
	}

	public static String getCity(String latitude, String longitude)
			throws JSONException {
		String result = null;
		JSONObject json = getLocation(latitude, longitude);
		if (json != null && !json.isNull(STATUS)) {
			if (!json.getJSONObject(STATUS).isNull(CODE)
					&& json.getJSONObject(STATUS).getInt(CODE) == 200) {
				result = json.getJSONArray(PLACKMARK).getJSONObject(0)
						.getJSONObject(ADDRESS_DETAILS).getJSONObject(COUNTRY)
						.getJSONObject(ADMINISTATIVEAREA)
						.getJSONObject(LOCALITY).getString(LOCALITY_NAME);
			}
		}
		return result;
	}

	// TODO дһ���������ж����ķ���

	public static String getAddr(String latitude, String longitude) {
		String addr = "";
		/*
		 * Ҳ������http://maps.google.cn/maps/geo?output=csv&q=%s,%s��
		 * 
		 * output=csv,Ҳ������xml��json������ʹ��csv���ص��������෽�����
		 */
		String url = String.format(URL + "output=csv&q=%s,%s", latitude,
				longitude);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				InputStreamReader insr = new InputStreamReader(
						httpsConn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(insr);
				String data = null;
				if ((data = br.readLine()) != null) {
					String[] retList = data.split(",");
					if (retList.length > 2 && ("200".equals(retList[0]))) {
						addr = retList[2];
					} else {
						addr = "";
					}
				}
				insr.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
		return addr;
	}

	/** ����������룬��λ���� */
	public static double getDistance(double lat1, double lon1, double lat2,
			double lon2) {
		float[] results = new float[1];
		Location.distanceBetween(lat1, lon1, lat2, lon2, results);
		return results[0];
	}
}
