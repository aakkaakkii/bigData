import org.apache.spark.sql.{DataFrame, Encoders, Row, SaveMode, SparkSession}

import scala.collection.mutable
//import play.api.libs.json.Json

case class MarketChart(symbol: String, market_caps: List[List[Double]], prices: List[List[Double]], total_volumes: List[List[Double]])
case class PriceData(date:Double, price:Double)
case class DataContainer(date:Double, value:Double)
case class CalculatedData2(macd: mutable.WrappedArray[DataContainer])
case class CalculatedData(macd: mutable.WrappedArray[DataContainer],
                          macdSignal:mutable.WrappedArray[DataContainer],
                          ema10:mutable.WrappedArray[DataContainer],
                          ema40:mutable.WrappedArray[DataContainer],
                          rsi:Double)


// MACD RSI EMA correlation

object PricePrediction {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Spark Structured Streaming with Kafka Demo")
      .config("spark.cassandra.connection.host", "localhost")
      .getOrCreate()

    import spark.implicits._

    val inputDF = spark.read.text("hdfs://localhost:9000/price/tmp")


    val df: DataFrame = spark.sqlContext.read.json("hdfs://localhost:9000/price/tmp").select("symbol","prices" )
    val prices: DataFrame = spark.sqlContext.read.json("hdfs://localhost:9000/price/tmp").select("prices" )
    val symbol: DataFrame = spark.sqlContext.read.json("hdfs://localhost:9000/price/tmp").select("symbol" )
//    df.show()

    val df2 = symbol.join(prices)
//    df2.show()


//    val test =prices.map {
//      case Row(data:mutable.WrappedArray[mutable.WrappedArray[Double]]) =>{
//        println("ssssssssssss")
//        println("ssssssssssss")
//        println("ssssssssssss")
//        println("ssssssssssss")
//        println("ssssssssssss")
//        println("ssssssssssss")
//        data.map(e => {
//          println("11111")
//
//          e(0)
//          new PriceData(e(0), e(1))
//        })
//      }
//    }.flatMap(e=> {
//      e
//    })


/*    val test =prices.map {
      case Row(data:mutable.WrappedArray[mutable.WrappedArray[Double]]) =>{
        val priceData = data.map(e => {
          new PriceData(e(0), e(1))
        })
        var oldValueEMA10 = 0.0;
                val ema10 = priceData.zipWithIndex.map {
                  case (e, index) => {
                    var ema:Double = 0;
                    val price = e.price;
                    if (index == 0) {
                      oldValueEMA10 = price
                    } else if(index > 10) {
                      val factor = 2.0 / (10 + 1)

                      ema = price * factor + oldValueEMA10 * (1 - factor)
                      oldValueEMA10 = ema;
                      println(ema, price, factor)
                    }

                    DataContainer(e.date, ema)
//                    ema
                  }
                }
        println("ssss")
        println("ssss")
        println("ssss")
        println("ssss")
        println("ssss")
        println("ssss")
        println("ssss")
                println(ema10)
        PriceData(5, 3)
      }
    }*/


