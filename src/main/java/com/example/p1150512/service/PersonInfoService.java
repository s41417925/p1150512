package com.example.p1150512.service;

import java.util.List;

import com.example.p1150512.entity.PersonInfo;

public interface PersonInfoService {
    List<PersonInfo> createPersonInfo(
            List<PersonInfo> personInfoList);

    int updatePersonInfo(
            String id,
            String name,
            int age,
            String city);

    List<PersonInfo> getAllPersonInfo();

    PersonInfo getPersonInfoById(String id);

    List<PersonInfo> findByAgeGreaterThan(int age);	
}
