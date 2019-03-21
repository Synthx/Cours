a = -pi
b = pi

# Calcul de l'approximation de Riemann
n = 1000
x = seq(from = a, to = b, by = 2*pi/n)
y = sin(10*x)
z = rep(0, length(y))

u = pmax(y, z)
int = (2*pi/n)*sum(u)

# Calcul de l'intégrale
f = function(x) {
  pmax(sin(10*x), 0)
}

integrate(f, lower = a, upper = b)
