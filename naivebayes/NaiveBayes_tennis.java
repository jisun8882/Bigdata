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

public class NaiveBayes_tennis{
	
	public static String[] outlook = {"Sunny","Overcast","Rain"};
	public static String[] temperature = {"Hot","Mild","Cool"};
	public static String[] humidity = {"High" , "Normal"};
	public static String[] wind = {"Strong","Weak"};
	public static String[] result = {"Yes","No"};
	
	public static double[][] p_outlook = new double[outlook.length][result.length];
	public static double[][] p_temperature = new double[temperature.length][result.length];
	public static double[][] p_humidity = new double[humidity.length][result.length];
	public static double[][] p_wind = new double[wind.length][result.length];
	public static double c_num = 0;
	public static double c_yes = 0;
	public static double c_no = 0;
	public static double c_sunny_yes = 0;
	public static double c_overcast_yes = 0;
	public static double c_rain_yes = 0;
	public static double c_cool_yes = 0;
	public static double c_mild_yes = 0;
	public static double c_hot_yes = 0;
	public static double c_high_yes = 0;
	public static double c_normal_yes = 0;
	public static double c_weak_yes = 0;
	public static double c_strong_yes = 0;
	public static double c_sunny_no = 0;
	public static double c_overcast_no = 0;
	public static double c_rain_no = 0;
	public static double c_cool_no = 0;
	public static double c_mild_no = 0;
	public static double c_hot_no = 0;
	public static double c_high_no = 0;
	public static double c_normal_no = 0;
	public static double c_weak_no = 0;
	public static double c_strong_no = 0;
	
	
	public static void readtrainFile() throws IOException {
		
		try{
			
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("play-tennis_train.txt"));
			
			while((line = reader.readLine()) != null)
			{
				String split[] = line.split(",");
				
				if(split[4].equals(result[0]))
				{
					c_yes++;
					
					if(split[0].equals(outlook[0])){ c_sunny_yes ++; }
					else if(split[0].equals(outlook[1])){ c_overcast_yes ++; }
					else{c_rain_yes ++;}
					
					if(split[1].equals(temperature[0])){ c_hot_yes ++; }
					else if(split[1].equals(temperature[1])){ c_mild_yes ++;}
					else{ c_cool_yes ++;}
					
					if(split[2].equals(humidity[0])){ c_high_yes ++; }
					else{ c_normal_yes ++; }
					
					if(split[3].equals(wind[0])){c_strong_yes ++;}
					else{ c_weak_yes ++; }
				}				
				else
				{
					c_no++;
					
					if(split[0].equals(outlook[0])){ c_sunny_no ++; }
					else if(split[0].equals(outlook[1])){ c_overcast_no ++; }
					else{c_rain_no ++;}
					
					if(split[1].equals(temperature[0])){ c_hot_no ++; }
					else if(split[1].equals(temperature[1])){ c_mild_no ++;}
					else{ c_cool_no ++;}
					
					if(split[2].equals(humidity[0])){ c_high_no ++; }
					else{ c_normal_no ++; }
					
					if(split[3].equals(wind[0])){c_strong_no ++;}
					else{ c_weak_no ++; }
				}
				
			}
			
			
			p_outlook[0][0] = c_sunny_yes/c_yes;
			p_outlook[0][1] = c_sunny_no/c_no;
			p_outlook[1][0] = c_overcast_yes/c_yes;
			p_outlook[1][1] = c_overcast_no/c_no;
			p_outlook[2][0] = c_rain_yes/c_yes;
			p_outlook[2][1] = c_rain_no/c_no;
			
			p_temperature[0][0] = c_hot_yes/c_yes;
			p_temperature[0][1] = c_hot_no/c_no;
			p_temperature[1][0] = c_mild_yes/c_yes;
			p_temperature[1][1] = c_mild_no/c_no;
			p_temperature[2][0] = c_cool_yes/c_yes;
			p_temperature[2][1] = c_cool_no/c_no;
			
			p_humidity[0][0] = c_high_yes/c_yes;
			p_humidity[0][1] = c_high_no/c_no;
			p_humidity[1][0] = c_normal_yes/c_yes;
			p_humidity[1][1] = c_normal_no/c_no;
			
			p_wind[0][0] = c_strong_yes/c_yes;
			p_wind[0][1] = c_strong_no/c_no;
			p_wind[1][0] = c_weak_yes/c_yes;
			p_wind[1][1] = c_weak_no/c_no;
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public static double result_yes = 1;
	public static double result_no = 1;

	
	public static void readtestFile() throws IOException {
		
		try{
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("play-tennis_test.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("play-tennis_test1.txt"));
			
			while((line = reader.readLine()) != null)
			{
				double result_yes = 1;
				double result_no = 1;
				
				String[] split = line.split(",");
				System.out.println(Arrays.toString(split));
			
				for(int i=0; i<outlook.length; i++)
				{
					if(split[0].equals(outlook[i])){
						result_yes *= p_outlook[i][0];
						result_no *= p_outlook[i][1];
					}
				}
				
				for(int i=0; i<temperature.length; i++)
				{
					if(split[1].equals(temperature[i])){
						result_yes *= p_temperature[i][0];
						result_no *= p_temperature[i][1];
					}
				}
				
				for(int i=0; i<humidity.length; i++)
				{
					if(split[2].equals(humidity[i])){
						result_yes *= p_humidity[i][0];
						result_no *= p_humidity[i][1];
					}
				}
				
				for(int i=0; i<wind.length; i++)
				{
					if(split[3].equals(wind[i])){
						result_yes *= p_wind[i][0];
						result_no *= p_wind[i][1];
					}
				}
				
				result_yes *= c_yes/(c_yes+c_no);
				result_no *= c_no/(c_yes+c_no);
				
				System.out.println(result_yes);
				System.out.println(result_no);
				
				if(result_yes>result_no){
		            String str = split[0]+","+split[1]+","+split[2]+","+split[3]+",Yes";		            
		            bw.write(str);
		            bw.newLine();
				}
				else{
		            String str = split[0]+","+split[1]+","+split[2]+","+split[3]+",No";		            
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