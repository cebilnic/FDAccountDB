package com.example.databasequery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class AccountInfo {
	@Id
	private Long account_no;
	@NotNull
	@Size(max=25)
	private String name;
	private String address;
	private int deposit_amount;
	private String deposite_date;
	private int no_of_year;
	private int maturity_amt;
	private byte[] data;
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Long getAccount_no() {
		return account_no;
	}
	public void setAccount_no(Long account_no) {
		this.account_no = account_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDeposit_amount() {
		return deposit_amount;
	}
	public void setDeposit_amount(int deposit_amount) {
		this.deposit_amount = deposit_amount;
	}
	public String getDeposite_date() {
		return deposite_date;
	}
	public void setDeposite_date(String deposite_date) {
		this.deposite_date = deposite_date;
	}
	public int getNo_of_year() {
		return no_of_year;
	}
	public void setNo_of_year(int no_of_year) {
		this.no_of_year = no_of_year;
	}
	public int getMaturity_amt() {
		return maturity_amt;
	}
	public void setMaturity_amt(int maturity_amt) {
		this.maturity_amt = maturity_amt;
	}
	@Override
	public String toString() {
		return "data_entry [account_no=" + account_no + ", name=" + name + ", address=" + address + ", deposit_amount="
				+ deposit_amount + ", deposite_date=" + deposite_date + ", no_of_year=" + no_of_year + ", maturity_amt="
				+ maturity_amt + "]";
	}

}

