def cieToRgb (x, y, Y=254) {
	def z = 1.0 - x - y
	def Y=1.0
	def X=(Y/y) * x
    def Z=(Y/y) * z
	def R= (X * 1.656492) + (Y * -0.354851) + (Z * -0.255038)
	def G= (X * -0.707196) + (Y * 1.655397) + (Z * 0.036152)
	def B= (X * 0.051713) + (Y * -0.121364) + (Z * 1.011530)
	if (R > B &&  R > G && R > 1.0) {
		G = G / R
		B = B / R
		R = 1.0
	} else if (G > B && G > R && G > 1.0){
		R = R / G
		B = B / G
		G = 1.0
	} else if (B > R && B > G && B > 1.0) {
		R = R / B
		G = G / B
		B = 1.0
	}
	def r=(R<=0.0031308) ? 12.92 * R: (1.055 * Math.pow(R, 1.0 / 2.4)) - 0.055
	def g=(G<=0.0031308) ? 12.92 * G: (1.055 * Math.pow(G, 1.0 / 2.4)) - 0.055
	def b=(B<=0.0031308) ? 12.92 * B: (1.055 * Math.pow(B, 1.0 / 2.4)) - 0.055

	return ["r": Math.round(255*r), "g": Math.round(255*g), "b": Math.round(255*b))
}

def RgbToCie (r, g, b)
	r=r/255
	g=g/255
	b=b/255
	def R=(r>0.4045) ? Math.pow((r+0.055)/ 1.055, 2.4) : ( r / 12.92)
	def G=(g>0.4045) ? Math.pow((g+0.055)/ 1.055, 2.4) : ( g / 12.92)
	def B=(b>0.4045) ? Math.pow((b+0.055)/ 1.055, 2.4) : ( b / 12.92)

	X = (R * 0.664511) + (G * 0.154324) + (B * 0.162028)
	Y = (R * 0.283881) + (G * 0.668433) + (B * 0.047685)
	Z = (R * 0.000088) + (G * 0.072310) + (B * 0.986039)
	x = (X/(X+Y+Z))
	y = (Y/(X+Y+Z))
	return (["x": x, "y": y])
}

def cieToCt(x,y) {
	n = ((x-0.3320)/(0.1858-y))
	CCT = (437*Math.pow(n,3))+(3601*Math.pow(n,2))+(6861*n)+5517
	return Math.round(CCT)
}