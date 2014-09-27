package com.infy.stocks;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;

public class Analysis {
	Mongo mongo;
	DB db;
	DBCollection collection ;
	public Analysis() {
		 try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		 db = mongo.getDB("sssmarket");
		collection = db.getCollection("BillCollection");
	}
	public void monthlySales(String month) throws Exception {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		int month_number = 0;
		for (Integer i = 0; i < months.length; i++) {
			if (months[i].equalsIgnoreCase(month)) {
				month_number = i;
				break;
			}
		}
		String map = "function(){if(this.date.getMonth()=="
				+ month_number
				+ "){for(var i in this.bill){emit({date:this.date.getMonth(),name:this.bill[i].name,netwt:this.bill[i].netwt}," +
				"{quantity:this.bill[i].quantity,amount:this.bill[i].amount});}}}";
		String reduce = "function(key,value){tsum={quantity:0,amount:0};value.forEach(function(obj){tsum.quantity+=obj.quantity;tsum.amount+=obj.amount;});" +
				"return tsum;}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,
				null, MapReduceCommand.OutputType.INLINE, null);

		MapReduceOutput out = collection.mapReduce(cmd);

		Double grandTotal = 0.0;
		System.out.println("Month:" + month);
		System.out.println("Name\t\t\tNet Weight\t\t\tQuantity\t\t\tAmount");
		for (DBObject o : out.results()) {

			DBObject id = (DBObject) o.get("_id");
			DBObject value = (DBObject) o.get("value");
			grandTotal += (Double) value.get("amount");
			System.out.println(id.get("name") + "\t\t\t" + id.get("netwt")
					+ "\t\t\t" + value.get("quantity") + "\t\t\t"
					+ value.get("amount"));
		}
		System.out.println("Grand Total:" + grandTotal);
	}

	public void itemAnalysis() {

	}

}
