package com.tje.cal_app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;

public interface S3Service {
	public List<PutObjectResult> upload(MultipartFile file);
}
