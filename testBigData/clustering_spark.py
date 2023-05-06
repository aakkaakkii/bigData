import numpy as np
import datetime as dt

from pyspark import SparkConf, SparkContext
from sklearn.ensemble import IsolationForest
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, LSTM
from pyspark.sql import SparkSession
import json

conf = SparkConf().setAppName("myFirstApp").setMaster("local")
sc = SparkContext(conf=conf)

df = sc.textFile("hdfs://localhost:9000//topics/test-topic5/partition=0/*")

def prepare_data(x):
    json_data = json.loads(x)
    return {"symbol": json_data['symbol'], "prices": list(map(lambda x: x[1], json_data['prices']))}


def cluster(cluster_data):
    data = cluster_data["data"]

    df = pd.DataFrame(data=data, columns=["from", "gas", "gasPrice", "gasUsed", "to", "value"])

    onehot_encoder = OneHotEncoder(sparse=False)

    string_df = df[["from", "to"]]
    string_numpy = string_df.to_numpy()
    onehot = onehot_encoder.fit_transform(string_numpy)
    number_df = pd.DataFrame(data=onehot)
    number_df["gas"] = df["gas"]
    number_df["gasPrice"] = df["gasPrice"]
    number_df["gasUsed"] = df["gasUsed"]
    number_df["value"] = df["value"]

    clf = IsolationForest(contamination=0.10, max_features=11, max_samples=len(data))

    clf.fit(number_df)

    y_pred_train = clf.predict(number_df)
    number_df["classification"] = y_pred_train
    ntc = number_df[ "classification"].where(number_df[ "classification"] == 1).count()
    nntc = number_df[ "classification"].where(number_df[ "classification"] == -1).count()
    signal = 1 if (nntc / (nntc + ntc)) * 100 < 15 else 0

    return {"symbol": cluster_data["symbol"], "signal": signal}


df\
    .map(prepare_data)\
    .map(cluster)\
    .saveAsTextFile( "hdfs://localhost:9000/price/cluster")