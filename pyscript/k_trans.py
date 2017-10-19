import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

model = load_model('./t05.h5')

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
f = open('weight.txt','w')
for x in weights[0]:
    for x0 in x:
        f.write(x0)
        f.write(',')
f.write('\n')
for x in weights[1]:
    f.write(x)
    f.write(',')
f.write('\n')
for x in weights[2]:
    for x2 in x:
        f.write(x2)
        f.write(',')
f.write('\n')
for x in weights[3]:
     f.write(x)
     f.write(',')
f.write('\n')  
for x in weights[4]:
    for x4 in x:
        f.write(x4)
        f.write(',')
f.write('\n')
for x in weights[5]:
  #  for x5 in x:
    f.write(x)
    f.write(',')

print('end-----------')
f.close()
#print ('lengh:',len(weights))
#print ('len 0',len(weights[0]))
#print ('leng 0 0',len(weights[0][0]))
#print ('fc',weights[0][0])
#print (weights)
#model.save_weights('./t05_weight.h5')
