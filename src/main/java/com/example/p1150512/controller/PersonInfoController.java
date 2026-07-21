package com.example.p1150512.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.p1150512.entity.PersonInfo;
import com.example.p1150512.service.PersonInfoService;

/**
 * @RestController(控制器標籤): 告訴 Spring 框架這個類別是一個控制器（Controller），而且一律回傳 JSON
 * 或純文字資料。<br>
 *
 * 一般的 @Controller 預設會去找 HTML 網頁範本（View）來呈現畫面。但因為我們現在寫的是前後端分離的
 * API，不需要回傳整個網頁，只需要回傳資料（JSON 或純字串）。
 * 
 * @RestController 其實就是 @Controller + @ResponseBody 的組合包，宣告它之後，你方法 return 的東西（例如
 *                 ResponseEntity 或字串）就會直接寫進 HTTP ResponseBody 中， 送到前端的瀏覽器或
 *                 Postman<br>
 *                 ========================== @RequestMapping("/api/persons"):
 *                 定義這個 Controller 的網址路徑"前綴"。 只要是進來到這個控制器的請求，網址開頭一律都必須是
 *                 /api/persons
 */
@RestController
@RequestMapping("/api/persons")
public class PersonInfoController {
	@Autowired
	private PersonInfoService personInfoService;

	/**
	 * 1. 創建資訊 (支援 1~多筆) POST http://localhost:8080/api/persons
	 */
	/*
	 * @PostMapping("/create"): 限定這個方法只接收 HTTP 的 POST 請求。 因為 PersonInfoController
	 * 有多個 @PostMapping 方法請求，所以後面必須要加上更詳細的路徑資訊(/create)<br>
	 * 
	 * @RequestBody: 把前端傳過來的 JSON 資料，自動轉換成 Java 的物件<br> ResponseEntity<T>: 代表了整個
	 * HTTP 回應(HTTP Response)，裝著你要給前端的實際資料(Body)，T 是泛型。 裡面包含了: 1. 實際資料(Body) 2. HTTP
	 * 狀態碼(HTTP Status Code) 3. HTTP Headers(回應標頭)，例如設定編碼、Cookie 或安全性設定<br>
	 * ResponseEntity<String> 代表HTTP 回應裡面裝的是「一串純文字訊息」
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createPersons(@RequestBody List<PersonInfo> personList) {
		try {
			personInfoService.createPersonInfo(personList);
			return ResponseEntity.ok("【API 呼叫成功】已觸發批次創建，詳細執行結果請看 Console 記錄。");
		} catch (Exception e) {
			// 捕捉 Service 丟出的 RuntimeException (回滾異常)
			return ResponseEntity.status(500).body("【API 呼叫失敗】創建過程中發生錯誤，已安全回滾。請查看 Console 錯誤資訊。");
		}
	}

	/**
	 * 2. 更新資訊 PUT http://localhost:8080/api/persons
	 */
	@PostMapping("/update")
	public ResponseEntity<String> updatePerson(@RequestBody PersonInfo person) {
		try {
			personInfoService.updatePersonInfo(person);
			return ResponseEntity.ok("【API 呼叫成功】已觸發資料更新，詳細執行結果請看 Console 記錄。");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("【API 呼叫失敗】更新過程中發生錯誤，操作已撤銷。請查看 Console 錯誤資訊。");
		}
	}

	/**
	 * 3. 取得所有資訊 GET http://localhost:8080/api/persons
	 */
	@GetMapping
	public ResponseEntity<List<PersonInfo>> getAllPersons() {
		List<PersonInfo> persons =
	            personInfoService.getAllPersonInfo();
		
		for (PersonInfo person : persons) {
	        System.out.println(
	            "id=" + person.getId()
	            + ", name=" + person.getName()
	            + ", age=" + person.getAge()
	            + ", sex=" + person.getSex()
	            + ", city=" + person.getCity()
	        );}
		
		return ResponseEntity.ok(persons);
	}

	/**
	 * 4. 透過 id 取得對應的個人資訊 GET http://localhost:8080/api/persons/{id}
	 */
	/* @PathVariable(路徑參數提取器): 用來獲取網址路徑中，被大括號 {} 包起來的動態變數(Path Variables) */
	@GetMapping("/{id}")
	public ResponseEntity<String> getPersonById(@PathVariable String id) {
		personInfoService.getPersonInfoById(id);
		return ResponseEntity.ok("【API 呼叫成功】已執行 ID 查詢「" + id + "」，查詢結果請看 Console 記錄。");
	}

	/**
	 * 5. 找出年紀大於輸入條件的所有資訊 GET http://localhost:8080/api/persons/older-than?age=18
	 */
	/* @RequestParam(網址參數提取器): 用來獲取網址問號 ? 後面的參數(Query Parameters) */
	@GetMapping("/older-than")
	public ResponseEntity<String> getPersonsOlderThan(@RequestParam int age) {
		personInfoService.findByAgeGreaterThan(age);
		return ResponseEntity.ok("【API 呼叫成功】已執行大於 " + age + " 歲的年齡篩選，篩選名單請看 Console 記錄。");
	}
}
