import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

model = load_model('./models/t05.h5')
json_string = model.to_json()
#print (json_string)
weights  = model.get_weights()
print (weights)
with open('./models/t051_weigths.txt','w') as f:
	f.write((weights.tostring()))
	f.close;
