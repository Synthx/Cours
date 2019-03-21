x = seq(-10, 10, length = 30)
y = x

f = function(x,y) {
  r = sqrt(x^2 + y^2)
  return(10*sin(r)/r)
}

z = outer(x, y, f)

persp(x, y, z, theta = 30, phi = 30, expand = 0.5, col = 'lightblue')