    val test =prices.map {
      case Row(data: mutable.WrappedArray[mutable.WrappedArray[Double]]) => {
        val priceData = data.map(e => {
          new PriceData(e(0), e(1))
        })

        var oldValueEMA10 = 0.0;
        val ema10 = priceData.zipWithIndex.map {
          case (e, index) => {
            var ema:Double = 0;
            val price = e.price;
            if (index == 0) {
              oldValueEMA10 = price
            } else if(index >= 10) {

              val factor = 2.0 / (10 + 1)

              ema = price * factor + oldValueEMA10 * (1 - factor)
              oldValueEMA10 = ema;
            }
            DataContainer(e.date, ema)
          }
        }


        //EMA 40
        var oldValueEMA40 = 0.0;
        val ema40 = priceData.zipWithIndex.map {
          case (e, index) => {
            var ema:Double = 0;
            val price = e.price;
            if (index == 0) {
              oldValueEMA40 = price
            } else if(index >= 40) {
              val factor = 2.0 / (40 + 1)
              ema = price * factor + oldValueEMA40 * (1 - factor)
              oldValueEMA40 = ema;
            }
            DataContainer(e.date, ema)
          }
        }

        //MACD
        //EMA12
        var oldValueEMA12 = 0.0;
        //EMA26
        var oldValueEMA26 = 0.0;
//        val ema12 = priceData.zipWithIndex.map {
//          case (e, index) => {
//            val price = e.price;
//            var ema:Double = 0;
//            if (index == 0) {
//              oldValueEMA12 = price
//            } else if(index >= 12) {
//              val factor = 2.0 / (12 + 1)
//              ema = price * factor + oldValueEMA12 * (1 - factor)
//              oldValueEMA12 = ema;
//            }
//            ema
//          }
//        }
//        val ema26 = priceData.zipWithIndex.map {
//          case (e, index) => {
//            val price = e.price;
//            var ema: Double = 0;
//            if (index == 0) {
//              oldValueEMA26 = price
//            } else if (index >= 26) {
//            val factor = 2.0 / (26 + 1)
//            ema = price * factor + oldValueEMA26 * (1 - factor)
//            oldValueEMA26 = ema;
//          }
//            ema
//          }
//        }

        val macd = priceData.zipWithIndex.map {
          case (e, index) => {
            val price = e.price;
            var ema12:Double = 0;
            var ema26:Double = 0;

            if (index == 0) {
              oldValueEMA12 = price
              oldValueEMA26 = price
            } else if(index >= 26) {
              val factor12 = 2.0 / (12 + 1)
              val factor26 = 2.0 / (26 + 1)
              ema12 = price * factor12 + oldValueEMA12 * (1 - factor12)
              ema26 = price * factor26 + oldValueEMA26 * (1 - factor26)
              oldValueEMA12 = ema12;
              oldValueEMA26 = ema26;
            }

            DataContainer(e.date, ema12 - ema26)
          }
        }

        var oldValueEMA9 = 0.0;
        val macdSignal = macd.zipWithIndex.map {
          case (e, index) => {
            var ema:Double = 0;
            if (index == 0) {
              oldValueEMA9 = e.value
            } else if(index >= 9) {
              val factor = 2.0 / (9 + 1)
              ema = e.value * factor + oldValueEMA9 * (1 - factor)
              oldValueEMA9 = ema;
            }
            DataContainer(e.date, ema)
          }
        }



        //RSI
        var negative = 0.0;
        var positive = 0.0;
        val negativeValues = priceData.map(e => {
          val price = e.price;
          if (negative == 0){
            negative = price
          }
          val diff = price - negative
          if (diff < 0) e.price else 0
        })
        val positiveValues = priceData.map(e => {
          val price = e.price;
          if (positive == 0){
            positive = price
          }
          val diff = price - positive
          if (diff > 0) e.price else 0
        })

        val sumNegative = negativeValues.sum;
        val sumPositive = positiveValues.sum;
        val rsi = 100 - (100/(1+ (sumNegative/negativeValues.length) / (sumPositive/positiveValues.length)))


        macd.foreach(e=> {
          println(e.value)
        })

        CalculatedData(macd, macdSignal, ema10, ema40, rsi)
      }
    }


    val result = symbol.join(test)
    result.show()
//    result.write.json("hdfs://localhost:9000/price/test3")

//    result.show()

    //        val rawDF = inputDF.selectExpr("CAST(value AS STRING)").as[String]

    //    val data = rawDF.map(data => {
    //      val parsedJson = Json.parse(data);
    //      MarketChart(
    //        parsedJson("symbol").toString(),
    //        parsedJson("market_caps").as[List[List[Double]]],
    //        parsedJson("prices").as[List[List[Double]]],
    //        parsedJson("total_volumes").as[List[List[Double]]])
    //    })

