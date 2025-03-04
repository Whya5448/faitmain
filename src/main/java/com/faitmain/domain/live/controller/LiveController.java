package com.faitmain.domain.live.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveProduct;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.domain.LiveUserStatus;
import com.faitmain.domain.live.service.LiveService;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.service.ProductService;
import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.service.UserSerivce;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/live/")
public class LiveController {

	// Field
	@Autowired
	@Qualifier("liveServiceImpl")
	private LiveService liveService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserSerivce userSerivce;

	@GetMapping("liveRoom")
	public String getLiveRoomList(Model model) throws Exception {

		System.out.println("/live/getLiveRoomList : GET start...");
		log.info("Controller = {} ", "/live/liveRoomList : GET start...");

		log.info("getRoomsList = {} ", this.getClass());

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/rooms");

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setRequestMethod("GET");

		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN",
				"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ2Y3h6dmN4ejE1OUBnbWFpbC5jb20iLCJleHAiOjE2NTUxMjE4NzcsImlhdCI6MTY1NTEwMzg3NywiYXV0aG9yaXRpZXMiOiJbUk9MRV9VU0VSXSJ9.wHNAWfW1N54JUu6YMPvCqYzHALvMbs1QmLdEHQ7NW_I");
		conn.setDoOutput(true);

		// 데이터 입력 스트림에 답기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		JSONArray data = (JSONArray) result.get("list");
		JSONObject tmp;
		for (int i = 0; i < data.size(); i++) {
			tmp = (JSONObject) data.get(i);
			System.out.println("data[" + i + "] : " + tmp);
		}
		System.out.println("data : " + data);
		model.addAttribute("json", data);

		log.info("Controller = {} ", "/live/liveRoomList : GET end...");

