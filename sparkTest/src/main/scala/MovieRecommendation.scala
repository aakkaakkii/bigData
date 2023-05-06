import org.apache.spark.ml.recommendation.ALSModel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.Rating

object MovieRecommendation {
  def main(args: Array[String]): Unit = {

    val sparcConfig = new SparkConf()
    sparcConfig.setAppName("MovieRecommendation")
    val ctx = new SparkContext(sparcConfig)

    val data = ctx.textFile("hdfs://localhost:9000/movies/master/ratings_small.csv")

    val header = data.first()
    val ratings2 = data.map(data => " " + data)

    val ratings = data.filter(row => row != header).map(_.split(",") match {
      case  Array(user, movie, rate, timestamp ) => {
        Rating(user.toInt, movie.toInt, rate.toDouble)
      }
    })

//    val myRatingsRDD = movie

/*    val training = ratings.filter { case Rating(userId, movieId, rating) => (userId * movieId) % 10 <= 3 }.persist
    val test = ratings.filter { case Rating(userId, movieId, rating) => (userId * movieId) % 10 > 3}.persist*/

    val rank = 8
    val iteration = 10
    val lambda = 0.01

    val model = ALS.train(ratings, rank, iteration, lambda)

    model.save(ctx, "hdfs://localhost:9000/movies/master/trained_model")

/*    val recommendedMoviesId = model.predict(ratings.map { product => (0, product)})
      .map { case Rating(user,movie,rating) => (movie,rating) }
      .sortBy( x => x._2, ascending = false).take(20).map( x => x._1)

                ALSModel.load("hdfs://localhost:9000/movies/master/trained_model")


    model.save(ctx, "hdfs://localhost:9000/movies/master/trained_model")*/

  }
}