    //    val data = rawDF.map(data => {
    //      val parsedJson = Json.parse(data);
    //        parsedJson("prices").as[List[List[Double]]]
    //    })
    //
    //    data.show();
  /*  val tmpPrices = df.first().getAs[mutable.WrappedArray[mutable.WrappedArray[Double]]](0);


    //EMA 10
    var oldValueEMA10 = 0.0;
    val ema10 = tmpPrices.zipWithIndex.map {
      case (e, index) => {
        val price = e(1);
        if (index == 0) {
          oldValueEMA10 = price
          return 0
        } else if(index < 10) {
          return 0
        }
        val factor = 2.0 / (10 + 1)

        val ema = price * factor + oldValueEMA10 * (1 - factor)
        oldValueEMA10 = ema;
        println(ema, price, factor)
        ema
      }
    }

    //EMA 40
    var oldValueEMA40 = 0.0;
    val ema40 = tmpPrices.zipWithIndex.map {
      case (e, index) => {
        val price = e(1);
        if (index == 0) {
          oldValueEMA40 = price
          return 0
        } else if(index < 40) {
          return 0
        }
        val factor = 2.0 / (40 + 1)
        val ema = price * factor + oldValueEMA40 * (1 - factor)
        oldValueEMA40 = ema;
        println(ema, price, factor)
        ema
      }
    }

    //MACD
    //EMA12
    var oldValueEMA12 = 0.0;
    //EMA26
    var oldValueEMA26 = 0.0;
    val ema12 = tmpPrices.zipWithIndex.map {
      case (e, index) => {
        val price = e(1);
        if (index == 0) {
          oldValueEMA12 = price
          return 0
        } else if(index < 12) {
          return 0
        }
        val factor = 2.0 / (12 + 1)
        val ema = price * factor + oldValueEMA12 * (1 - factor)
        oldValueEMA12 = ema;
        ema
      }
    }
    val ema26 = tmpPrices.zipWithIndex.map {
      case (e, index) => {
        val price = e(1);
        if (index == 0) {
          oldValueEMA26 = price
          return 0
        } else if(index < 26) {
          return 0
        }
        val factor = 2.0 / (26 + 1)
        val ema = price * factor + oldValueEMA26 * (1 - factor)
        oldValueEMA26 = ema;
        ema
      }
    }

    val macd = tmpPrices.zipWithIndex.map {
      case (e, index) => {
        val price = e(1);
        if (index == 0) {
          oldValueEMA12 = price
          oldValueEMA26 = price
          return 0
        } else if(index < 26) {
          return 0
        }
        val factor12 = 2.0 / (26 + 1)
        val factor26 = 2.0 / (26 + 1)
        val ema12 = price * factor12 + oldValueEMA12 * (1 - factor12)
        val ema26 = price * factor26 + oldValueEMA26 * (1 - factor26)
        oldValueEMA12 = ema12;
        oldValueEMA26 = ema26;
        ema12 - ema26
      }
    }

    var oldValueEMA9 = 0.0;
    val macdSignal = macd.zipWithIndex.map {
      case (e, index) => {
        if (index == 0) {
          oldValueEMA9 = e
          return 0
        } else if(index < 9) {
          return 0
        }
        val factor = 2.0 / (9 + 1)
        val ema = e * factor + oldValueEMA9 * (1 - factor)
        oldValueEMA9 = ema;
        ema
      }
    }



    //RSI
    var negative = 0.0;
    var positive = 0.0;
    val negativeValues = tmpPrices.map(e => {
      val price = e(1);
      if (negative == 0){
        negative = price
      }
      val diff = price - negative
      if(diff < 0) {
        return e(1)
      } else {
        0
      }
    })
    val positiveValues = tmpPrices.map(e => {
      val price = e(1);
      if (positive == 0){
        positive = price
      }
      val diff = price - positive
      if(diff > 0) {
        return e(1)
      } else {
        0
      }
    })

    val sumNegative = negativeValues.sum;
    val sumPositive = positiveValues.sum;
    val rsi = 100 - (100/(1+ (sumNegative/negativeValues.length) / (sumPositive/positiveValues.length)))



    df.show()
*/

  }
}
