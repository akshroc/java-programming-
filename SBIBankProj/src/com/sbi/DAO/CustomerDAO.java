package com.sbi.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.*;

import com.sbi.bean.CustomerBean;
import com.sbi.util.DBConnection;

public class CustomerDAO 
{
	Connection con=DBConnection.getSingleton().getConnection();
	Statement st;
	ResultSet rs;
	CustomerBean customerBean;

	public void createAccount(String name,int pin,double amount)
	{
		int accNumber=001;
		try {
			st=con.createStatement();
			rs=st.executeQuery("select max(accnumber) from sbi");
			if(rs.next())
			{
				accNumber=rs.getInt(1)+1;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		String checkId="insert into sbi values("+accNumber+",'"+name+"',"+amount+","+pin+")";
		try {
			if (amount>=500)
				if(pin<=9999 && pin>=999)
				{
					st.executeUpdate(checkId);

					System.out.println("Account created successfully...");
					System.out.println("********************************");
					System.out.println("--------Account detail----------");
					System.out.println("Account Number :"+accNumber);
					System.out.println("Account holder :"+name);
					System.out.println("********************************");
				}
				else
					System.err.println("please select 4 digit pin");
			else
				System.err.println("Minimum balance to open account in SBI is 500");

		}catch (Exception e) {
			System.out.println("Something went wrong during account creation:");
		}
	}

	/**
	 * To check balance of your account.
	 * @param accNumber
	 * @return balance
	 */
	public double checkBalance(int accNumber, int pin)
	{
		double balance=0;
		String checkId="select balance from sbi where accnumber="+accNumber+" and pin="+pin;
		try {
			st=con.createStatement();
			rs=st.executeQuery(checkId);
			if(rs.next())
			{
				balance=rs.getDouble(1);
			}
		} catch (SQLException e) {
			System.err.println("Invalid input : Check your Account Number and Pin");
			e.printStackTrace();
		}
		return balance;
	}

	public void withdraw(int accNumber,double amount,int password) 
	{
		try {
			double balance=getBalance(accNumber, password);
			balance = balance - amount;
			if (balance>500) 
			{
				con.setAutoCommit(false);
				String query1="update sbi set balance="+balance+" where accnumber="+accNumber+" and pin="+password;
				String query="insert into transactionlog (source_acc,amount,status,date_time,action) values ("+accNumber+","+amount+",'debited'"+
						",'"+new Date()+"','withdraw')";
				st=con.createStatement();
				st.addBatch(query1);
				st.addBatch(query);
				st.executeBatch();
					con.commit();
					System.out.println("*********************************************************************************");
					System.out.println(amount+" withdraw successful");
					System.out.println("current balance :"+checkBalance(accNumber, password));
			}
			else
			{
				System.err.println("Transation failed : Maintain min balance 500");
			}


		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("*********************************************************************************");
			System.err.println("withrawal failed...");
			System.out.println("current balance :"+checkBalance(accNumber,password));
			System.out.println("*********************************************************************************");
			e.printStackTrace();
		}

	}

	public void deposit(int accNumber,double amount,int password)
	{
		try {
			double balance=getBalance(accNumber, password);
			balance+=amount;
			con.setAutoCommit(false);
			String query1="update sbi set balance="+balance+" where accnumber="+accNumber+" and pin="+password;
			String query="insert into transactionlog (source_acc,amount,status,date_time,action) values ("+accNumber+","+amount+",'credited'"+
					",'"+new Date()+"','deposit')";
			st=con.createStatement();
			st.addBatch(query1);
			st.addBatch(query);
			st.executeBatch();
				con.commit();
				System.out.println("*********************************************************************************");
				System.out.println(amount+" deposited successful");
				System.out.println("current balance :"+checkBalance(accNumber,password));
				System.out.println("*********************************************************************************");
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("*********************************************************************************");
			System.err.println("deposit failed...");
			System.out.println("current balance :"+checkBalance(accNumber,password));
			System.out.println("*********************************************************************************");
			e.printStackTrace();
		}

	}


/**
 * transfer money from one user to other and update the record
 * inside sbi table and transactionlog table
 * @param tranfer_AccNumber
 * @param source_accNumber
 * @param pin
 * @param trans_amount
 */
	public void transfer(int tranfer_AccNumber,int source_accNumber, int pin, double trans_amount) 
	{
		
		double trans_balance=0;
		//getting the source balance
		double source_balance=getBalance(source_accNumber, pin);
		//minimum 500 balance in source account
		if (source_balance>500)
		{
			source_balance = source_balance - trans_amount;
			try {
				//get balance of transfer account
				st=con.createStatement();
				rs=st.executeQuery("select balance,name from sbi where accnumber="+tranfer_AccNumber);
				while (rs.next())
				{
					trans_balance=rs.getInt("balance");
				}
				trans_balance+=trans_amount;
				con.setAutoCommit(false);
				String name=getName(tranfer_AccNumber);
				//update source account in sbi table
				String query1="update sbi set balance="+source_balance+" where accnumber="+source_accNumber;
				//inserting record in transactionlog table
				String queryLog="insert into transactionlog values("+source_accNumber+","+tranfer_AccNumber+",'"+name+
						"',"+trans_amount+",'debited','"+new Date()+"','transaction')";
				//update transfer account in sbi table 	
				String query2="update sbi set balance="+trans_balance+" where accnumber="+tranfer_AccNumber;
				//inserting record in transactionlog table
				String queryLog2="insert into transactionlog values("+source_accNumber+","+tranfer_AccNumber+",'"+name+
						"',"+trans_amount+",'credited','"+new Date()+"','transction')";
				st.addBatch(query1);
				st.addBatch(queryLog);
				st.addBatch(query2);
				st.addBatch(queryLog2);
				st.executeBatch();
				con.commit();
				System.out.println("*********************************************************************************");
				System.out.println("money tranfered successfully...");
				System.out.println("current balance :"+checkBalance(source_accNumber,pin));
				System.out.println("*********************************************************************************");
			}
		 catch (SQLException  e) 
			{
			 e.printStackTrace();
			    try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println("*********************************************************************************");
				System.err.println("transation failed...");
				System.out.println("current balance :"+checkBalance(source_accNumber,pin));
				System.out.println("*********************************************************************************");
			 
		}
	}
	else
		System.err.println("Transtion failed : your account have low balance"+source_balance);
}
			
	
	
	public boolean login(int acc_number, int pin)
	{
		boolean isTrue=false;
		String checkId="select name from sbi where accnumber="+acc_number+" and pin="+pin;
		try {
			st=con.createStatement();
			rs=st.executeQuery(checkId);
			if (rs.next()) 
			{
				System.out.println("----------------------------Welcome "+rs.getString("name")+"---------------------------");
				isTrue=true;
			}
			return isTrue;
		} 
		catch (Exception e) {
			System.err.println("login failed...");

		}
		return isTrue;
	}
	public double getBalance(int acc_num, int pin)
	{
		double balance=0;
		String query="select balance from sbi where accnumber="+acc_num+" and pin="+pin;
		try {
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) 
			{
				balance=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;

	}
	public String getName(int acc_number)
	{
		String name=null;
		String query="select name from sbi where accnumber="+acc_number;
		try {
			st=con.createStatement();
			st.executeQuery(query);
			if (rs.next()) 
			{
				name=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return name;
	}










}
