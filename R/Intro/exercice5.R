poisson = function(k, lambda) {
  (lambda^k)*exp(-lambda)/factorial(k)
}

f = function(n, lambda) {
  x = 1:n-1
  somme = sum(poisson(k = x, lambda = lambda))
    
  return(1-somme)
}

f(10, pi)
