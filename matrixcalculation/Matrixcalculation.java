package lab04;

import java.io.IOException;
import java.util.Iterator;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Matrixcalculation{
	
	private static int row;
	private static int col;
	private static int row_col;
	/*
	 * MapperClass
	 */
	public static class MapperClass
		extends Mapper<Object,Text, Text, Text> {

		private Text emitKey = new Text();
		private Text emitVal = new Text();
		
		int row = 0;
		int col = 0;
		
		protected void setup(Context context) throws IOException, InterruptedException {
			Configuration config = context.getConfiguration();
			
			row = Integer.parseInt(config.get("row"));
			col = Integer.parseInt(config.get("col"));

		}

		// map function (Object, Text : input key-value pair
		//               Context : fixed parameter)
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] arr = value.toString().split ("\t");
			
			if(arr[0].equals("A"))
			{
				for(int k = 0; k < col; k++)
				{
					emitKey.set(arr[1]+ "," + k);
					emitVal.set(arr[2]+ "," + arr[3]);
					context.write(emitKey, emitVal);
				}
				
			}
			else
			{
				for(int i = 0; i < row; i++)
				{
					emitKey.set(i + "," +arr[2]);
					emitVal.set(arr[1] + "," +arr[3]);
					context.write(emitKey, emitVal);
				}
			}
			
		}
	}
	
	/*
	 * ReducerClass
	 */
	public static class ReducerClass extends Reducer<Text, Text, Text, IntWritable> {

		private IntWritable result = new IntWritable();
		
		int row_col = 0;
		
		protected void setup(Context context) throws IOException, InterruptedException {
			Configuration config = context.getConfiguration();
			
			row_col = Integer.parseInt(config.get("row_col"));
		}
		
		public void reduce(Text key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {
			
			Iterator<Text> iter = values.iterator();
			
			int[] e_value = new int[row_col];
			
			for(int i = 0; i<row_col; i++)
			{
				e_value[i] = 1;
			}
				
			
			int m_multi = 0;
						
		     while (iter.hasNext()) {
		    	 String [] value = iter.next().toString().split (",");
		    	 e_value[Integer.parseInt(value[0])] *= Integer.parseInt(value[1]);
		     }
		     
		     for(int i = 0; i<row_col; i++)
		     {
		    	 m_multi += e_value[i];
		     }
			
			result.set(m_multi);
			context.write(key,result);
		}

	}

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		if ( otherArgs.length != 5 ) {
			System.err.println("Usage: matrixcalculation <row> <row_col> <col> <in> <out>");
			System.exit(1);
		}
		
		row = Integer.parseInt(otherArgs[0]);
		row_col = Integer.parseInt(otherArgs[1]);
		col = Integer.parseInt(otherArgs[2]);

		conf.setInt ("row", row);
		conf.setInt ("row_col", row_col);
		conf.setInt ("col", col);

		Job job = new Job(conf,"Matrixcalculation");
		job.setJarByClass(Matrixcalculation.class);
		
		// let hadoop know the map and reduce classes
		job.setMapperClass(MapperClass.class);
		job.setReducerClass(ReducerClass.class);
		
		job.setMapOutputValueClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		// let hadoop know the key-value pair type
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		Path outdir = new Path (otherArgs[4]);
		if (FileSystem.get(conf).exists(outdir)) {
			FileSystem.get(conf).delete (outdir);
		}

		// read the output of reduce function to obtain the updated cluster centers
		// the updated cluster centers are broadcasted again
		FileInputFormat.addInputPath(job,new Path(otherArgs[3]));
		FileOutputFormat.setOutputPath(job,new Path(otherArgs[4]));
		
		job.waitForCompletion(true);

	}
}
