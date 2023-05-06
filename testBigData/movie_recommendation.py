from pyspark.ml.recommendation import ALS
from pyspark.sql import SparkSession
from pyspark.sql.types import StructType,StructField, StringType
import json
spark = SparkSession.builder.appName("test22").getOrCreate()

df = spark.read.text("hdfs://localhost:9000/price/tmp")

prices = df.select('value').rdd.flatMap(lambda x: x).collect()

data = prices[0]
json.loads(data)['prices']

print("ratings")
print("ratings")
print("ratings")
print("ratings")
print("ratings")
print("ratings")
print("ratings")
print("ratings")
print("ratings")
print("ratings")
print()
