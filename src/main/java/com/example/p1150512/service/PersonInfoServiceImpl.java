package com.example.p1150512.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		// TODO Auto-generated method stub
		return personInfoDao.saveAll(personInfoList);
	}

	@Override
	@Transactional
	public int updatePersonInfo(String id, String name, int age, String city) {
		// TODO Auto-generated method stub
		return personInfoDao.updatePersonInfo(
                id, name, age, city);
	}

	@Override
	public List<PersonInfo> getAllPersonInfo() {
		// TODO Auto-generated method stub
	     return personInfoDao.findAll();
	}

	@Override
	public PersonInfo getPersonInfoById(String id) {
		// TODO Auto-generated method stub
        return personInfoDao.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "找不到此 ID 的資料！"));
	}

	@Override
	@Transactional
	public List<PersonInfo> findByAgeGreaterThan(int age) {
		// TODO Auto-generated method stub
        return personInfoDao.findByAgeGreaterThan(age);
	}

}
