package com.example.p1150512.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.p1150512.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {
	// 年齡介於兩個數字之間，包含頭尾
	// 年齡由大到小，只取前三筆
	List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int minAge, int maxAge);

	// city 包含特定文字
	List<PersonInfo> findByCityContaining(String keyword);

	// city 在指定的城市清單裡
	List<PersonInfo> findByCityIn(List<String> cities);

	// 找出年齡大於指定數字的人
	List<PersonInfo> findByAgeGreaterThan(int age);

	// 更新姓名、年齡與城市
	@Modifying
	@Transactional
	@Query("""
			UPDATE PersonInfo p
			SET p.name =
			    CASE
			        WHEN :#{#person.name} IS NULL THEN p.name
			        ELSE :#{#person.name}
			    END,
			    p.sex =
			    CASE
			        WHEN :#{#person.sex} IS NULL THEN p.sex
			        ELSE :#{#person.sex}
			    END,
			    p.city =
			    CASE
			        WHEN :#{#person.city} IS NULL THEN p.city
			        ELSE :#{#person.city}
			    END,
			    p.age =
			    CASE
			        WHEN :#{#person.age} <= 0 THEN p.age
			        ELSE :#{#person.age}
			    END
			WHERE p.id = :#{#person.id}
			""")
	public int updatePersonInfo(@Param("person") PersonInfo person);
}
