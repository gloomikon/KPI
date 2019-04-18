class Ticket:
	def __init__(self, number, name, surname, train, carriage, date1, time1, date2, time2):
		self.number = number
		self.name = name
		self.surname = surname
		self.train = train
		self.carriage = carriage
		self.date1 = date1
		self.time1 = time1
		self.date2 = date2
		self.time2 = time2
	def print_info(self):
		print(" Number:" + self.number)
		print(" Name Surname: " + self.name + " " + self.surname)
		print(" Train:" , self.train)
		print(" Carriage:" , self.carriage)
		print(" Departure time & date: " + self.date1 + " " + self.time1)
		print(" Arrival time: " + self.date2 + " " + self.time2)
		print()

class Person:
	def __init__(self,name, age, ticket):
		self.name = name
		self.age = age
		self.ticket = ticket

class Carriage:
	def __init__(self, number):
		self.number = number
		self.passagers = []
	def add_passager(self, passager):
		if (passager.ticket.carriage == self.number):
			self.passagers.append(passager)
		else:
			print("Invalid carriage number")
	def print_passagers(self):
		for passager in self.passagers:
			print("Name: " + passager.name)
			print("Age: " + passager.age)
			print("Ticket: ")
			passager.ticket.print_info()

class Train:
	def __init__(self, number):
		self.number = number
		self.carriages = []
	def print_train(self):
		print("ooOOOO      \n" +
			"oo      _____\n" +
		  " _I__n_n__||_|| " + " ________" * len(self.carriages) +
		"\n>(_________|_$_|-" + "|______|-" * len(self.carriages) +
		"\n /o ()() ()() o  " + " oo  oo  " * len(self.carriages))
		print("                 ", end = '')
		for carriage in self.carriages:
			print("N: %4d  " % carriage.number, end = '')
		print()
		print("                 ", end = '')
		for carriage in self.carriages:
			print("P: %4d  " % len(carriage.passagers), end = '')
		print()
	def add_carriage(self, carriage):
		self.carriages.append(carriage)
	def add_passager(self, number, passager):
		if (passager.ticket.train == self.number):
			self.carriages[number - 1].add_passager(passager)
		else:
			print("Invalid train number")


train = Train(69)
print("Creating a new train with no carriages")
train.print_train()
print("Add 3 carriages to train")
carr1 = Carriage(1)
carr2 = Carriage(2)
carr3 = Carriage(3)
train.add_carriage(carr1)
train.add_carriage(carr2)
train.add_carriage(carr3)
train.print_train()
print("Create some people and tickets and add them on train")
ticket1 = Ticket("1A", "Nikolay", "Zhurba", 69, 1, "20/04/2019", "6:20", "20/04/2019", "15:47")
nz = Person("Nikolay Zhurba", "19", ticket1)
train.add_passager(1, nz)
ticket2 = Ticket("2A", "Bethany", "Tusen", 69, 1, "20/04/2019", "6:20", "20/04/2019", "15:47")
bt = Person("Bethany Tusen", "19", ticket2)
train.add_passager(1, bt)
ticket3 = Ticket("3A", "Strawbery", "Aliz", 69, 1, "20/04/2019", "6:20", "20/04/2019", "15:47")
sa = Person("Strawberry Aliz", "19", ticket3)
train.add_passager(1, sa)
train.print_train()
print("Check for invalid operation")
ticket4 = Ticket("1A", "Invalid", "Person", 69, 1, "20/04/2019", "6:20", "20/04/2019", "15:47")
ip = Person("Invalid Person", "19", ticket4)
train.add_passager(2, ip)
print("Print info")
train.carriages[0].print_passagers()