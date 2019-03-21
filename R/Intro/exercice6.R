geom = function(k, p) {
  p*(1-p)^(k-1)
}

f = function(n, p) {
  x = 1:n-1
  somme = sum(geom(k = x, p = p))
  
  return(1-somme)
}

f(3, 1/pi)