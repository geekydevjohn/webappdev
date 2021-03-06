package com.trumpia.trumpia.services;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;




public class TrumpiaAPILibrary {	
	/* 
	 * CheckList : HOW TO SEND ERR MSG - IO Exception..
	 * 			In case of UnsuccessfulRequestException()..
	 */
	
	public static JSONObject deleteSubscriptionInfo(String subscriptionId, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/subscription/"+ subscriptionId, trumpia);
			response = request.delete();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();		
		}
		
		return new JSONObject(response);
	}
	
	public static JSONObject putNewSubscriptionInfo(String subscriptionsBody, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithResponseBody(subscriptionsBody, "/subscription", trumpia);
			response = request.put();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		JSONObject a = new JSONObject(response);
		return new JSONObject(response);
	}

	public static JSONObject postChangedSubscriptionInfo(String subscriptionsBody, String subscriptionId, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithResponseBody(subscriptionsBody, "/subscription/" + subscriptionId, trumpia);
			response = request.post();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return new JSONObject(response);
	}
	/*
	 * Search only for contact Info
	 * */

	public static JSONObject searchSubscriptionByEmail(String email, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/subscription/search?search_type=1&search_data=" + email, trumpia);
			response = request.get();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}

		return new JSONObject(response);

	}

	public static JSONObject searchSubscriptionByMobile(String mobile, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/subscription/search?search_type=2&search_data=" + mobile, trumpia);
			response = request.get();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return new JSONObject(response);
	}

	public static JSONObject searchSubscriptionByLandline(String landline, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/subscription/search?search_type=3&search_data=" + landline, trumpia);
			response = request.get();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return new JSONObject(response);
	}
	
	public static String getStatusReport(String requestId, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/report/" + requestId, trumpia);
			response = request.get();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return response;
	}
	
	public static JSONObject searchSubscriptionBySubsId(String subscriptionId, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/subscription/" + subscriptionId , trumpia);
			response = request.get();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return new JSONObject(response);
	}
	
	public static boolean validCheck(TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/balance/credit", trumpia);
			response = request.get();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject(response);
		if(json.getString("status_code").equals("MPCE0000"))
			return true;
		else
			return false;
	}
	
	public static JSONObject setList(String requestBody, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithResponseBody(requestBody, "/list", trumpia);
			response = request.put();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject(response);
		return json;
	}
	
	public static JSONObject deleteList(String id, TrumpiaAccountEntity trumpia) {
		String response = null;
		try {
			HttpRequest request = createRequestWithoutResponseBody("/list/" + id, trumpia);
			response = request.delete();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject(response);
		return json;
	}

	////

	private static HttpRequest createRequestWithResponseBody(String requestBody, String url, TrumpiaAccountEntity trumpia) throws JsonProcessingException {
		String apikey = trumpia.getApikey();
		String userId = trumpia.getUniqueId();
		String baseUrl = trumpia.getBaseURL();

		RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json");
		headers.put("x-apikey", apikey);

		HttpRequest request;
		request = new HttpRequest.Builder()
				.URL(baseUrl+ "/rest/v1/" + userId + url)
				.headers(headers)
				.setRawBody(body)
				.build();

		return request;
	}

	private static HttpRequest createRequestWithoutResponseBody(String url, TrumpiaAccountEntity trumpia) throws JsonProcessingException {
		String apikey = trumpia.getApikey();
		String userId = trumpia.getUniqueId();
		String baseUrl = trumpia.getBaseURL();

		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json");
		headers.put("x-apikey", apikey);

		HttpRequest request;
		request = new HttpRequest.Builder()
				.URL(baseUrl+ "/rest/v1/" + userId + url)
				.headers(headers)
				.build();

		return request;
	}

}
