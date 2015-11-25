package lab05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NaiveBayes_car{
	
	public static String[] buying = {"vhigh","high","med","low"};
	public static String[] main = {"vhigh","high","med","low"};
	public static String[] doors = {"2","3","4","5more"};
	public static String[] person = {"2","4","more"};
	public static String[] size = {"small","med","big"};
	public static String[] safety = {"low","med","high"};
	public static String[] result = {"unacc","acc","vgood","good"};
	
	public static double[][] p_buying = new double[buying.length][result.length];
	public static double[][] p_main = new double[main.length][result.length];
	public static double[][] p_doors = new double[doors.length][result.length];
	public static double[][] p_person = new double[person.length][result.length];
	public static double[][] p_size = new double[size.length][result.length];
	public static double[][] p_safety = new double[safety.length][result.length];
	
	public static double c_unacc = 0;
	public static double c_acc = 0;
	public static double c_vgood = 0;
	public static double c_good = 0;
	public static double c_buy_vhigh_unacc = 0;
	public static double c_buy_vhigh_acc = 0;
	public static double c_buy_vhigh_vgood = 0;
	public static double c_buy_vhigh_good = 0;
	public static double c_buy_high_unacc = 0;
	public static double c_buy_high_acc = 0;
	public static double c_buy_high_vgood = 0;
	public static double c_buy_high_good = 0;
	public static double c_buy_vgood_unacc = 0;
	public static double c_buy_vgood_acc = 0;
	public static double c_buy_vgood_vgood = 0;
	public static double c_buy_vgood_good = 0;
	public static double c_buy_good_unacc = 0;
	public static double c_buy_good_acc = 0;
	public static double c_buy_good_vgood = 0;
	public static double c_buy_good_good = 0;
	public static double c_main_vhigh_unacc = 0;
	public static double c_main_vhigh_acc = 0;
	public static double c_main_vhigh_vgood = 0;
	public static double c_main_vhigh_good = 0;
	public static double c_main_high_unacc = 0;
	public static double c_main_high_acc = 0;
	public static double c_main_high_vgood = 0;
	public static double c_main_high_good = 0;
	public static double c_main_vgood_unacc = 0;
	public static double c_main_vgood_acc = 0;
	public static double c_main_vgood_vgood = 0;
	public static double c_main_vgood_good = 0;
	public static double c_main_good_unacc = 0;
	public static double c_main_good_acc = 0;
	public static double c_main_good_vgood = 0;
	public static double c_main_good_good = 0;
	public static double c_door_2_unacc = 0;
	public static double c_door_2_acc = 0;
	public static double c_door_2_vgood = 0;
	public static double c_door_2_good = 0;
	public static double c_door_3_unacc = 0;
	public static double c_door_3_acc = 0;
	public static double c_door_3_vgood = 0;
	public static double c_door_3_good = 0;
	public static double c_door_4_unacc = 0;
	public static double c_door_4_acc = 0;
	public static double c_door_4_vgood = 0;
	public static double c_door_4_good = 0;
	public static double c_door_5_unacc = 0;
	public static double c_door_5_acc = 0;
	public static double c_door_5_vgood = 0;
	public static double c_door_5_good = 0;
	public static double c_per_2_unacc = 0;
	public static double c_per_2_acc = 0;
	public static double c_per_2_vgood = 0;
	public static double c_per_2_good = 0;
	public static double c_per_4_unacc = 0;
	public static double c_per_4_acc = 0;
	public static double c_per_4_vgood = 0;
	public static double c_per_4_good = 0;
	public static double c_per_more_unacc = 0;
	public static double c_per_more_acc = 0;
	public static double c_per_more_vgood = 0;
	public static double c_per_more_good = 0;
	public static double c_small_unacc = 0;
	public static double c_small_acc = 0;
	public static double c_small_vgood = 0;
	public static double c_small_good = 0;
	public static double c_med_unacc = 0;
	public static double c_med_acc = 0;
	public static double c_med_vgood = 0;
	public static double c_med_good = 0;
	public static double c_big_unacc = 0;
	public static double c_big_acc = 0;
	public static double c_big_vgood = 0;
	public static double c_big_good = 0;
	public static double c_low_unacc = 0;
	public static double c_low_acc = 0;
	public static double c_low_vgood = 0;
	public static double c_low_good = 0;
	public static double c_sf_med_unacc = 0;
	public static double c_sf_med_acc = 0;
	public static double c_sf_med_vgood = 0;
	public static double c_sf_med_good = 0;
	public static double c_high_unacc = 0;
	public static double c_high_acc = 0;
	public static double c_high_vgood = 0;
	public static double c_high_good = 0;
	
	
	public static void readtrainFile() throws IOException {
		
		try{
			
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("car_train.txt"));
			
			while((line = reader.readLine()) != null)
			{
				String split[] = line.split(",");
				
				if(split[6].equals(result[0]))
				{
					c_unacc++;
					
					if(split[0].equals(buying[0])){ c_buy_vhigh_unacc ++; }
					else if(split[0].equals(buying[1])){ c_buy_high_unacc ++; }
					else if(split[0].equals(buying[2])){ c_buy_vgood_unacc ++; }
					else{c_buy_good_unacc ++;}
					
					if(split[1].equals(main[0])){ c_main_vhigh_unacc ++; }
					else if(split[1].equals(main[1])){ c_main_high_unacc ++;}
					else if(split[1].equals(main[2])){ c_main_vgood_unacc ++;}
					else{ c_main_good_unacc ++;}
					
					if(split[2].equals(doors[0])){ c_door_2_unacc ++; }
					else if(split[2].equals(doors[1])){ c_door_3_unacc ++;}
					else if(split[2].equals(doors[2])){ c_door_4_unacc ++;}
					else{ c_door_5_unacc ++;}
					
					if(split[3].equals(person[0])){ c_per_2_unacc ++; }
					else if(split[3].equals(person[1])){ c_per_4_unacc ++;}
					else{ c_per_more_unacc ++;}
					
					if(split[4].equals(size[0])){ c_small_unacc ++; }
					else if(split[4].equals(size[1])){ c_med_unacc ++;}
					else{ c_big_unacc ++;}
					
					if(split[5].equals(safety[0])){ c_low_unacc ++; }
					else if(split[5].equals(safety[1])){ c_sf_med_unacc ++;}
					else{ c_high_unacc ++;}
				}				
				else if(split[6].equals(result[1]))
				{
					c_acc++;
					
					if(split[0].equals(buying[0])){ c_buy_vhigh_acc ++; }
					else if(split[0].equals(buying[1])){ c_buy_high_acc ++; }
					else if(split[0].equals(buying[2])){ c_buy_vgood_acc ++; }
					else{c_buy_good_acc ++;}
					
					if(split[1].equals(main[0])){ c_main_vhigh_acc ++; }
					else if(split[1].equals(main[1])){ c_main_high_acc ++;}
					else if(split[1].equals(main[2])){ c_main_vgood_acc ++;}
					else{ c_main_good_acc ++;}
					
					if(split[2].equals(doors[0])){ c_door_2_acc ++; }
					else if(split[2].equals(doors[1])){ c_door_3_acc ++;}
					else if(split[2].equals(doors[2])){ c_door_4_acc ++;}
					else{ c_door_5_acc ++;}
					
					if(split[3].equals(person[0])){ c_per_2_acc ++; }
					else if(split[3].equals(person[1])){ c_per_4_acc ++;}
					else{ c_per_more_acc ++;}
					
					if(split[4].equals(size[0])){ c_small_acc ++; }
					else if(split[4].equals(size[1])){ c_med_acc ++;}
					else{ c_big_acc ++;}
					
					if(split[5].equals(safety[0])){ c_low_acc ++; }
					else if(split[5].equals(safety[1])){ c_sf_med_acc ++;}
					else{ c_high_acc ++;}
				}
				else if(split[6].equals(result[2]))
				{
					c_vgood++;
					
					if(split[0].equals(buying[0])){ c_buy_vhigh_vgood ++; }
					else if(split[0].equals(buying[1])){ c_buy_high_vgood ++; }
					else if(split[0].equals(buying[2])){ c_buy_vgood_vgood ++; }
					else{c_buy_good_vgood ++;}
					
					if(split[1].equals(main[0])){ c_main_vhigh_vgood ++; }
					else if(split[1].equals(main[1])){ c_main_high_vgood ++;}
					else if(split[1].equals(main[2])){ c_main_vgood_vgood ++;}
					else{ c_main_good_vgood ++;}
					
					if(split[2].equals(doors[0])){ c_door_2_vgood ++; }
					else if(split[2].equals(doors[1])){ c_door_3_vgood ++;}
					else if(split[2].equals(doors[2])){ c_door_4_vgood ++;}
					else{ c_door_5_vgood ++;}
					
					if(split[3].equals(person[0])){ c_per_2_vgood ++; }
					else if(split[3].equals(person[1])){ c_per_4_vgood ++;}
					else{ c_per_more_vgood ++;}
					
					if(split[4].equals(size[0])){ c_small_vgood ++; }
					else if(split[4].equals(size[1])){ c_med_vgood ++;}
					else{ c_big_vgood ++;}
					
					if(split[5].equals(safety[0])){ c_low_vgood ++; }
					else if(split[5].equals(safety[1])){ c_sf_med_vgood ++;}
					else{ c_high_vgood ++;}
				}
				else
				{
					c_good++;
					
					if(split[0].equals(buying[0])){ c_buy_vhigh_good ++; }
					else if(split[0].equals(buying[1])){ c_buy_high_good ++; }
					else if(split[0].equals(buying[2])){ c_buy_vgood_good ++; }
					else{c_buy_good_good ++;}
					
					if(split[1].equals(main[0])){ c_main_vhigh_good ++; }
					else if(split[1].equals(main[1])){ c_main_high_good ++;}
					else if(split[1].equals(main[2])){ c_main_vgood_good ++;}
					else{ c_main_good_good ++;}
					
					if(split[2].equals(doors[0])){ c_door_2_good ++; }
					else if(split[2].equals(doors[1])){ c_door_3_good ++;}
					else if(split[2].equals(doors[2])){ c_door_4_good ++;}
					else{ c_door_5_good ++;}
					
					if(split[3].equals(person[0])){ c_per_2_good ++; }
					else if(split[3].equals(person[1])){ c_per_4_good ++;}
					else{ c_per_more_good ++;}
					
					if(split[4].equals(size[0])){ c_small_good ++; }
					else if(split[4].equals(size[1])){ c_med_good ++;}
					else{ c_big_good ++;}
					
					if(split[5].equals(safety[0])){ c_low_good ++; }
					else if(split[5].equals(safety[1])){ c_sf_med_good ++;}
					else{ c_high_good ++;}
				}

			}
			
			System.out.println(c_unacc);
			System.out.println(c_acc);
			System.out.println(c_vgood);
			System.out.println(c_good);
			
			p_buying[0][0] = c_buy_vhigh_unacc/c_unacc;
			p_buying[0][1] = c_buy_vhigh_acc/c_acc;
			p_buying[0][2] = c_buy_vhigh_vgood/c_vgood;
			p_buying[0][3] = c_buy_vhigh_good/c_good;
			p_buying[1][0] = c_buy_high_unacc/c_unacc;
			p_buying[1][1] = c_buy_high_acc/c_acc;
			p_buying[1][2] = c_buy_high_vgood/c_vgood;
			p_buying[1][3] = c_buy_high_good/c_good;
			p_buying[2][0] = c_buy_vgood_unacc/c_unacc;
			p_buying[2][1] = c_buy_vgood_acc/c_acc;
			p_buying[2][2] = c_buy_vgood_vgood/c_vgood;
			p_buying[2][3] = c_buy_vgood_good/c_good;
			p_buying[3][0] = c_buy_good_unacc/c_unacc;
			p_buying[3][1] = c_buy_good_acc/c_acc;
			p_buying[3][2] = c_buy_good_vgood/c_vgood;
			p_buying[3][3] = c_buy_good_good/c_good;

			p_main[0][0] = c_main_vhigh_unacc/c_unacc;
			p_main[0][1] = c_main_vhigh_acc/c_acc;
			p_main[0][2] = c_main_vhigh_vgood/c_vgood;
			p_main[0][3] = c_main_vhigh_good/c_good;
			p_main[1][0] = c_main_high_unacc/c_unacc;
			p_main[1][1] = c_main_high_acc/c_acc;
			p_main[1][2] = c_main_high_vgood/c_vgood;
			p_main[1][3] = c_main_high_good/c_good;
			p_main[2][0] = c_main_vgood_unacc/c_unacc;
			p_main[2][1] = c_main_vgood_acc/c_acc;
			p_main[2][2] = c_main_vgood_vgood/c_vgood;
			p_main[2][3] = c_main_vgood_good/c_good;
			p_main[3][0] = c_main_good_unacc/c_unacc;
			p_main[3][1] = c_main_good_acc/c_acc;
			p_main[3][2] = c_main_good_vgood/c_vgood;
			p_main[3][3] = c_main_good_good/c_good;
			
			p_doors[0][0] = c_door_2_unacc/c_unacc;
			p_doors[0][1] = c_door_2_acc/c_acc;
			p_doors[0][2] = c_door_2_vgood/c_vgood;
			p_doors[0][3] = c_door_2_good/c_good;
			p_doors[1][0] = c_door_3_unacc/c_unacc;
			p_doors[1][1] = c_door_3_acc/c_acc;
			p_doors[1][2] = c_door_3_vgood/c_vgood;
			p_doors[1][3] = c_door_3_good/c_good;
			p_doors[2][0] = c_door_4_unacc/c_unacc;
			p_doors[2][1] = c_door_4_acc/c_acc;
			p_doors[2][2] = c_door_4_vgood/c_vgood;
			p_doors[2][3] = c_door_4_good/c_good;
			p_doors[3][0] = c_door_5_unacc/c_unacc;
			p_doors[3][1] = c_door_5_acc/c_acc;
			p_doors[3][2] = c_door_5_vgood/c_vgood;
			p_doors[3][3] = c_door_5_good/c_good;
			
			
			p_person[0][0] = c_per_2_unacc/c_unacc;
			p_person[0][1] = c_per_2_acc/c_acc;
			p_person[0][2] = c_per_2_vgood/c_vgood;
			p_person[0][3] = c_per_2_good/c_good;
			p_person[1][0] = c_per_4_unacc/c_unacc;
			p_person[1][1] = c_per_4_acc/c_acc;
			p_person[1][2] = c_per_4_vgood/c_vgood;
			p_person[1][3] = c_per_4_good/c_good;
			p_person[2][0] = c_per_more_unacc/c_unacc;
			p_person[2][1] = c_per_more_acc/c_acc;
			p_person[2][2] = c_per_more_vgood/c_vgood;
			p_person[2][3] = c_per_more_good/c_good;
			
			p_size[0][0] = c_small_unacc/c_unacc;
			p_size[0][1] = c_small_acc/c_acc;
			p_size[0][2] = c_small_vgood/c_vgood;
			p_size[0][3] = c_small_good/c_good;
			p_size[1][0] = c_med_unacc/c_unacc;
			p_size[1][1] = c_med_acc/c_acc;
			p_size[1][2] = c_med_vgood/c_vgood;
			p_size[1][3] = c_med_good/c_good;
			p_size[2][0] = c_big_unacc/c_unacc;
			p_size[2][1] = c_big_acc/c_acc;
			p_size[2][2] = c_big_vgood/c_vgood;
			p_size[2][3] = c_big_good/c_good;
			
			p_safety[0][0] = c_low_unacc/c_unacc;
			p_safety[0][1] = c_low_acc/c_acc;
			p_safety[0][2] = c_low_vgood/c_vgood;
			p_safety[0][3] = c_low_good/c_good;
			p_safety[1][0] = c_sf_med_unacc/c_unacc;
			p_safety[1][1] = c_sf_med_acc/c_acc;
			p_safety[1][2] = c_sf_med_vgood/c_vgood;
			p_safety[1][3] = c_sf_med_good/c_good;
			p_safety[2][0] = c_high_unacc/c_unacc;
			p_safety[2][1] = c_high_acc/c_acc;
			p_safety[2][2] = c_high_vgood/c_vgood;
			p_safety[2][3] = c_high_good/c_good;
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	

	
	public static void readtestFile() throws IOException {
		
		try{
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("car_test.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("car_test1.txt"));
			
			while((line = reader.readLine()) != null)
			{
				double result_unacc = 1;
				double result_acc = 1;
				double result_vgood = 1;
				double result_good = 1;
				
				String[] split = line.split(",");
				System.out.println(Arrays.toString(split));
			
				for(int i=0; i<buying.length; i++)
				{
					if(split[0].equals(buying[i])){
						result_unacc *= p_buying[i][0];
						result_acc *= p_buying[i][1];
						result_vgood *= p_buying[i][2];
						result_good *= p_buying[i][3]; 
					}
				}
				
				for(int i=0; i<main.length; i++)
				{
					if(split[1].equals(main[i])){
						result_unacc *= p_main[i][0];
						result_acc *= p_main[i][1];
						result_vgood *= p_main[i][2];
						result_good *= p_main[i][3]; 
					}
				}
				
				for(int i=0; i<doors.length; i++)
				{
					if(split[2].equals(doors[i])){
						result_unacc *= p_doors[i][0];
						result_acc *= p_doors[i][1];
						result_vgood *= p_doors[i][2];
						result_good *= p_doors[i][3]; 
					}
				}
				
				for(int i=0; i<person.length; i++)
				{
					if(split[3].equals(person[i])){
						result_unacc *= p_person[i][0];
						result_acc *= p_person[i][1];
						result_vgood *= p_person[i][2];
						result_good *= p_person[i][3]; 
					}
				}
				
				for(int i=0; i<size.length; i++)
				{
					if(split[4].equals(size[i])){
						result_unacc *= p_size[i][0];
						result_acc *= p_size[i][1];
						result_vgood *= p_size[i][2];
						result_good *= p_size[i][3]; 
					}
				}
				
				for(int i=0; i<safety.length; i++)
				{
					if(split[5].equals(safety[i])){
						result_unacc *= p_safety[i][0];
						result_acc *= p_safety[i][1];
						result_vgood *= p_safety[i][2];
						result_good *= p_safety[i][3]; 
					}
				}
				
				result_unacc *= c_unacc/(c_unacc+c_acc+c_vgood+c_good);
				result_acc *= c_acc/(c_unacc+c_acc+c_vgood+c_good);
				result_vgood *= c_vgood/(c_unacc+c_acc+c_vgood+c_good);
				result_good *= c_good/(c_unacc+c_acc+c_vgood+c_good);
				
				System.out.println(result_unacc);
				System.out.println(result_acc);
				System.out.println(result_vgood);
				System.out.println(result_good);
				
				if(result_unacc>result_acc && result_unacc>result_vgood && result_unacc>result_good){
		            String str = split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+","+split[5]+",unacc";		            
		            bw.write(str);
		            bw.newLine();
				}
				if(result_acc>result_unacc && result_acc>result_vgood && result_acc>result_good){
					String str = split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+","+split[5]+",acc";	            
		            bw.write(str);
		            bw.newLine();
				}
				if(result_vgood>result_unacc && result_vgood>result_acc && result_vgood>result_good){
					String str = split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+","+split[5]+",vgood";	            
		            bw.write(str);
		            bw.newLine();
				}
				if(result_good>result_unacc && result_good>result_acc && result_good>result_vgood){
					String str = split[0]+","+split[1]+","+split[2]+","+split[3]+","+split[4]+","+split[5]+",good";	            
		            bw.write(str);
		            bw.newLine();
				}
			}
			
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		readtrainFile();
		readtestFile();
		
	}
}