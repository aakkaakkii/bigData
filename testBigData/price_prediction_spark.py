import numpy as np
import datetime as dt
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, LSTM
from pyspark.sql import SparkSession
import json

spark = SparkSession.builder.appName("test22").getOrCreate()

df = spark.read.text("hdfs://localhost:9000//topics/test-topic4/partition=0/*")

prices = df.select('value').rdd.flatMap(lambda x: x).collect()

data = prices[0]

# Load Data
price_data = json.loads(data)['prices']

prices = []
# map(lambda el: el[1], PriceData.price_data)

for el in price_data:
    prices.append(el[1])

prices = np.array(prices[0:len(prices) - 60]).reshape(-1, 1)

scaler = MinMaxScaler(feature_range=(0, 1))

scaled_data = scaler.fit_transform(prices)

prediction_days = 60

x_train = []
y_train = []

for x in range(prediction_days, len(scaled_data)):
    x_train.append(scaled_data[x - prediction_days:x, 0])
    y_train.append(scaled_data[x, 0])

x_train, y_train = np.array(x_train), np.array(y_train)
x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], 1))

model = Sequential()

model.add(LSTM(units=50, return_sequences=True, input_shape=(x_train.shape[1], 1)))
model.add(Dropout(0.2))

model.add(LSTM(units=50, return_sequences=True))
model.add(Dropout(0.2))

model.add(LSTM(units=50))
model.add(Dropout(0.2))
model.add(Dense(units=1))

model.compile(optimizer='adam', loss='mean_squared_error')
model.fit(x_train, y_train, epochs=25, batch_size=32)

test_start = dt.datetime(2020, 1, 1)
test_end = dt.datetime(2020, 1, 1)

test_data = {}
actual_prices = prices

total_dataset = prices
model_inputs = np.array(total_dataset).reshape(-1, 1)
model_inputs = scaler.transform(model_inputs)

# predoctopms

x_test = []

for x in range(prediction_days, len(model_inputs)):
    x_test.append(model_inputs[x - prediction_days:x, 0])

x_test = np.array(x_test)
x_test = np.reshape(x_test, (x_test.shape[0], x_test.shape[1], 1))

predicted_prices = model.predict(x_test)
predicted_prices = scaler.inverse_transform(predicted_prices)

# predict next week

# real_data = prices
# real_data = np.array(prices)
# real_data = np.reshape(real_data, (real_data.shape[0], real_data.shape[1], 1))
#
# prediction = model.predict(real_data)
# prediction = scaler.inverse_transform(prediction)
# print(prediction)

