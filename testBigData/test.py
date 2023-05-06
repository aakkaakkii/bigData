import numpy as np
import matplotlib.pyplot as plt
import datetime as dt
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, LSTM
from data import PriceData

prices = [1, 2, 3, 5, 5, 1]
prices = np.array(prices).reshape(-1, 1)


scaler = MinMaxScaler(feature_range=(0, 1))

total_dataset = prices
model_inputs = np.array(total_dataset).reshape(-1, 1)
model_inputs = scaler.transform(model_inputs)
print(f"model inputs {model_inputs}")

# predoctopms

x_test = []

for x in range(len(model_inputs)):
    x_test.append(model_inputs[x, 0])

x_test = np.array(x_test)
x_test = np.reshape(x_test, (x_test.shape[0], x_test.shape[1], 1))

print(x_test)
