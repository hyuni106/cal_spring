package com.tje.cal_app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;

@Service("com.tje.cal_app.service.S3ServiceImpl")
public class S3ServiceImpl implements S3Service {

	@Autowired
	AmazonS3 s3client;

	private PutObjectResult uploadProcess(InputStream inputStream, String uploadKey) {
		PutObjectRequest putObjectRequest = new PutObjectRequest("calendarapplication", uploadKey, inputStream,
				new ObjectMetadata());

		PutObjectResult putObjectResult = s3client.putObject(putObjectRequest);
		
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

		IOUtils.closeQuietly(inputStream, null);

		return putObjectResult;
	}

	@Override
	public List<PutObjectResult> upload(MultipartFile file) {
		// TODO Auto-generated method stub
		List<PutObjectResult> results = new ArrayList<PutObjectResult>();

		try {
			results.add(uploadProcess(file.getInputStream(), file.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}
}
