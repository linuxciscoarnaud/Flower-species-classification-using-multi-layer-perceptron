# Flower-species-classification-using-multi-layer-perceptron
Flower species classification using weka's multi-layer perceptron classifier.

This is a simple program that performs a multi-class classification using multi-layer perceptron. The program is implemented using weka library.
To train the classifier, i used the famous iris dataset published in Fisher's paper. The dataset has 4 attributes and 3 classes (Iris Setosa, Iris Versicolour and Iris Virginica), each one representing a type of iris plant. So the dataset originally contains 150 instances  (50 in each of three classes). The attributes represent the dimensions in cm (length and width) of the iris sepal and petal.

I have split it out into training data (120 instances) and test data (30 instances). This is to be able to train de model on separate training data, then test its accuracy on different test data.

The network is composed of 4 layers (4 neurons on first layer, 5 neurons on second layer, 4 neurons on the third layer and 3 neurons on the last layer). The 3 neurons on the last layer represent the output label classes (the 3 types of iris)

![to be urgently updated](https://user-images.githubusercontent.com/1300982/52940122-1136de00-3366-11e9-8855-3a582b46f604.png)

It takes approximately 60 seconds to train the network on my old laptop (Intel(R) Core(TM) i3-231M CPU @ 2.10GHz 2.10GHz, Installed memory (RAM): 4.00 GB). The trained model is saved as a binary file (trained_network.txt) in the application directory.

The testing results show that our network operates pretty well with an accuracy of 96%

