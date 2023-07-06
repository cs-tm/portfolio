package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.AttendanceEntity;

@Component
@Mapper
public interface AttendanceRepository{
	List<AttendanceEntity> findByYm(String code, String ym);
	AttendanceEntity findByYmd(String code, String ym, String date);
	void entry(AttendanceEntity ent);
}
