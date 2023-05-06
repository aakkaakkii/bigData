import org.apache.spark.sql.{SaveMode, SparkSession}
import play.api.libs.json.Json

case class MovieUserRating(movieId: Int, movieRating:Int, userId: Int, tmstmp: Long )

//hadoop fs -mv  "/topics/test-topic4/partition=0/*" /price/tmp

object PriceReducer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Spark Structured Streaming with Kafka Demo")
      .config("spark.cassandra.connection.host", "localhost")
      .getOrCreate()

    import spark.implicits._

    val inputDF = spark.read.text("hdfs://localhost:9000/price/tmp/")

    val rawDF = inputDF.selectExpr("CAST(value AS STRING)").as[String]

    val data = rawDF.map(data => {
      val parsedJson = Json.parse(data);
      MovieUserRating(
        parsedJson("movieId").toString().toInt,
        parsedJson("movieRating").toString().toInt,
        parsedJson("userId").toString().toInt,
        parsedJson("timestamp").toString().toLong )
    }).map(data => s"${data.userId},${data.movieId},${data.movieRating},${data.tmstmp}")

    data.coalesce(1).write.option("sep","\t")
      .mode(SaveMode.Overwrite)
      .csv("hdfs://localhost:9000/movies/tmp2/res")

  }
}
