package lab02;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PageRank {
	
	public static HashMap<String, ArrayList<String>> result_map = new HashMap<String, ArrayList<String>>();
	public static HashMap<String,Double> pre_pagerank = new HashMap<String,Double>();
	public static HashMap<String,Double> next_pagerank = new HashMap<String,Double>();
	private static double damping = 0.85;
	private static int document_num = 10000;
	private static double rank_val;
	private static double threshold = 0.0001; 
	
	
	public static void main(String[] args) throws Exception {
	
		try{
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("/home/jisun/workspace/bigdatalab_02/directed.txt"));
			
			while((line = reader.readLine()) != null)
			{
				String[] split = line.split("\t");
				String page_node = split[0];
				
				for(int loop = 1; loop < split.length; loop++)
				{
					ArrayList<String> invertlink = result_map.get(split[loop]);
					
					if(invertlink == null)
					{
						invertlink = new ArrayList<String>();
						result_map.put(String.valueOf(split[loop]),invertlink);
					}
					
					invertlink.add(page_node);
					
				}
				
				double rank = Math.random();
				pre_pagerank.put(page_node, rank);
				
			}
			
			Iterator<String> iterator = pre_pagerank.keySet().iterator();
			
			
			System.out.println("Page	Ranking");
			
			while(iterator.hasNext())
			{
				String key = (String)iterator.next();
				rank_val = damping * (pre_pagerank.get(key)/result_map.get(key).size()) + (1 - damping) * (1/document_num );
				pre_pagerank.put(key, rank_val);
			}
			
			Iterator<String> iterator_1 = pre_pagerank.keySet().iterator();
			
			while(iterator_1.hasNext())
			{
				String key = (String)iterator_1.next();
				rank_val = damping * (pre_pagerank.get(key)/result_map.get(key).size()) + (1 - damping) * (1/document_num );
				next_pagerank.put(key, rank_val);
			}
			
			Iterator<String> iterator_2 = pre_pagerank.keySet().iterator();
			while(iterator_1.hasNext())
			{
				String key = (String)iterator_1.next();
				if(pre_pagerank.get(key)-next_pagerank.get(key)>=threshold){
					rank_val = damping * (next_pagerank.get(key)/result_map.get(key).size()) + (1 - damping) * (1/document_num );
					next_pagerank.put(key, rank_val);
				}
				else{
					rank_val = next_pagerank.get(key);
				}

			}
				
			
			Iterator<String> it = PageRank.sortByValue(next_pagerank).iterator();
			
			while(it.hasNext()){
	            String key = (String) it.next();
	            System.out.println(key + "	" + next_pagerank.get(key));
	        }
			
		
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}	
	
	 public static List sortByValue(final HashMap map){
	        List<String> list = new ArrayList();
	        list.addAll(map.keySet());
	         
	        Collections.sort(list,new Comparator(){
	             
	            public int compare(Object o1,Object o2){
	                Object v1 = map.get(o1);
	                Object v2 = map.get(o2);
	                 
	                return ((Comparable) v1).compareTo(v2);
	            }
	             
	        });
	        
	        Collections.reverse(list);
	        return list;
	    }

}