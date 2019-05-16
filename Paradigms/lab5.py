from tkinter import *
import random
# CREATING FUCKING FORM
root = Tk()
root.title("Lab5 by @gloomikon")
root.geometry("1500x1000")
root.configure(background = "powder blue")

# ADDING SHITTY ELEMETS
matrix = None

def fillRandomValues():
	global matrix
	print(matrix)
	for elem in matrix:
		elem.delete(0, 'end')
		elem.insert(END, str(random.randint(1, 50)))

def findElement(event, entry, out):
	global matrix
	for i in range(len(matrix)):
		if (matrix[i].get() == entry.get()):
			matrix[i].config(bg = "green")
			out.insert(END, "x = " + str(i - (i // scalex.get()) * scalex.get()) + "\ty = " + str(i // scalex.get()) + "\n")

def createSecondLevel():
	btnRandom = Button(root, text = "Random", command = fillRandomValues)
	btnRandom.place(x = 1300, y = 300)

	lbl2 = Label(root, text = "Enter an element", font = ("Arial 18"))
	lbl2.place(x = 900, y = 300)

	entryNumber = Entry(root, width = 7)
	entryNumber.insert(END, "0")
	entryNumber.place(x = 900, y = 400)

	textOut = Text(root, width = 30, height = 10)
	textOut.place(x = 900, y = 500)

	btnFinal = Button(root, text = "Find")
	btnFinal.bind("<Button-1>", lambda event, entry=entryNumber, out=textOut: findElement(event, entry, out))
	btnFinal.place(x = 1300, y = 400)





def generateMatrix():
	global matrix
	if (matrix):
		for elem in matrix:
				elem.destroy()
	matrix = [0] * scalex.get() * scaley.get()
	print(matrix)
	for i in range (scaley.get()):
		for j in range (scalex.get()):
			matrix[i * scaley.get() + j] = Entry(root, width = 5)
			matrix[i * scaley.get() + j].insert(END, "0")
			matrix[i * scaley.get() + j].place(x = 100 + j * 100, y = 300 + i * 100)
	createSecondLevel()


# FIRST INPUT
lbl1 = Label(root, text = "Enter size X & Y", font = ("Arial 20"))
lbl1.place(x = 550, y = 50)

lblx = Label(root, text = "X = ", font = ("Arial 17"))
lblx.place(x = 100, y = 130)

scalex = Scale(root, from_ = 3, to = 7, font = ("Arial 17"), orient = HORIZONTAL)
scalex.place(x = 190, y = 130)

lbly = Label(root, text = "Y = ", font = ("Arial 17"))
lbly.place(x = 500, y = 130)

scaley = Scale(root, from_ = 3, to = 7, font = ("Arial 17"), orient = HORIZONTAL)
scaley.place(x = 590, y = 130)

btn1 = Button(root, text = "OK", command = generateMatrix)
btn1.place(x = 400, y = 200)
root.mainloop()