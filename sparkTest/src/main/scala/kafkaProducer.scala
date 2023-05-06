import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp
import play.api.libs.json.{Json, OWrites}

import java.util.{Date, Properties}
import scala.util.Random

object kafkaProducer {
  def main(args: Array[String]): Unit = {
        val topic = "movietopic1"
        val kafkaBootstrapServer = "localhost:9092"

        val props = new Properties()

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer)
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

        val producer = new KafkaProducer[String, String](props)

        while (true){
          val jsonMessage =  Json.toJson(dataGenerator())
          val record = new ProducerRecord[String, String](topic, jsonMessage.toString())

          println(record.toString)
          producer.send(record)
          Thread.sleep(2000)
        }

/*    val jsonMessage =  Json.toJson(dataGenerator())
    val record = new ProducerRecord[String, String](topic, jsonMessage.toString())

    println(record.toString)
    producer.send(record)
        producer.close()*/
  }



  def dataGenerator(): MovieRating = {
    MovieRating(Random.nextInt(10),  Random.nextInt(10), Random.nextInt(20), new Date().getTime)
  }

}

object MovieRating {
  implicit val writes: OWrites[MovieRating] = OWrites {
    movieRating => {
      Json.obj(
        "movieId" -> movieRating.movieId,
        "movieRating" -> movieRating.movieRating,
        "userId" -> movieRating.userId,
        "timestamp"-> movieRating.timestamp
      )
    }
  }
}

case class MovieRating(movieId: Int, movieRating: Int, userId: Int, timestamp: Timestamp)
