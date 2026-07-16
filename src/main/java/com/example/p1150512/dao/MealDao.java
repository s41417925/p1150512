package com.example.p1150512.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.p1150512.entity.Meal;

/**
 * @Repository: 告訴 Spring 容器：「這是一個資料存取層的組件，Spring 在啟動時會自動掃描到它，
 *              並為這個介面建立一個實作類別（Proxy 物件）注入到 Spring 容器中(託管)， 之後在 Service
 *              層就可以直接用 @Autowired 引入並使用它<br>
 * @Autowired: 注入(引入)被 Spring 託管的物件到容器中並使用之<br>
 *             JpaRepository<Meal, String>: <br>
 *             Meal：這個 Repository 是專門用來操作 Meal 這個 Entity（資料表）的<br>
 *             String：Meal 裡面被設定為 @Id (主鍵) 的那個變數型態是 String（也就是你的 name 欄位）
 */
@Repository
public interface MealDao extends JpaRepository<Meal, String> {

	/**
	 * @Transactional: 開啟事務管理，如果使用 @Query 自定義語法，這個必須要加<br>
	 * @Modifying: 提示框架這個查詢不是 SELECT(查詢)，而是 INSERT、UPDATE 或 DELETE， 如果不加Spring Boot
	 *             預設會用執行查詢的方式去執行這段 SQL。加上此註解後， 回傳值得資料型態只會是 int(Integer) 或是
	 *             void，整數值表示資料 新增/修改/刪除 成功的筆數<br>
	 * @Query: 要寫入自定義的語法<br>
	 *         nativeQuery = true: 告訴 JPA 直接把這段字串送進 MySQL 執行，不要用 JPA 的 JPQL 語法去解析它，
	 *         語法中使用的表和欄位的名稱是資料庫中的表和欄位名稱<br>
	 *         nativeQuery = false: 透過 JPA 的 JPQL 語法去解析語法，語法中使用的表和欄位名稱就換變成是 Entity
	 *         class 名稱(Meal) 以及 屬性變數名稱<br>
	 *         防止 SQL 注入(SQL Injection): SQL 語法中的 :name 和 :price 是佔位符，
	 *         它們會自動對應到後方 @Param 註解括號裡的字串名稱(非變數名稱)
	 */
	@Transactional
	@Modifying // 2. 告知 Spring 這是一個 DML (新增/修改/刪除) 操作
	@Query(value = "INSERT INTO meal (name, price) VALUES (:name, :price)", nativeQuery = true)
	public int insertMeal(@Param("name") String name, @Param("price") int price);

	@Query(value = "select * from meal where name = ?1", nativeQuery = true)
	public Optional<Meal> findByName(String name);

	/**
	 * 根據多個餐點名稱查詢餐點。
	 */
	@Query(value = "select * from meal where name in (?1)", nativeQuery = true)
	public List<Meal> findAllByNameList(List<String> nameList);

	/**
	 * 根據餐點名稱修改價格。
	 */
	@Modifying
	@Query(value = "update meal set price = ?2 where name = ?1", nativeQuery = true)
	public int updatePriceByName(String name, int newPrice);

}
