public class Polynomial {
	double[] coef = {0};
	
	public Polynomial() {}
	
	public Polynomial(double[] coef) {
		this.coef = coef;
	}
	
	public Polynomial add(Polynomial p) {
		int thisLength = coef.length;
		int argLength = p.coef.length;
		double[] newCoef;
		if (thisLength >= argLength) {
			newCoef = new double[thisLength];
			
			for (int i = 0; i < argLength; i++) {
				newCoef[i] = p.coef[i] + coef[i];
			}
			for (int i = argLength; i < thisLength; i++) {
				newCoef[i] = coef[i];
			}
		} else {
			newCoef = new double[argLength];
			
			for (int i = 0; i < thisLength; i++) {
				newCoef[i] = p.coef[i] + coef[i];
			}
			for (int i = thisLength; i < argLength; i++) {
				newCoef[i] = p.coef[i];
			}
		}
		
		Polynomial newPoly = new Polynomial(newCoef);
		return newPoly;
	} 

	public double evaluate(double x) {
		double result = 0;
		double power = 1;
		for (double a:coef) {
			result += a*power;
			power *= x;
		}
		return result;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
}