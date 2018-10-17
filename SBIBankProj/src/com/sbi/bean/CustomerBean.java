package com.sbi.bean;

public class CustomerBean
{
	private int accNumber=000;
	private String name;
	private double deposit;
	private double withdraw;
	private double balance;
	private double amount;
	private int pin;
	public CustomerBean()
	{
		
		this.name=name;
		
	}
	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public int getPin() {
		return pin;
	}
	public int getAccNumber() {
		return accNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public double getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(double withdraw) {
		this.withdraw = withdraw;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getAmount() {
		return amount;
	}
	
	
	
}
