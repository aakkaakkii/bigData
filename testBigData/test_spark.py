import numpy as np
import datetime as dt

from pyspark import SparkConf, SparkContext
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, LSTM
from pyspark.sql import SparkSession
import json

# spark = SparkSession.builder.appName("test22").getOrCreate()
conf = SparkConf().setAppName( "myFirstApp" ).setMaster( "local" )
sc = SparkContext(conf=conf)

df = sc.textFile( "hdfs://localhost:9000//topics/test-topic4/partition=0/*")


def prepare_data( x ):
    json_data = json.loads( x)
    return { "symbol": json_data[ 'symbol' ], "prices": list( map( lambda x: x[1], json_data[ 'prices ']) ) }


def predict( data):
    last_price = data[ "prices"][ len (data["prices"] ) - 1]
    prices = np.array( data["prices"] ).reshape( -1, 1)

    scaler = MinMaxScaler( feature_range = (0, 1))

    scaled_data = scaler.fit_transform( prices )

    prediction_days = 60

    x_train = []
    y_train = []

    for x in range( prediction_days, len( scaled_data)):
        x_train.append( scaled_data[ x - prediction_days:x, 0])
        y_train.append( scaled_data[ x, 0])

    x_train, y_train = np.array( x_train), np.array( y_train)
    x_train = np.reshape( x_train, ( x_train.shape[0], x_train.shape[1], 1))

    model = Sequential()

    model.add( LSTM( units =50, return_sequences =True, input_shape=( x_train.shape[1], 1)))
    model.add( Dropout(0.2))

    model.add( LSTM( units=50, return_sequences =True))
    model.add( Dropout(0.2))

    model.add( LSTM(units =50))
    model.add( Dropout(0.2))
    model.add( Dense(units =1))

    model.compile( optimizer ='adam', loss ='mean_squared_error')
    model.fit( x_train, y_train, epochs =2, batch_size =32)

    # total_dataset = prices
    model_inputs = np.array( prices).reshape(-1, 1)
    model_inputs = scaler.transform( model_inputs)

    real_data = [ model_inputs[ len( model_inputs) - prediction_days:len( model_inputs + 1), 0]]
    real_data = np.array( real_data)
    real_data = np.reshape( real_data, ( real_data.shape[0], real_data.shape[1], 1))

    prediction = model.predict( real_data)
    prediction = scaler.inverse_transform( prediction)
    predicted_price = prediction[0][0]
    signal = 0 if predicted_price - last_price < 0 else 1

    return { "symbol": data[ "symbol"], "predictedPrice": predicted_price, "lastPrice": last_price, "signal": signal}


df.map( prepare_data )\
    .map( predict )\
    .saveAsTextFile( "hdfs://localhost:9000/price/NN")
# print(res)
