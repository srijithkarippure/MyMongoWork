package com.infy.stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tester {
	
	public static void main(String[] args) throws Exception {

		String itemId;
		String itemName;
		String itemCategory;
		int net_wt;
		int quantity;
		double price;
		int choice = 0;

		System.out.println("********WELCOME TO SSSMARKET*********");
		System.out.println("1.Add Stocks");
		System.out.println("2.Billing");
		System.out.println("3.Analysis");
		System.out.println("4.Stock Details");
		System.out.println("5.Exit");
		do {
			System.out.print("Enter your choice:");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			try {
				choice = Integer.parseInt(br.readLine());
			} catch (IOException e) {

				e.printStackTrace();
			}

			switch (choice) {
			case 1:
				System.out.println("You are in Add Stocks");
				System.out.print("Enter item ID:");
				itemId = br.readLine();
				if (!new Stocks().itemExists(itemId)) {

					System.out.print("Enter item name:");
					itemName = br.readLine();
					System.out.print("Enter item category:");
					itemCategory = br.readLine();
					System.out.print("Net Weight:");
					net_wt = Integer.parseInt(br.readLine());
					System.out.print("quantity:");
					quantity = Integer.parseInt(br.readLine());
					System.out.print("Price:");
					price = Double.parseDouble(br.readLine());

					Item item = new Item();
					item.setItemCategory(itemCategory);
					item.setItemId(itemId);
					item.setItemName(itemName);
					item.setNet_wt(net_wt);
					item.setPrice(price);
					item.setQuantity(quantity);

					new Stocks().Add(item);
					System.out.println("Item Added");
				}
				break;

			case 2:
				System.out.println("You are in billing");
				double amount;
				Billing bill=new Billing();
				List items = new ArrayList();
				String s = "y";
				Item item_bill;
				do {
					System.out.print("Enter item ID:");
					itemId = br.readLine();
					System.out.print("quantity:");
					quantity = Integer.parseInt(br.readLine());
					System.out.print("net weight:");
					net_wt = Integer.parseInt(br.readLine());
					 item_bill = new Item();
					item_bill.setItemId(itemId);
					item_bill.setQuantity(quantity);
					item_bill.setNet_wt(net_wt);
					amount = bill.bill(item_bill);
					System.out.println("Amount:" + amount);
					items.add(itemId);
					items.add(Billing.name);
					items.add(quantity);
					items.add(net_wt);
					items.add(amount);
					items.add(Billing.category);
					
					System.out.println("Do u want to enter more(y/n):");
					s = br.readLine();
				} while (!s.equalsIgnoreCase("n"));
				
				
				double sum=bill.PrintBill(items);
				System.out.println("\n\nTOTAL:"+"\t"+sum);	
			
				bill.AddBill(items,sum);
				System.out.println("Bill added");

				break;

			case 3:
				
				System.out.println("You are in analysis");
				System.out.print("Enter the month:");
				String month= br.readLine();
				new Analysis().monthlySales(month);
		
				break;
			case 4:
				StockDetails sDetails= new StockDetails();
				System.out.println("STOCK DETAILS");
				String ch="n";
				System.out.println("Do u want to search by item name or category(y/n):");
				ch=br.readLine();
				if(ch.equalsIgnoreCase("n"))
				sDetails.stockDetails();
				else{
					System.out.println("YES for search by name{y/n}:");
					String option= br.readLine();
					if(option.equalsIgnoreCase("y")){
					System.out.println("ItemName:");
					String item_name= br.readLine();
					sDetails.stockDetails(item_name, null);
					}
					else{
						System.out.println("ItemCategory:");
						String item_category= br.readLine();
						sDetails.stockDetails(null, item_category);
					}
					
				}
				break;
			case 5:
				break;
			default:
				System.out.println("You have entered a wrong choice");
				break;
			}
		} while (choice != 5);
		System.out.println("BYE");
	}
}
