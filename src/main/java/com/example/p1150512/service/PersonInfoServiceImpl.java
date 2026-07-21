package com.example.p1150512.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.p1150512.dao.PersonInfoDao;
import com.example.p1150512.entity.PersonInfo;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	private final PersonInfoDao personInfoDao;

	public PersonInfoServiceImpl(PersonInfoDao personInfoDao) {
		this.personInfoDao = personInfoDao;
	}

	@Override
	@Transactional
	public List<PersonInfo> createPersonInfo(List<PersonInfo> personInfoList) {
		List<String> inputIds = new ArrayList<>();
		for (PersonInfo personInfo : personInfoList) {
			if (!StringUtils.hasText(personInfo.getId())) {
				throw new IllegalArgumentException("ID不得為空");
			}

			String id = personInfo.getId().trim();
			personInfo.setId(id);

			if (inputIds.contains(id)) {
				throw new IllegalArgumentException("輸入的" + id + "已重複");
			}

			if (personInfoDao.existsById(id)) {
				throw new IllegalArgumentException("資料庫已存在" + id);
			}

			inputIds.add(id);
		}
		// TODO Auto-generated method stub
		return personInfoDao.saveAll(personInfoList);
	}

	@Override
	@Transactional
	public int updatePersonInfo(PersonInfo person) {
		// TODO Auto-generated method stub
		return personInfoDao.updatePersonInfo(person);
	}

	@Override
	public List<PersonInfo> getAllPersonInfo() {
		// TODO Auto-generated method stub
		return personInfoDao.findAll();
	}

	@Override
	public PersonInfo getPersonInfoById(String id) {
		// TODO Auto-generated method stub
		return personInfoDao.findById(id).orElseThrow(() -> new IllegalArgumentException("找不到此 ID 的資料！"));
	}

	@Override
	@Transactional
	public List<PersonInfo> findByAgeGreaterThan(int age) {
		// TODO Auto-generated method stub
		return personInfoDao.findByAgeGreaterThan(age);
	}

}
