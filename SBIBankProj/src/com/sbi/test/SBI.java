package com.sbi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sbi.DAO.CustomerDAO;
import com.sbi.bean.CustomerBean;

public class SBI
{
	public static void main(String[] args)
	{
		CustomerDAO dao = new CustomerDAO();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println();
		do {
			System.out.println("===================================");
			System.out.println("1. create account \t 2.login");
			System.out.println("===================================");
			int key;
			try {
				System.out.print("Please select option to processed: ");
				key = Integer.parseInt(br.readLine());

				switch (key) {
				case 1:
					System.out.println("=========================================================================");
					System.out.println("-------------------------------Welcome to SBI----------------------------");
					System.out.print("Name   :");
					String name = br.readLine();
					System.out.print("Amount :");
					int amount = Integer.parseInt(br.readLine());
					System.out.print("Pin    :");
					int pin = Integer.parseInt(br.readLine());
					dao.createAccount(name, pin, amount);
					System.out.println("=========================================================================");

					break;
				case 2:
					System.out.println("=========================================================================");
					System.out.println("---------------------------Login to your SBI account---------------------");
					System.out.print("Account Number :");
					try {
						int acc_num = Integer.parseInt(br.readLine());
						System.out.print("Pin :");
						int pin1 = Integer.parseInt(br.readLine());
						boolean re=dao.login(acc_num, pin1);
						System.out.println(re);
						if (re) 
						{
							do {
								System.out.println(
										"........................................................................");
								System.out.println("1.Balance \t 2.Withdraw \t 3.Deposit \t 4.Transfer \t 5. Logout");
								System.out.println(
										"........................................................................");
								System.out.println("Please select above option to proccesses :");
								int option = Integer.parseInt(br.readLine());
								switch (option) {
								case 1:
									System.out.println("================================================================");
									System.out.println("Account Number       :" + acc_num);
									System.out.print("Re-enter your 4 digit pin :");
									int pin2 = Integer.parseInt(br.readLine());
									double balance=dao.checkBalance(acc_num, pin2);
									System.out.println("Balance :"+balance);
									System.out.println("================================================================");
									break;
								case 2:
									System.out.println("================================================================");
									System.out.println("Account Number           :" + acc_num);
									System.out.print("Enter amount to withdraw :");
									int amount1 = Integer.parseInt(br.readLine());
									System.out.print("Enter your 4 digit pin :");
									int pin3 = Integer.parseInt(br.readLine());
									dao.withdraw(acc_num, amount1, pin3);
									System.out.println("================================================================");
									break;
								case 3:
									System.out.println("================================================================");
									System.out.println("Account Number           :" + acc_num);
									System.out.print("Enter amount to deposit    :");
									int amount2 = Integer.parseInt(br.readLine());
									System.out.print("Enter your 4 digit pin     :");
									int pin4 = Integer.parseInt(br.readLine());
									dao.deposit(acc_num, amount2, pin4);
									System.out.println("================================================================");
									break;
								case 4:
									System.out.println("================================================================");
									System.out.println("Account Number             :" + acc_num);
									System.out.print("Transfer Account Number    :");
									int trans_acc = Integer.parseInt(br.readLine());
									System.out.print("Enter amount to transfer   :");
									int amount3 = Integer.parseInt(br.readLine());
									System.out.print("Enter your 4 digit pin     :");
									int pin5 = Integer.parseInt(br.readLine());
									dao.transfer(trans_acc, acc_num, pin5, amount3);
									System.out.println("================================================================");
									break;
								case 5:
									System.out.println("================================================================");
									System.out.println("-----------------------thank you for using SBI service----------------");
									System.exit(0);
									System.out.println("================================================================");

								default:System.out.println("================================================================");
								System.out.println("Invalid Input : Please select valid option");
								System.out.println("================================================================");
									break;
								}
							} while (true);
						}
						else
							System.err.println("Login failed : Please provide valid Account Number & Password");
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}
					System.out.println("=========================================================================");
					break;



				default:System.err.println("Invalid Input :Please  select correct option ");
				break;
				}
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			} 

		} while (true);

	}
}
