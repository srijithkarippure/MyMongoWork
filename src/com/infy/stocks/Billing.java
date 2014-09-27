package com.infy.stocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Billing {
	
	static String name;
	static String category;
public double bill(Item item) throws Exception
{
	Mongo mongo = new Mongo("localhost", 27017);
    DB db = mongo.getDB("sssmarket");
    DBCollection collection = db.getCollection("items");
    
    BasicDBObject query= new BasicDBObject().append("_id", item.getItemId());
    DBObject retrieved_item=collection.findOne(query);
      name= (String) retrieved_item.get("itemName");
      category=(String) retrieved_item.get("itemCategory");
    BasicDBList retrieved_details_array= (BasicDBList) retrieved_item.get("details");
    int net_wt= item.getNet_wt();
    double price = 0;
    for(Integer i=0;i<retrieved_details_array.size();i++){
    	DBObject retrieved_details_item= (DBObject) retrieved_details_array.get(i);      	
       	int retrieved_netwt=Integer.parseInt(retrieved_details_item.get("netwt").toString());
       	if(net_wt== retrieved_netwt ){
    		 price= Double.parseDouble(retrieved_details_item.get("price").toString());
    		 BasicDBObject updateQuery=new BasicDBObject().append("$inc", new BasicDBObject("details."+i.toString()+".quantity",-item.getQuantity()));
    				   collection.update(new BasicDBObject("_id", item.getItemId()),updateQuery);
    				   break;
    	}
    }
     double amount= price*item.getQuantity();
    return amount;
}

@SuppressWarnings("unchecked")
public double PrintBill(List items) {
	System.out.println("ITEM\t\t\t\tQUANTITY\t\t\t\tNETWEIGHT\t\t\t\tAMOUNT");
	double sum=0;
	for(int i=0;i<items.size();i=i+6){
		System.out.println(items.get(i+1)+"\t\t\t\t"+items.get(i+2)+"\t\t\t\t"+items.get(i+3)+"\t\t\t\t"+items.get(i+4));
		sum+=Double.parseDouble(items.get(i+4).toString());
	}
	return sum;
}

@SuppressWarnings("unchecked")
public void AddBill(List items,double sum) throws Exception {
	Date date= new Date();
	Mongo mongo = new Mongo("localhost", 27017);
    DB db = mongo.getDB("sssmarket");
    DBCollection collection = db.getCollection("BillCollection");
    BasicDBObject document=new BasicDBObject();
	document.put("date", date);
	List<BasicDBObject> bill_details_list= new ArrayList<BasicDBObject>();
	for(int i=0;i<items.size();i=i+6)
	{
BasicDBObject bill_details=new BasicDBObject().append("itemId", items.get(i)).append("name", items.get(i+1)).append("quantity", items.get(i+2)).append("netwt", items.get(i+3)).append("amount", items.get(i+4)).append("category", items.get(i+5));
bill_details_list.add(bill_details);
	}
	document.put("bill", bill_details_list);
	document.put("total", sum);
	collection.insert(document);
	
}
}
