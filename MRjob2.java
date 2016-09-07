 
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;

public class MRjob2 {


 public static class MapSort extends Mapper<Text, Text, IntWritable, Text> {

   		public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
			context.write(new IntWritable(Integer.parseInt(value.toString())), key);			
		}

	}

	public static class ReducerSort extends Reducer<IntWritable, Text, Text, IntWritable> {

		public void reduce(IntWritable uricount,Iterable<Text> uri, Context context) throws IOException, InterruptedException {
				for(Text eachuri: uri)
					context.write(eachuri, uricount);
			
		}

   }
	
	public static void main(String[] args) throws Exception {

	Configuration conf1 = new Configuration();
    Job job1 = Job.getInstance(conf1, "uri sort");
    job1.setJarByClass(MRjob2.class);
    job1.setMapperClass(MapSort.class);
    job1.setReducerClass(ReducerSort.class);
    job1.setMapOutputKeyClass(IntWritable.class);
    job1.setMapOutputValueClass(Text.class);
    job1.setInputFormatClass(KeyValueTextInputFormat.class);
    job1.setOutputKeyClass(Text.class);
    job1.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job1, new Path(args[0]));
    FileOutputFormat.setOutputPath(job1, new Path(args[1]));
    System.exit(job1.waitForCompletion(true)? 0:1);
    }
  }

 
