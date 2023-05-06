import numpy as np
import pandas as pd
from sklearn.ensemble import IsolationForest
from sklearn.preprocessing import OneHotEncoder
from cluster_data import cluster_data

data = cluster_data[ "data"]

df = pd.DataFrame( data=data, columns =[ "from", "gas", "gasPrice", "gasUsed", "to", "value" ])

onehot_encoder = OneHotEncoder( sparse =False)

string_df = df[[ "from", "to"]]
string_numpy = string_df.to_numpy()
onehot = onehot_encoder.fit_transform( string_numpy)
number_df = pd.DataFrame(data=onehot)
number_df[ "gas"] = df[ "gas"]
number_df[ "gasPrice"] = df[ "gasPrice"]
number_df[ "gasUsed"] = df[ "gasUsed"]
number_df[ "value"] = df[ "value"]

clf = IsolationForest( contamination=0.10, max_features=11, max_samples= len( data))

clf.fit( number_df)

y_pred_train = clf.predict( number_df)
number_df[ "classification"] = y_pred_train
number_df[ "classification"].count()

ntc = number_df[ "classification"].where(number_df[ "classification"]==1).count()
nntc = number_df[ "classification"].where(number_df[ "classification"]==-1).count()


print(ntc)
print(nntc)
print((nntc/(nntc + ntc))* 100)
# print(number_df)
