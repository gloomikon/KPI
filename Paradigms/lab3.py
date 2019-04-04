import random
import numpy
#Reading sizes
a, b = map(int, input("Enter A, B\n").split())
#Creating an array
lst = numpy.random.random_integers(-10, 10, size=(a,b))
#Printing an array
print(lst)
#Reading an element to find
x = int(input("Enter a number: "))
#Finding the element
result = numpy.where(lst == x)
#Converting to nice format
listOfCoordinates= list(zip(result[0], result[1]))
#Printing the result
for cord in listOfCoordinates:
	print(cord)
