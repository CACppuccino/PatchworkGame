import h5py
from keras.models import Sequential, load_model, model_from_json
from keras.layers import Dense
import numpy

def save_weight():
	model = load_model('./t05.h5')
	json_string = model.to_json()
	#print (json_string)
	weights  = model.get_weights()
	print (weights)
	model.save_weights('../model/t05_weight.h5')

def print_structure(weight_file_path='../model/t05_weight.h5'):
    """
    Prints out the structure of HDF5 file.

    Args:
      weight_file_path (str) : Path to the file to analyze
    """
    f = h5py.File(weight_file_path)
    try:
        if len(f.attrs.items()):
            print("{} contains: ".format(weight_file_path))
            print("Root attributes:")
        for key, value in f.attrs.items():
            print("  {}: {}".format(key, value))

        if len(f.items())==0:
            return 

        for layer, g in f.items():
            print("  {}".format(layer))
            print("    Attributes:")
            for key, value in g.attrs.items():
                print("      {}: {}".format(key, value))

            print("    Dataset:")
            for p_name in g.keys():
                param = g[p_name]
                print("      {}: {}".format(p_name, param.shape))
    finally:
        f.close()
