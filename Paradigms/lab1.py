import numpy as np
import pylab as p
from scipy.signal import argrelextrema
import math

np.set_printoptions(precision=3, suppress=True)

def f(x):
	return math.tan(x) / x

# VIEW A TABLE
a = -10
b = 10
h = 0.2
x = np.arange(a, b + h, h)
y = [f(x[i]) for i in range(len(x))]
table = [[x[i], y[i]] for i in range(len(x))]

for elem in table:
	print("| x = %.1f" %elem[0] + " | " + "f(x) = %.5f" %elem[1] + " |")


# TABLE FOR PLOT
x = p.linspace(-1.0, 1.0, 1000)
y = (p.sin(2 * p.pi * x)) / (p.cos(2 * p.pi * x) / x)

tol = 10
y[y > tol] = np.nan
y[y < -tol] = np.nan

#EXTREM POINTS
a = np.diff(np.sign(np.diff(y))).nonzero()[0] + 1 # local min+max
b = (np.diff(np.sign(np.diff(y))) > 0).nonzero()[0] + 1 # local min
c = (np.diff(np.sign(np.diff(y))) < 0).nonzero()[0] + 1 # local max


# graphical output...
p.plot(x, y, 'g-', lw=1)
p.plot(x[b], y[b], "o", label="min")
p.plot(x[c], y[c], "o", label="max")
p.legend()

p.show()