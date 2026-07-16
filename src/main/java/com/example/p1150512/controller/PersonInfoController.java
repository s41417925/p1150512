package com.example.p1150512.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.p1150512.entity.PersonInfo;
import com.example.p1150512.service.PersonInfoService;

@RestController
@RequestMapping("/person-info")
public class PersonInfoController {

    private final PersonInfoService personInfoService;

    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    // 1. 新增一筆或多筆資料
    @PostMapping("/create")
    public List<PersonInfo> createPersonInfo(
            @RequestBody List<PersonInfo> personInfoList) {

        return personInfoService.createPersonInfo(personInfoList);
    }

    // 2. 更新資料
    @PutMapping("/update")
    public int updatePersonInfo(
            @RequestParam String id,
            @RequestParam(required = false) String name,
            @RequestParam int age,
            @RequestParam(required = false) String city) {

        return personInfoService.updatePersonInfo(
                id, name, age, city);
    }

    // 3. 取得全部資料
    @GetMapping("/all")
    public List<PersonInfo> getAllPersonInfo() {

        return personInfoService.getAllPersonInfo();
    }

    // 4. 透過 ID 取得資料
    @GetMapping("/{id}")
    public PersonInfo getPersonInfoById(
            @PathVariable String id) {

        return personInfoService.getPersonInfoById(id);
    }

    // 5. 找出年齡大於輸入條件的人
    @GetMapping("/age-greater-than")
    public List<PersonInfo> findByAgeGreaterThan(
            @RequestParam int age) {

        return personInfoService.findByAgeGreaterThan(age);
    }
}