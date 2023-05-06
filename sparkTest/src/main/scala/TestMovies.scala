import org.apache.commons.math3.analysis.interpolation.LinearInterpolator
import org.apache.commons.math3.ode.sampling.StepInterpolator
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.recommendation.ALSModel
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction

object TestMovies {
  def main(args: Array[String]): Unit = {
    var linearInterpolator:LinearInterpolator = new LinearInterpolator();
    val psfSeaWater = linearInterpolator.interpolate(dates, deltas)
    psfSeaWater.derivative()

    val sparcConfig = new SparkConf()
    sparcConfig.setAppName("MovieRecommendation")
    val ctx = new SparkContext(sparcConfig)

    val model = MatrixFactorizationModel.load(ctx,"hdfs://localhost:9000/movies/master/trained_model")

    val movies = model.recommendProducts(2, 5)

    println(";;;;;;;;;;;;;;;;;;;;;;;;;")
    movies.foreach(r => {
      println(r.user, r.product, r.rating)
    })
  }
}

