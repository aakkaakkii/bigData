import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

//date open high low close volume Name

/*
    RUN
    hadoop jar ~/programming/java/bigData/stockMR/target/stockMR-1.0-SNAPSHOT.jar Main /stock/stock.csv /output/stocks
 */

public class Main {
    public static void main(String[] args) throws Exception{
        if (args.length != 2) {
            System.err.println("Need Two Args");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(Main.class);
        job.setJobName("MaxClosePrice");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapperClass(MaxClosePriceMapper.class);
        job.setReducerClass(MaxClosePriceReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

