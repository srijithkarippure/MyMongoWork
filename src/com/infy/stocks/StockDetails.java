package com.infy.stocks;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class StockDetails {
private Mongo mongo;
private DB db;
private DBCollection collection;

public StockDetails() throws Exception {
	mongo= new Mongo("localhost",27017);
	db= mongo.getDB("sssmarket");
	collection=db.getCollection("items");
}

public void stockDetails() {
	DBCursor stocks= collection.find();
	System.out.println("ITEMNAME\t\t\tNETWEIGHT\t\t\tQUANTITY");
	for(DBObject o: stocks){
		System.out.print(o.get("itemName"));
	BasicDBList details_list= (BasicDBList) o.get("details");
	for(int i=0;i<details_list.size();i++){
		DBObject array_item= (DBObject) details_list.get(i);
		System.out.print("\t\t\t\t\t"+array_item.get("netwt"));
		System.out.println("\t\t\t"+array_item.get("quantity"));
		
	}
	}
}

public void stockDetails(String itemName,String itemCategory) {
	if(itemCategory==null){
		DBCursor stocks= collection.find(new BasicDBObject("itemName",itemName)); 
		System.out.println("ITEMNAME:"+itemName);
		if(stocks.count()==0){
			System.out.println("No Stock found");
		}
		else{
			System.out.println("\t\t\tNETWEIGHT\t\tQUANTITY");
		for(DBObject o: stocks){
		BasicDBList details_list= (BasicDBList) o.get("details");
		for(int i=0;i<details_list.size();i++){
			DBObject array_item= (DBObject) details_list.get(i);
			System.out.print("\t\t\t"+array_item.get("netwt"));
			System.out.println("\t\t\t"+array_item.get("quantity"));
	}}
}
	}
	if(itemName==null){
		DBCursor stocks= collection.find(new BasicDBObject("itemCategory",itemCategory)); 
		System.out.println("ITEMCATEGORY:"+itemCategory);
		if(stocks.count()==0){
			System.out.println("No Stock found");
		}
		else{
			System.out.println("ITEMNAME\t\t\tNETWEIGHT\t\tQUANTITY");
			for(DBObject o: stocks){
				System.out.print(o.get("itemName"));
				BasicDBList details_list= (BasicDBList) o.get("details");
				for(int i=0;i<details_list.size();i++){
					DBObject array_item= (DBObject) details_list.get(i);
					System.out.print("\t\t\t"+array_item.get("netwt"));
					System.out.println("\t\t\t"+array_item.get("quantity"));
			}}
		}
	}
}



}
