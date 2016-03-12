import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.WindowConstants;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WeatherAnalysis {

	public static class WeatherAnalysisMapper1 extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String data = value.toString();

			// Ignore the first line of each file, which contains headers
			if (data.substring(0, 1).matches("[0-9]")) {
				// to remove spaces and commas in dataset
				String[] dataVals = data.split("[ ,]+");
				String stationId = dataVals[0];

				// get the hour value, to identify sections
				int hour = Integer.parseInt(dataVals[2].substring(9, dataVals[2].length()));
				double temp = Double.parseDouble(dataVals[3]);
				double dewP = Double.parseDouble(dataVals[4]);
				double windSpeed = Double.parseDouble(dataVals[12]);

				String section = "0";
				if (hour >= 5 && hour < 11) {
					section = "1";
				} else if (hour >= 11 && hour < 17) {
					section = "2";
				} else if (hour >= 17 && hour < 23) {
					section = "3";
				} else {
					section = "4";
				}

				// Output key of Mapper1: Station ID, month and section
				Text outKey = new Text(stationId + ":" + dataVals[2].substring(4, 6) + ":" + section);
				// Output value of Mapper1: Temperature, dew point and windspeed
				Text outValue = new Text(temp + ":" + dewP + ":" + windSpeed);
				context.write(outKey, outValue);
			}
		}
	}

	public static class WeatherAnalysisReducer1 extends Reducer<Text, Text, Text, Text> {
		private static final double WINDSPEED_INVALID = 999.9;
		private static final double TEMP_DEW_INVALID = 9999.9;

		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			double avgTemp = 0.0;
			double avgDewP = 0.0;
			double avgWindSp = 0.0;

			double sumTemp = 0.0;
			double sumDewP = 0.0;
			double sumWindSp = 0.0;

			int tempCount = 0;
			int dewPCount = 0;
			int windSpcount = 0;

			for (Text dataVal : values) {
				String[] tdwValues = dataVal.toString().split(":");
				Double temp = Double.parseDouble(tdwValues[0]);
				Double dewP = Double.parseDouble(tdwValues[1]);
				Double windSp = Double.parseDouble(tdwValues[2]);

				// If any one of the values for the temperature, dew point or
				// wind speed is not provided, do not add the count for that
				// value
				if (temp != TEMP_DEW_INVALID) {
					sumTemp += temp;
					tempCount++;
				}
				if (dewP != TEMP_DEW_INVALID) {
					sumDewP += dewP;
					dewPCount++;
				}
				if (windSp != WINDSPEED_INVALID) {
					sumWindSp += windSp;
					windSpcount++;
				}
			}

			// Use this to truncate the value of the output to 4 decimal spaces
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);

			if (tempCount != 0)
				avgTemp = sumTemp / tempCount;
			if (dewPCount != 0)
				avgDewP = sumDewP / dewPCount;
			if (windSpcount != 0)
				avgWindSp = sumWindSp / windSpcount;

			// String[] keys = key.toString().split(":");

			// Output keys for Reducer1: stationID, month and section
			Text reducerOutputKey = key;

			// Output value of Reducer1: contains the average temperature, dew
			// point and wind speed for each station, for each section of time,
			// in a month

			Text reducerOutputVal = new Text(
					df.format(avgTemp) + "|" + df.format(avgDewP) + "|" + df.format(avgWindSp));

			context.write(reducerOutputKey, reducerOutputVal);
		}
	}

	public static class WeatherAnalysisMapper2 extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {

			// The input for this mapper is the output of reducer1.
			// the key and value are separated by spaces,
			String[] keyVal = value.toString().split("\\s+");

			String[] keys = keyVal[0].split(":");

			// Output key of Mapper2: Station ID and Month
			Text mapperOutputKey = new Text(keys[0] + ":" + keys[1]);
			// Output value of Mapper2: sectionID, average temp, dew point and
			// wind speed
			Text mapperOutputVal = new Text(keys[2] + ":" + keyVal[1]);
			context.write(mapperOutputKey, mapperOutputVal);
		}
	}

	public static class WeatherAnalysisReducer2 extends Reducer<Text, Text, Text, Text> {
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String finalOutput1 = "";
			String finalOutput2 = "";
			String finalOutput3 = "";
			String finalOutput4 = "";

			// Split the values for each key, to separate the section and
			// averages
			for (Text dataVal : values) {
				String[] valSplit = dataVal.toString().split(":");
				// Based on the section, decide the order of the output
				if (valSplit[0].equals("1")) {
					finalOutput1 = valSplit[1];
				} else if (valSplit[0].equals("2")) {
					finalOutput2 = valSplit[1];
				} else if (valSplit[0].equals("3")) {
					finalOutput3 = valSplit[1];
				} else {
					finalOutput4 = valSplit[1];
				}
			}

			String[] keys = key.toString().split(":");

			// Output keys of reducer2: Station ID and Month
			Text reducerOutputKey = new Text("StationID: " + keys[0] + " Month: " + keys[1]);
			// Output values of reducer2: Average temperature, dew point and
			// wind speed for each section of the day.
			Text reducerOutputVal = new Text(
					finalOutput1 + "|" + finalOutput2 + "|" + finalOutput3 + "|" + finalOutput4);

			context.write(reducerOutputKey, reducerOutputVal);
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration();

		Job job1 = new Job(conf, "WeatherAnalysis");
		job1.setMapperClass(WeatherAnalysisMapper1.class);
		job1.setReducerClass(WeatherAnalysisReducer1.class);
		job1.setJarByClass(WeatherAnalysis.class);

		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path(args[1]));

		job1.setInputFormatClass(TextInputFormat.class);
		job1.setOutputFormatClass(TextOutputFormat.class);

		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);

		boolean succ1 = job1.waitForCompletion(true);
		if (!succ1) {
			System.out.println("Job1 failed, exiting...");
			return;
		}

		Job job2 = new Job(conf, "WeatherReportFinal");
		job2.setMapperClass(WeatherAnalysisMapper2.class);
		job2.setReducerClass(WeatherAnalysisReducer2.class);
		job2.setJarByClass(WeatherAnalysis.class);

		FileInputFormat.addInputPath(job2, new Path(args[1]));
		FileOutputFormat.setOutputPath(job2, new Path(args[2]));

		job2.setInputFormatClass(TextInputFormat.class);
		job2.setOutputFormatClass(TextOutputFormat.class);

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		boolean succ2 = job2.waitForCompletion(true);
		if (!succ2) {
			System.out.println("Job2 failed, exiting...");
			return;
		}

	}

}
