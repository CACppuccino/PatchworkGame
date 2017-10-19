import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

# load the model
model = load_model("./models/t01_300.h5")

# define the dataset
dataset = numpy.loadtxt("./data/DetailDataset02.csv", delimiter=",")

# get the test data from dataset
X_test = dataset[300:,1:8]
Y_test = dataset[300:,0]

# get the score 

scores = model.evaluate(X_test, Y_test)
print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))


