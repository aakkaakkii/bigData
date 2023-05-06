import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class Main {
    public static void main(String[] args) throws Exception{
        Job job = Job.getInstance(new Configuration(), "friendsFinder");

        job.setJarByClass(Main.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        job.setMapperClass(FriendsMapper.class);
        job.setReducerClass(FriendsReducer.class);

        job.setMapOutputKeyClass(FriendPair.class);
        job.setMapOutputValueClass(FriendArray.class);

        job.setOutputKeyClass(FriendPair.class);
        job.setOutputValueClass(FriendArray.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
