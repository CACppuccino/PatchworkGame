import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

model = load_model('../model/m_plc.h5')

json_string = model.to_json()
#print (json_string)
print('starts---------------')
weights  = model.get_weights()
#for x in range(0,6):
#    print ('layer:',len(weights[x])) 
#for xx in weights[0]:
#    print ('neruon',len(xx))
#for zz in weights[4]:
#    print ('1-neruon',len(zz))
f = open('weight_plc.txt','w')
print('0:m',weights[0])
for x in weights[0]:
    print ('0:',len(x))
    for x0 in x:
        f.write(repr(x0))
        f.write(',')
f.write('\n')
print('1:',len(weights[1]))
for x in weights[1]:
    f.write(repr(x))
    f.write(',')
f.write('\n')
print('2:',len(weights[2]))
for x in weights[2]:
    print('2:x',len(x))
    for x2 in x:
        f.write(repr(x2))
        f.write(',')
f.write('\n')
print('3:',len(weights[3]))
for x in weights[3]:
  #  for x5 in x:
    f.write(repr(x))
    f.write(',')

print('end-----------')
f.close()
#print ('lengh:',len(weights))
#print ('len 0',len(weights[0]))
#print ('leng 0 0',len(weights[0][0]))
#print ('fc',weights[0][0])
#print (weights)
#model.save_weights('./t05_weight.h5')
