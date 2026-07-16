package com.example.p1150512.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.p1150512.dao.MealDao;
import com.example.p1150512.entity.Meal;

/**
 * @Service: 是用來標記業務邏輯層（Business Logic Layer / Service 層）組件的註解<br>
 *           加在類別上方時，Spring 主要會幫你處理以下三件核心事務：<br>
 *           1. 自動註冊為 Spring Bean（納入 IoC 容器託管）<br>
 *           2. 標記商業邏輯的「集中地」<br>
 *           3. 事務管理（Transaction）切入點<br>
 *           Transaction 的使用時機:<br>
 *           1. 一個方法中，有使用到多個不同的 Dao 時 --> 跨表 新增/修改/刪除 資料 2. 一個方法中，有多次使用同一個 Dao
 *           時(迴圈) --> 一次新增/修改/刪除 多筆資料<br>
 *           結論就是 新增/修改/刪除 的資料，要嘛全部成功，要嘛全部不成功
 */
@Service
public class MealService {

	/*
	 * @Autowired: 把被 spring 託管的物件(MealDao)注入/引入到容器(變數 mealDao)中，<br>
	 * 如果要求注入的物件是沒有被託管的，系統啟動或使用到該物件時就會報錯<br> 使用的物件其宣告方式就等同於一個類別中屬性的宣告，格式如下: <br>
	 * 被存取權限 物件名稱(類別/介面) 變數名稱
	 */
	@Autowired
	private final MealDao mealDao;

	public MealService(MealDao mealDao) {
		this.mealDao = mealDao;
	}

	@Transactional(rollbackFor = Exception.class)
	public String addMeals(List<Meal> mealList) {

		if (CollectionUtils.isEmpty(mealList)) {
			return "新增失敗：請提供至少一筆餐點資料！";
		}

		for (int i = 0; i < mealList.size(); i++) {

			Meal meal = mealList.get(i);

			String name = meal.getName();
			int price = meal.getPrice();
			int index = i + 1;

			if (!StringUtils.hasText(name)) {
				throw new IllegalArgumentException("第 " + index + " 筆資料新增失敗：餐點名稱不能為空！");
			}

			if (name.length() > 100) {
				throw new IllegalArgumentException("第 " + index + " 筆資料新增失敗：餐點名稱不能超過 100 個字！");
			}

			if (price <= 0) {
				throw new IllegalArgumentException("第 " + index + " 筆資料新增失敗：價格必須大於 0！");
			}
		}

		try {

			int totalInserted = 0;

			for (Meal meal : mealList) {

				int rowsAffected = mealDao.insertMeal(meal.getName().trim(), meal.getPrice());

				if (rowsAffected == 1) {
					totalInserted++;
				} else {
					throw new RuntimeException("新增餐點「" + meal.getName() + "」失敗！");
				}
			}

			return "成功新增 " + totalInserted + " 筆餐點！";

		} catch (Exception e) {

			throw new RuntimeException("批次新增失敗，已全部回滾！錯誤原因：" + e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public String updateByName(String name, int newPrice) {

		if (!StringUtils.hasText(name)) {
			return "修改失敗：餐點名稱不能為空！";
		}

		if (newPrice <= 0) {
			return "修改失敗：價格不能小於等於 0 元！";
		}

		int rowsAffected = mealDao.updatePriceByName(name.trim(), newPrice);

		if (rowsAffected > 0) {
			return "「" + name + "」的價格已成功修改為 " + newPrice + " 元！";
		}

		return "修改失敗：資料庫未受影響。";
	}

	@Transactional(readOnly = true)
	public int getPrice(String name) {

		if (!StringUtils.hasText(name)) {
			return -1;
		}

		Optional<Meal> mealOpt = mealDao.findByName(name.trim());

		return mealOpt.map(Meal::getPrice).orElse(-1);
	}

	/**
	 * 新增單筆餐點的商業邏輯（含防呆驗證） * @param name 餐點名稱
	 * 
	 * @param price 餐點價格
	 * @return String 提示訊息（成功或失敗原因）
	 */
	@Transactional // 將事務管理加在 Service 層
	public String addMeal(String name, int price) {

		// ===== 參數防呆驗證 (Input Validation) =====
		// 使用排除法 --> 先將參數一一檢查，只要不符合規定，直接跳出方法

		// 1. 檢查名稱是否為空
		// 一定要先檢查類別是否是 Null，不然該物件是 Null時，null 使用點(.)去呼叫方法時會
		// 報錯(NullPointerException，空指針例外)
//        if (name == null || name.trim().isEmpty()) {
//            return "新增失敗：餐點名稱不能為空字串或 NULL！";
//        }
		// 上面這段可用下面的程式碼來替代
		// StringUtils.hasText(name): 同時判斷 name 不是 null 以及非全空白字串
		// !StringUtils.hasText(name): 前面有驚嘆號，等同於 StringUtils.hasText(name) == false
		if (!StringUtils.hasText(name)) {
			return "新增失敗：餐點名稱不能為空字串或 NULL！";
		}

		// 2. 檢查名稱長度（對應資料庫的 length = 100）
		if (name.length() > 100) {
			return "新增失敗：餐點名稱長度不能超過 100 個字！";
		}

		// 3. 檢查價格是否合理（對應資料庫的 nullable = false 且符合商業邏輯）
		if (price <= 0) {
			return "新增失敗：餐點價格不能為負數！";
		}

		// ===== 執行資料庫操作 =====
		try {
			// 呼叫 Dao 層的 nativeQuery 語法
			int rowsAffected = mealDao.insertMeal(name.trim(), price);
			// 只有新增一筆，結果要嘛等於1，要嘛報錯(因為新增的語法是寫 insert into，有相同PK就會報錯)
			// 有用 try-catch 將程式碼包住，報錯的話會被下面的 catch 抓取
			if (rowsAffected == 1) {
				return "「" + name + "」餐點新增成功！";
			} else {
				return "餐點新增失敗！";
			}
		} catch (Exception e) {
			// 捕捉可能發生的資料庫異常（例如：違反唯一主鍵，餐點名稱(欄位 name 的值)重複了）
			// 注意：如果拋出 RuntimeException，@Transactional 會自動觸發回滾
			return "新增失敗：資料庫發生錯誤（可能餐點名稱已重複）。" + e.getMessage();
		}

	}

	@Transactional(readOnly = true)
	public List<Meal> getAllMeals() {

		return mealDao.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	public String addMealList(List<Meal> mealList) {

		// ===== 🛑 階段一：基本格式驗證 =====
		if (CollectionUtils.isEmpty(mealList)) {
			return "新增失敗：請提供至少一筆餐點資料！";
		}
		// 用來收集所有預備新增的餐點名稱(PK)
		List<String> newNames = new ArrayList<>();
		for (int i = 0; i < mealList.size(); i++) {
			Meal meal = mealList.get(i);
			String name = meal.getName();
			int price = meal.getPrice();
			int index = i + 1;
			if (!StringUtils.hasText(name)) {
				return "第 " + index + " 筆資料驗證失敗：餐點名稱不能為空！";
			}
			if (name.length() > 100) {
				return "第 " + index + " 筆資料驗證失敗：名稱「" + name + "」長度不能超過 100 個字！";
			}
			if (price <= 0) {
				return "第 " + index + " 筆資料驗證失敗：「" + name + "」價格不能小於或等於 0 元！";
			}
			// 將經過去除空白處理的名稱存入 List 中
			newNames.add(name.trim());
		}
		mealDao.saveAll(mealList);
		return("新增成功");

	}
}