		return "/live/liveList";
	}

	// 토큰 발급 메서드
	public String getToken() throws Exception {

		log.info("getToken Method start...");

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/token");

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());

		conn.setRequestMethod("GET");
		conn.setRequestProperty("API_KEY", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");

		// 데이터 입력 스트림에 담기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		log.info("data from v.chatServer : {}", br);

		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		JSONObject data = (JSONObject) result.get("data");
		String dataa = (String) data.get("X-AUTH-TOKEN");
		long Code = (long) result.get("result_cd");

		log.info("X-AUTH-TOKEN : {}", dataa);
		log.info("result_cd : {}", Code);
		log.info("getToken Method end...");

		return dataa;
	}

	// 방송 시작
	@PostMapping("create")
	public String createRoom(HttpServletRequest req, @RequestParam("roomName") String liveTitle, HttpSession session,
			Model model) throws Exception {

		log.info("createRoom = {} ", this.getClass());

		String token = getToken();

		User user = (User) session.getAttribute("user");

		Live validation = liveService.getLiveByStoreId(user.getId());

		String[] liveProducts = req.getParameterValues("liveProduct");

		System.out.println(liveTitle);

		for (String product : liveProducts) {
			log.info("liveProduct = {}", product);
		}

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		validation = liveService.getLiveByStoreId(user.getId());

		if (validation == null) {

			TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustCerts, new java.security.SecureRandom());

			URL url = new URL("https://vchatcloud.com/openapi/v1/rooms");

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");

			conn.setRequestProperty("X-AUTH-TOKEN", token);
			conn.setDoOutput(true);

			String Data = "roomName=" + liveTitle + "&maxUser=5&webrtc=91";

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(Data);
			wr.flush();

			// 데이터 입력 스트림에 담기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while (br.ready()) {
				sb.append(br.readLine());
			}
			conn.disconnect();

			result = (JSONObject) new JSONParser().parse(sb.toString());

			// REST API 호출 상태 출력하기
			StringBuilder out = new StringBuilder();
			out.append(result.get("status") + " : " + result.get("status_message") + "\n");
			log.info("status / status_message : {}", out);

			// JSON데이터에서 "data"라는 JSONObject를 가져온다.
			JSONObject data = (JSONObject) result.get("data");
			String roomId = (String) data.get("roomId");
			long Code = (long) result.get("result_cd");

			log.info("roomId = {}", roomId);
			log.info("result_cd = {}", Code);

			if (Code != 1) {
				log.info("에러 발생! result_cd : {}", Code);
				
			} else {

				// 라이브 방송 등록 후 DB에 데이터 입력
				// 라이브

				Live live = new Live();

				live.setRoomId(roomId);
				live.setStoreId(user.getId());
				live.setLiveTitle(liveTitle);
				live.setLiveIntro(liveTitle);
				live.setLiveImage("라이브 대표사진.png");

				liveService.addLive(live);
				
				model.addAttribute("Live", live);

				System.out.println("라이브 방송 정보 : "
						+ liveService.getLive(liveService.getLiveByStoreId(user.getId()).getLiveNumber()));
				live = null;

				for (String product : liveProducts) {
					System.out.println(product);
				}

				// 라이브 판매 상품
				live = liveService.getLiveByStoreId(user.getId());

				LiveProduct liveProduct = new LiveProduct();

				for (String product : liveProducts) {
					liveProduct.setLiveNumber(live.getLiveNumber());
					liveProduct.setLiveReservationNumber(0);
					liveProduct.setProductNumber(Integer.parseInt(product));
					liveProduct.setProductMainImage(
							productService.getProduct(Integer.parseInt(product)).getProductMainImage());
					liveProduct.setProductName(productService.getProduct(Integer.parseInt(product)).getProductName());
					liveProduct
							.setProductDetail(productService.getProduct(Integer.parseInt(product)).getProductDetail());
					liveService.addLiveProduct(liveProduct);
				}
			}
			
		} else {

			log.info("room already exist");

			editRoom(req, liveTitle, session, token, model);
		}
		List<LiveProduct> list = liveService
				.getLiveProductListByLiveNumber(liveService.getLiveByStoreId(user.getId()).getLiveNumber());
		model.addAttribute("listProduct", list);

		String roomId = liveService.getLiveByStoreId(user.getId()).getRoomId();

		log.info("채널키 파라미터 체크 {} : ", roomId);

		model.addAttribute("channelKey", roomId);

		log.info("model status : " + model);

		return "/live/live";

	}

	// 방송 정보 수정

	public String editRoom(HttpServletRequest req, String liveTitle, HttpSession session, String token, Model model)

			throws Exception {

		log.info("editRoom = {} ", this.getClass());
		System.out.println("방송 정보 수정");

		User user = (User) session.getAttribute("user");

		Live live = liveService.getLiveByStoreId(user.getId());

		String[] liveProducts = req.getParameterValues("liveProduct");

		System.out.println(liveTitle);

		for (String product : liveProducts) {
			log.info("product : {}", product);
		}

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/rooms/" + live.getRoomId());

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());

		conn.setRequestMethod("POST");

		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", token);
		conn.setDoOutput(true);

		String Data = "maxUser=5&roomName=" + liveTitle + "&roomStatus=A&webrtc=91";

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(Data);
		wr.flush();

		// 데이터 입력 스트림에 담기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();
		System.out.println("br" + br);
		System.out.println("wr" + wr);
		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		Long data = (Long) result.get("result_cd");
		System.out.println(data);

		if (data == 1) {

			// 라이브 방송 등록 후 DB에 데이터 입력
			// 라이브

			live = new Live();

			live.setLiveNumber((liveService.getLiveByStoreId(user.getId())).getLiveNumber());
			live.setLiveTitle(liveTitle);
			live.setLiveIntro(liveTitle);
			live.setLiveStatus(true);

			liveService.updateLive(live);
			
			model.addAttribute("Live", live);

			System.out.println(
					"라이브 방송 정보 : " + liveService.getLive(liveService.getLiveByStoreId(user.getId()).getLiveNumber()));

			live = new Live();

//          for(String product : liveProducts) {
//             System.out.println(product);
//             }

			// 라이브 판매 상품

			live = liveService.getLiveByStoreId(user.getId());

			liveService.deleteLiveProduct(live.getLiveNumber());

			LiveProduct liveProduct = new LiveProduct();

			for (String product : liveProducts) {
				liveProduct.setLiveNumber(live.getLiveNumber());
				liveProduct.setLiveReservationNumber(0);
				liveProduct.setProductNumber(Integer.parseInt(product));
				liveProduct.setProductMainImage(
						productService.getProduct(Integer.parseInt(product)).getProductMainImage());
				liveProduct.setProductName(productService.getProduct(Integer.parseInt(product)).getProductName());
				liveProduct.setProductDetail(productService.getProduct(Integer.parseInt(product)).getProductDetail());
				liveService.addLiveProduct(liveProduct);
			}
		} else {
			System.out.println("오류남");
		}

		String roomId = liveService.getLiveByStoreId(user.getId()).getRoomId();

		log.info("채널키 파라미터 체크 = {}", roomId);

		model.addAttribute("channelKey", roomId);

		return "live/live";

	}

	@GetMapping("addLiveView")
	public String addLiveView(HttpSession session, Model model) throws Exception {

		System.out.println("/live/addLiveView : GET start...");
		log.info("Controller = {} ", "/live/addLiveView : GET start...");

		User user = (User) session.getAttribute("user");

		Map<String, Object> map = productService.getProductListByStoreId(user.getId());

		model.addAttribute("map", map);

		System.out.println(map);

		log.info("Controller = {} ", "/live/addLiveView : GET end...");

		return "live/addLiveView";
	}

	@GetMapping("addLive")
	public String addLive() throws Exception {

		System.out.println("/live/addLive : GET start...");
		log.info("Controller = {} ", "/live/addLive : GET start...");

		log.info("Controller = {} ", "/live/addLiveView : GET end...");

		return "/live/addLive";
	}

	@GetMapping("watchLive/{liveNumber}")
	public String watchLive(Model model, @PathVariable int liveNumber) throws Exception {
		log.info("watchLive() : GET start...");

		Live live = liveService.getLive(liveNumber);

		List<LiveProduct> list = liveService.getLiveProductListByLiveNumber(live.getLiveNumber());

		model.addAttribute("listProduct", list);
		model.addAttribute("live", live);

		log.info("model live : " + model.getAttribute("live"));
		return "live/watchLive";
	}

	@GetMapping("addLiveUserStatus")
	public void addLiveUserStatus(@ModelAttribute("liveUsrStatus") LiveUserStatus liveUserStatus) throws Exception {

		System.out.println("/live/addSanctionUser : GET start...");

		liveService.addLiveUserStatus(liveUserStatus);

		System.out.println("/live/addSanctionUser : GET end...");

	}

	@GetMapping("getLiveReservationCal")
	public String getLiveReservationCal() {
		log.info("getLiveReservationCal() : GET start... ");

		// 단순 네비게이션

		log.info("getLiveReservationCal() : GET end... ");
		return "live/liveReservationCal";
	}

	@GetMapping("getLiveReservationList")
	public String getLiveReservationList(@RequestParam String date, Model model) throws Exception {
		log.info("getLiveReservationList() : GET start... ");

		log.info("date : " + date);

		List<LiveReservation> list = liveService.getLiveReservationList(date);
		log.info("list : {}", list);
		List<LiveReservation> resultList = new ArrayList<LiveReservation>();

		for (LiveReservation obj : list) {
			obj.setLiveProduct(liveService.getLiveProductList(obj.getLiveReservationNumber()));
			obj.setStore(userSerivce.getUser(obj.getStore().getId()));

			resultList.add(obj);
		}

		// 결과 확인용 for문
		for (LiveReservation obj : resultList) {
			log.info("resultList : {}", obj);
		}

		model.addAttribute("list", resultList);

		log.info("getLiveReservationList() : GET end... ");
		return "/live/liveReservationList";
	}

	@GetMapping("liveManageTab")
	public String getLiveUserList( HttpServletRequest req, HttpSession session,  Model model ) throws Exception {

		log.info("Controller = {} ", "/live/getLiveUserList : GET start...");

		log.info("getLiveUserList = {} ", this.getClass());
		
		User user = (User) session.getAttribute("user");
		
		
		

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/users/" + liveService.getLiveByStoreId(user.getId()).getRoomId());

		System.out.println("유우우우우우우우우ㅏㄹ엘    " + url);

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("API_KEY", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", getToken());
		conn.setDoOutput(true);

		// 데이터 입력 스트림에 답기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		JSONArray data = (JSONArray) result.get("list");
		System.out.println("옴뇸뇸" + data);
		JSONObject tmp;
		for (int i = 0; i < data.size(); i++) {
			tmp = (JSONObject) data.get(i);
			System.out.println("data[" + i + "] : " + tmp);
		}
		System.out.println("data : " + data);
		
		model.addAttribute("userList", data);
		
		log.info("Controller = {} ", "/live/getLiveUserList : GET end...");
		
		return "/live/liveManageTab";
	}

	@GetMapping("liveStatusUpdate")
	public RedirectView liveStatusUpdate(HttpSession session) throws Exception {

		Live live = liveService.getLiveByStoreId(((User) session.getAttribute("user")).getId());
		log.info("live : {}", live);

		String token = getToken();
		log.info("token : {}", token);

		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/rooms/" + live.getRoomId());

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("API_KEY", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", token);
		conn.setDoOutput(true);

		// conn body에 데이터 담기
		String Data = "roomName=" + live.getLiveTitle() + "&roomType=화상&maxUser=5&roomStatus=s&rtcStat=Y&gTransStat=N";

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(Data);
		wr.flush();

		// 데이터 입력 스트림에 답기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		live.setLiveStatus(false);

		liveService.updateLive(live);

		return new RedirectView("/");
	}


	@GetMapping("addLiveReservation")
	public RedirectView addLiveReservation(HttpSession session, Model model) throws Exception {
		log.info("addLiveReservation GET : start...");

		User user = (User) session.getAttribute("user");

		LiveReservation liveReservation = liveService.getLiveReservationByStoreId(user.getId());

		log.info("liveReservation = {}", liveReservation);

		if (liveReservation == null) { // 프리미엄 라이브 예약을 하지 않았을 경우

			log.info("addLiveReservation GET : end...");
			return new RedirectView("/live/addLiveReservationView");
		} else { // 프리미엄 라이브 예약을 했을 경우
			return new RedirectView("/live/getLiveReservationList?date=" + liveReservation.getReservationDate());
		}
	}

	@GetMapping("addLiveReservationView")
	public String addLiveReservationView(HttpSession session, Model model) throws Exception {
		User user = (User) session.getAttribute("user");

		Map<String, Object> ProductList = productService.getProductListByStoreId(user.getId());

		model.addAttribute("list", ProductList.get("list"));
		return "/live/addLiveReservationView";
	}

	@PostMapping("addLiveReservation")
	public RedirectView addLiveReservation(LiveReservation liveReservation, @RequestParam String[] liveProductNum,
			HttpSession session, Model model) throws Exception {
		log.info("addLiveReservation POST : start...");

		User user = (User) session.getAttribute("user");

		liveReservation.setStore(user);

		liveService.addLiveReservation(liveReservation);

		log.info("PK value = {}", liveReservation.getLiveReservationNumber());

		LiveProduct liveProduct = new LiveProduct();
		liveProduct.setLiveReservationNumber(liveReservation.getLiveReservationNumber());

		for (int i = 0; i < liveProductNum.length; i++) {
			Product prod = productService.getProduct(Integer.parseInt(liveProductNum[i]));
			liveProduct.setProductNumber(prod.getProductNumber());
			liveProduct.setProductName(prod.getProductName());
			liveProduct.setProductMainImage(prod.getProductMainImage());
			liveProduct.setProductDetail(prod.getProductDetail());

			liveService.addLiveProduct(liveProduct);
		}

		log.info("addLiveReservation POST : end...");
		return new RedirectView("/live/getLiveReservationList?date=" + liveReservation.getReservationDate());
	}


	@GetMapping("deleteLiveReservation/{liveProductNum}/{date}")
	public RedirectView deleteLiveReservation(@PathVariable String liveProductNum, @PathVariable String date)
			throws Exception {
		log.info("deleteLiveReservation GET start...");

		liveService.deleteLiveReservation(Integer.parseInt(liveProductNum));

		liveService.deleteLiveProductByReservationNumber(Integer.parseInt(liveProductNum));
		// liveService.deleteLive
		log.info("deleteLiveReservation GET end...");
		return new RedirectView("/live/getLiveReservationList?date=" + date);
	}

}