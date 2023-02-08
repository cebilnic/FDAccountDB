package com.example.databasequery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.databasequery.model.AccountInfo;
import com.example.databasequery.repo.AccountRepo;

public interface AccountService {
	AccountInfo saveAccountInfo(AccountInfo acc);
	AccountInfo findAccountbyId(Long Id);
	boolean existAccountInfobyId(Long Id);
	List<AccountInfo> fetchAccountList();
	Long isAccountDetailsEmpty();
	boolean isAccountlistEmpty();
	boolean addDocumentbyId(AccountInfo acc,MultipartFile file);
}
