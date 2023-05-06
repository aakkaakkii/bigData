import numpy as np
import matplotlib.pyplot as plt
import datetime as dt
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, LSTM
from data import PriceData

# Load Data


prices = []
# map(lambda el: el[1], PriceData.price_data)

for el in PriceData.price_data:
    prices.append(el[1])

last_price = prices[len(prices) - 1]
prices = np.array(prices).reshape(-1, 1)

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
# model.fit(x_train, y_train, epochs=25, batch_size=32)
model.fit(x_train, y_train, epochs=2, batch_size=32)

test_data = {}
actual_prices = prices

total_dataset = prices
model_inputs = np.array(total_dataset).reshape(-1, 1)
model_inputs = scaler.transform(model_inputs)

real_data = [model_inputs[len(model_inputs)  - prediction_days:len(model_inputs + 1), 0]]
real_data = np.array(real_data)
real_data = np.reshape(real_data, (real_data.shape[0], real_data.shape[1], 1))



prediction = model.predict(real_data)
prediction = scaler.inverse_transform(prediction)

print({"data": prediction[0][0]})
print({"data": prediction.tolist[0]})
# print(real_data[len(real_data)])
