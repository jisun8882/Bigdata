package lab07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class association_rule{

public static HashMap<String, String> itemset = new HashMap<String, String>();
public static HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
public static HashMap<ArrayList<String>, Integer> frequent = new HashMap<ArrayList<String>, Integer>() ;
public static HashMap<ArrayList<String>, Integer> frequentset = new HashMap<ArrayList<String>, Integer>() ;
public static double min_support = 0.4;
public static double min_confi = 0.4;
public static ArrayList<String> itemlist = null;
public static int person_id = 0;
	
public static void readFile() throws IOException {
		
		try{
			
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("vote.txt"));
			
			boolean is_attr = true;
			
			while((line = reader.readLine()) != null)
			{
				if(line.equals("")){
					is_attr = false;
					continue;
				}
				
				if(is_attr){
					String split[] = line.split(" ");
					
					if(split[0].equals("@attribute"))
					{
						itemset.put(split[2], split[1]);
					}
				}
				else{
					itemlist = new ArrayList<String>();
					String split[] = line.split(" ");
					
					data.put(Integer.toString(person_id), itemlist);
					
					for(int i = 0; i<split.length; i++)
					{
						itemlist.add(split[i]);
					}
					
					person_id++;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void frequent_item(){
		Iterator<String> itemIter = itemset.keySet().iterator();
		while(itemIter.hasNext()){
			String attr = itemIter.next();
			int count=0;
			
			for(int i = 0; i<data.size(); i++){
				
				itemlist = data.get(Integer.toString(i));
				
				if(itemlist.contains(attr)){
					count++;
				}
			}
			
			if((double)count/data.size()>=min_support){
				ArrayList<String> fr_itemset = new ArrayList<String>();
				fr_itemset.add(attr);
				frequent.put(fr_itemset, count);
			}
		}
	}

	public static void frequent_itemset(HashMap h1){
		
		HashMap<ArrayList<String>, Integer> frequentset = new HashMap<ArrayList<String>, Integer>() ;
		Iterator<ArrayList<String>> itemIter = h1.keySet().iterator();
		HashMap<ArrayList<String>, Integer> nextMap = (HashMap<ArrayList<String>, Integer>) h1.clone();		
		while(itemIter.hasNext()){
			
			ArrayList<String> attr = itemIter.next();
			nextMap.remove(attr);
			Iterator<ArrayList<String>> item_nextIter = nextMap.keySet().iterator();
			
			while(item_nextIter.hasNext()){
				
				ArrayList<String> next_attr = item_nextIter.next();
				int count = 0;
				
				ArrayList<String> fr_itemset = new ArrayList<String>();
				
				fr_itemset.addAll(attr);
				fr_itemset.addAll(next_attr);

				ArrayList itemset_result_List = new ArrayList<String>();
				HashSet hs = new HashSet(fr_itemset); 
						
				Iterator it = hs.iterator(); 
				while(it.hasNext()){ 
					itemset_result_List.add(it.next()); 
				}
				
				Collections.sort(itemset_result_List);
				
				
				for(int i= 0; i<data.size(); i++){
					itemlist = data.get(Integer.toString(i));
										
					if(itemlist.containsAll(itemset_result_List)){
						count++;
					}

				}
				
				if((double)count/data.size() >= min_support){					
					if(frequent.containsKey(itemset_result_List) == false ){					
						frequentset.put(itemset_result_List, count);
					}
				}
			}
			
		}
				
		frequent.putAll(frequentset);
		if(frequentset.size()>0){
			
			frequent_itemset(frequentset);
			
		}
		
	}
	
	public static void cal_confi(HashMap<ArrayList<String>, Integer> h1){
		Iterator<ArrayList<String>> itemIter = h1.keySet().iterator();
		ArrayList<String> clone_list = new ArrayList<String>();
		while(itemIter.hasNext()){			
			ArrayList<String> attr = itemIter.next();
			clone_list = (ArrayList<String>) attr.clone();
			
			for(int i=0; i<attr.size(); i++){
				for(int j=i; j<attr.size(); j++){
					List sub_list = attr.subList(i, j);
					clone_list.remove(sub_list);
					double support = (double) h1.get(attr);
					if(support/h1.get(clone_list) >= min_confi){
						System.out.println(clone_list.toString() +"->"+ sub_list.toString());
						System.out.println("confi = " +(double)h1.get(attr)/(double)h1.get(clone_list));
						System.out.println("support = " +(double)h1.get(attr));
					}
				}
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		readFile();
		frequent_item();
		frequent_itemset(frequent);
		
		Set <ArrayList<String>> keyset = frequent.keySet();
		
		TreeSet <ArrayList<String>> sortKey = new TreeSet <ArrayList<String>>(new Comp());
		sortKey.addAll(keyset);
		
		Iterator<ArrayList<String>> Iterator1 = sortKey.iterator();
		
		while(Iterator1.hasNext()){
			ArrayList<String> key = Iterator1.next();
			System.out.println(key+":"+frequent.get(key));
		}
	}
	
	static class Comp implements Comparator<ArrayList<String>>{
		public int compare(ArrayList<String> o1, ArrayList<String> o2){ 
			if (o1.size() > o2.size()) return 1;
			else return -1;

		}
	}
}