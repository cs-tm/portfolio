package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.MsEmployee;

@Component
@Mapper
public interface MsEmployeeRepository{
	MsEmployee findById(String code);
	List<MsEmployee> findAll();
}
