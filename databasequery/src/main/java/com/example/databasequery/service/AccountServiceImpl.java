package com.example.databasequery.service;

import java.io.IOException;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.databasequery.model.AccountInfo;
import com.example.databasequery.repo.AccountRepo;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepo repo;
	@Override
	public AccountInfo saveAccountInfo(AccountInfo accountinfo) {
		return repo.save(accountinfo);
	}
	@Override
	public AccountInfo findAccountbyId(Long Id) {
		return repo.findById(Id).get();
	}
	
	@Override
	public boolean existAccountInfobyId(Long Id) {
		return repo.existsById(Id);
	}
	
	@Override
	 public List<AccountInfo> fetchAccountList(){
		return repo.findAll();
	}
	
	@Override
	public Long isAccountDetailsEmpty() {
		return repo.count();
	}
	@Override
	public boolean isAccountlistEmpty(){
		System.out.println("count = ");
		return (repo.count() == 0);
	}

    @Override
	@Transactional
	public boolean addDocumentbyId(AccountInfo acc,MultipartFile file){
	try {
		acc.setData(file.getBytes());
		return true;
	}
	catch(Exception e) {
		return false;
	}
}
}
