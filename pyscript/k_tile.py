import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

# fix random seed for reproducibility
seed = 3
numpy.random.seed(seed)
# load pima indians dataset
dataset = numpy.loadtxt("../data/DetailDataset02.csv", delimiter=",")
# split into input (X) and output (Y) variables
X_train = dataset[0:500,1:9]
Y_train = dataset[0:500,0]
X_test = dataset[3000:, 1:9]
Y_test = dataset[3000:, 0]
# create model
model = Sequential()
model.add(Dense(12, input_dim=8, init='uniform'))
#model.add(Dense(171, init='uniform', activation='relu'))
model.add(Dense(35, init='uniform', activation='softmax'))
# Compile model
model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
# Fit the model
model.fit(X_train, Y_train, epochs=800, batch_size=10,  verbose=2)
# calculate predictions
#predictions = model.predict(X)
# round predictions
#rounded = [round(x[0]) for x in predictions]
#print(rounded)
# evaluate the model

scores = model.evaluate(X_test, Y_test)
print("\n%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))

# Save the model and weights
model.save("../model/m_tile.h5")

# If need to load the model
# keras.models.load_model("./models/t01_300.h5")

# only save the archi
# json_string = model.to_json()
# and load
# model = model_from_json(json_string)

