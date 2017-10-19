import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

model = load_model('./t05.h5')

json_string = model.to_json()
#print (json_string)
print('starts---------------')
weights  = model.get_weights()
for x in weights:
    print ('layer:',len(x))
    if x!= dtype.float32:
        for xx in x:
            print ('neruon',len(xx))
print('end-----------')
print ('lengh:',len(weights))
print ('len 0',len(weights[0]))
print ('leng 0 0',len(weights[0][0]))
print ('fc',weights[0][0])
#print (weights)
#model.save_weights('./t05_weight.h5')
