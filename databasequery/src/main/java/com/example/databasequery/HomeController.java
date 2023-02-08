package com.example.databasequery;

import com.example.databasequery.model.AccountInfo;
import com.example.databasequery.repo.AccountRepo;
import com.example.databasequery.service.AccountService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import javax.swing.JOptionPane;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	private AccountInfo accinfo;
	@Autowired
	private AccountService accservice;
	public String UPLOAD_FOLDER = "E:\\save _files\\";
	Logger log = LoggerFactory.getLogger(HomeController.class);

	public String getTodaysDate() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(today);
	}

	@RequestMapping("/")
	public String showHomePage() {
		return "index";
	}

	@RequestMapping("/accountentry")
	public String uploadAccountDetails(Model m) {
		String todaysDate = getTodaysDate();
		m.addAttribute("res", todaysDate);
		System.out.println(todaysDate);
		return "accountentry";
	}

	@PostMapping(value = "/validate")
	public String showUploadingData(@ModelAttribute("data_entry") AccountInfo data_entry) {
		int no_of_days             = data_entry.getNo_of_year() * 365;        // number of years
		int interest_rate          = 7;                                 // interst percentage
		int total_amount           = data_entry.getDeposit_amount();
		int freq_of_compounding    = 1;
		int maturity_amts          = 0;

		if (freq_of_compounding != 0){
			maturity_amts =  ((total_amount) * ((int)Math.round(Math.pow((1+(interest_rate/freq_of_compounding)), (freq_of_compounding*no_of_days)))));
			if (maturity_amts != 0){
				data_entry.setMaturity_amt(maturity_amts);
			}
		}

		data_entry.setDeposite_date(getTodaysDate());
		data_entry.setMaturity_amt(1425);
		accinfo = data_entry;
		return "validates";
	}

	@RequestMapping("/uploaded")
	public String uploadSuccess(Model m) {
		System.out.println(accinfo.toString());
		System.out.println(accinfo.getAccount_no().getClass().getName());
		accservice.saveAccountInfo(accinfo);
		m.addAttribute("data_entry", accinfo);
		return "uploaded";
	}

	@RequestMapping("/accountdetails")
	public String showAccountDetails(@ModelAttribute("acn") Long acn, Model m) {
		System.out.println("entered into the loop");
		System.out.println(acn);
        System.out.println(accservice.existAccountInfobyId(acn));
        if (accservice.existAccountInfobyId(acn)) {
			Boolean dropbox = true;
        	accinfo = accservice.findAccountbyId(acn);
        	m.addAttribute("data_entry", accinfo);
			m.addAttribute("dropbox", dropbox);
        	return "accountdetails";
        }
        else {
        	return "accountNotFound";
        }
		
	}
	
	@RequestMapping("/viewAll")
	public String showAllAccountDetails(Model m) {
		System.out.println("entered into viewAll");
		System.out.println(accservice.fetchAccountList());
		m.addAttribute("accinfos", accservice.fetchAccountList());
		if(accservice.isAccountlistEmpty()){
			return "accountNotFound";
		}
		else{
			return "viewall";
		}
	}

	@RequestMapping("/editName")
	public String editNameInUserAccount(@RequestParam Long acc){
		System.out.println(acc);
		return "notAdded";
	}

	@RequestMapping("/editAddress")
	public String editAddressInUserAccount(){
		return "notAdded";
	}

	@RequestMapping(value = "/uploadDocument")
	public String uploadDocumentforUser(){
		return "uploadFile";
	}

	@PostMapping("/uploadedFile")
	public String validateFiles(@RequestParam("file") MultipartFile file, @RequestParam("account_number") Long account_number) {
		log.info("file is uploading");
		if (file.isEmpty()) {
			System.out.println("file is empty, going to error file");
			return "error";
		}
		// upload file to db connected to the account number using service
		if (accservice.existAccountInfobyId(account_number)) {
        	accinfo = accservice.findAccountbyId(account_number);
			if(!accservice.addDocumentbyId(accinfo,file)){
				log.info("exception during getbyteoperation");
				return "error";
			}
		}
		else {
			log.info("Account number doesn't exist:- can't find in the DB");
			return "error";
		}
        // try to save the file uploaded in to a local folder
		try {
			String content = file.getContentType();
			System.out.println("file content: " + content);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
			System.out.println(path);
			Files.write(path, bytes);
		}
		catch(Exception e) {
			log.info("unable to save file in local folder");
			return "error";
		}
		return "index";
	}
	
  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
	accinfo = accservice.findAccountbyId(id);
	byte[] fileContent = accinfo.getData();
  
	if (fileContent == null ) {
	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_PDF);
	headers.setContentLength(fileContent.length);
	headers.setContentDispositionFormData("attachment", "file.pdf");
	
	return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
  }
}



