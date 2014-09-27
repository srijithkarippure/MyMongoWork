package com.infy.stocks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Stocks {
public boolean itemExists(String itemId) throws Exception
{
	Mongo mongo = new Mongo("localhost", 27017);
    DB db = mongo.getDB("ssmarket");
    DBCollection collection = db.getCollection("items");
    DBObject retrievedData=collection.findOne(new BasicDBObject("_id",itemId));
if(retrievedData!=null)
{
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("already exists");
	System.out.println("Itemname:"+retrievedData.get("itemName"));
	System.out.print("quantity:");
	int quantity=Integer.parseInt(br.readLine());
	System.out.print("Net Weight:");
	int net_wt=Integer.parseInt(br.readLine());

	DBObject retrieved_details=  (DBObject) retrievedData.get("details");
	BasicDBList retrieved_details_list= (BasicDBList) retrieved_details;
	int flag=1;
	for(Integer i=0;i<retrieved_details_list.size();i++)
	{
		
	DBObject retrieved_details_array= (DBObject) retrieved_details_list.get(i);
	String retrieved_details_netwt= retrieved_details_array.get("netwt").toString();
	
	if(Double.parseDouble(retrieved_details_netwt)==net_wt){
		DBObject update_query= new BasicDBObject("details."+i.toString()+".quantity",quantity);
	collection.update(new BasicDBObject("_id",itemId), new BasicDBObject("$inc",update_query));
	flag=0;
	break;
	}
	}
	if(flag!=0)
	{
		System.out.print("Enter the price:");
		Double price=Double.parseDouble(br.readLine());
DBObject push_query= new BasicDBObject("details", new BasicDBObject().append("netwt",net_wt).append("quantity", quantity).append("price", price));
	DBObject push_details=  new BasicDBObject("$push", push_query);

	collection.update(new BasicDBObject("_id",itemId),push_details);
	DBObject dbo= collection.findOne(new BasicDBObject("_id",itemId));
	System.out.println(dbo);

	}
	
	return true;

}
return false;
}

public void Add(Item item) throws Exception {
	Mongo mongo = new Mongo("localhost", 27017);
    DB db = mongo.getDB("ssmarket");
    DBCollection collection = db.getCollection("items");


    BasicDBObject document = new BasicDBObject();
    document.put("_id", item.getItemId());
    document.append("itemName", item.getItemName());
    document.append("itemCategory", item.getItemCategory());
    BasicDBObject details=  new BasicDBObject().append("netwt", item.getNet_wt()).append("quantity", 
    		item.getQuantity()).append("price", item.getPrice());
    ArrayList<BasicDBObject> details_list= new ArrayList<BasicDBObject>();
    details_list.add(details);
    document.append("details",details_list);
    
	collection.insert(document);

}
}
