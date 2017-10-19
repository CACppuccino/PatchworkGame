import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

# load the model
model = load_model("../model/m_tile.h5")

# define the dataset
dataset = numpy.loadtxt("../data/DetailDataset06.csv", delimiter=",")

# get the test data from dataset
X_test = dataset[:,1:9]
Y_test = dataset[:,0]

# get the score 

scores = model.evaluate(X_test, Y_test)
print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))


