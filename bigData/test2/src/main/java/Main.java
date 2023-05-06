import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Main {
    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) throws Exception{
//        /logins/logins-2012-10-25.txt
//        InputStream in = null;
//        in = new URL("hdfs://localhost:9000/logins/logins-2012-10-25.txt").openStream();
//        in = new URL("hdfs://localhost:9870/logins/logins-2012-10-25.txt").openStream();
//        IOUtils.copyBytes(in, System.out, 4096, false);


        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");


        FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.0.130:9000/test/stock.txt"), conf);
        FSDataInputStream in = null;

        try {
            in = fs.open(new Path("hdfs://192.168.0.130:9000/test/stock.txt"));
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }
}
