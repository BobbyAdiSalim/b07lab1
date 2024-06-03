import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	double[] coef;
	int[] exp;
	
	// Constructors
	public Polynomial() {

	}
	
	public Polynomial(double[] coef, int[] exp) {
		this.coef = coef;
		this.exp = exp;
	}

	public Polynomial(File file) throws FileNotFoundException {
		Scanner fileReader = new Scanner(file);
		if (!fileReader.hasNextLine()) {
			fileReader.close();
			return;
		}
		String stringRep = fileReader.nextLine();
		fileReader.close();
		if (stringRep.equals("0") || stringRep.equals("-0")) {
			return;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < stringRep.length(); i++) {
			char c = stringRep.charAt(i);
			if (c == '-' && i!=0) {
				stringBuilder.append("+" + c);
			} else {
				stringBuilder.append(c);
			}
		}
		stringRep = stringBuilder.toString();
		stringRep = stringRep.replaceAll("([\\+\\-]|^)x", "$1" + "1x");
		stringRep = stringRep.replaceAll("x([\\+\\-]|$)", "x1" + "$1");
		String[] split = stringRep.split("\\+");
		int length = split.length;

		exp = new int[length];
		coef = new double[length];

		for (int i = 0; i < length; i++) {
			String s = split[i];
			String[] sSplit = s.split("x");
			if (sSplit.length == 2) {
				coef[i] = Double.parseDouble(sSplit[0]);
				exp[i] = Integer.parseInt(sSplit[1]);
			} else{
				coef[i] = Double.parseDouble(sSplit[0]);
				exp[i] = 0;
			}
		}
	}


	// String rep
	@Override
	public String toString() {
		if (exp == null || exp.length == 0) {
			return "0";
		}

		StringBuilder stringBuilder = new StringBuilder();

		if (exp[0] == 0) {
			stringBuilder.append(coef[0] + "");
		} else {
			stringBuilder.append(coef[0] + "x" + exp[0]);
		}

		for (int i = 1; i < exp.length; i++) {
			if (coef[i] >= 0) {
				stringBuilder.append("+");
			}
			if (exp[i] == 0) {
				stringBuilder.append(coef[i] + "");
			} else {
				stringBuilder.append(coef[i] + "x" + exp[i]);
			}
		}
		return stringBuilder.toString();
	}


	public void saveToFile(String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName, false);
		fileWriter.write(this.toString());
		fileWriter.close();
	}
	

	public static int getIndex(int[] exp, int e) {
		if (exp == null) return -1;
		for (int i = 0; i < exp.length; i ++) {
			if (exp[i] == e) {
				return i;
			}
		}
		return -1;
	}

	public static int countOccur(double[] coef, double c, int start, int end) {
		if (coef == null) return 0;
		int count = 0;
		for (int i = start; i < end; i ++) {
			if (coef[i] == c) {
				count++;
			}
		}
		return count;
	}

	public Polynomial add(Polynomial p) {
		if (exp == null) {
			double[] emptyC = {};
			int[] emptyE = {};
			Polynomial newThis = new Polynomial(emptyC, emptyE);
			return newThis.add(p);
		}
		if (p.exp == null) {
			double[] emptyC = {};
			int[] emptyE = {};
			Polynomial newArg = new Polynomial(emptyC, emptyE);
			return this.add(newArg);
		}

		int argLength = p.exp.length;
		int thisLength = exp.length;
		int maxLength = argLength + thisLength;
		double[] tempCoef = new double[maxLength];
		int[] tempExp = new int[maxLength];

		int tempIdx = 0;
		for (int i = 0; i < thisLength; i++) {
			if (coef[i] == 0) {
				continue;
			}
			int expIdx = getIndex(tempExp, exp[i]);
			if (expIdx == -1) {
				tempExp[tempIdx] = exp[i];
				tempCoef[tempIdx] = coef[i];
				tempIdx++;
			} else if (exp[i] == 0 && tempCoef[expIdx] == 0) {
				tempCoef[tempIdx] = coef[i];
				tempIdx++;
			} else {
				tempCoef[expIdx] += coef[i];
			}
		}

		for (int i = 0; i < argLength; i++) {
			if (p.coef[i] == 0) {
				continue;
			}
			int expIdx = getIndex(tempExp, p.exp[i]);
			if (expIdx == -1) {
				tempExp[tempIdx] = p.exp[i];
				tempCoef[tempIdx] = p.coef[i];
				tempIdx++;
			} else if (p.exp[i] == 0 && tempCoef[expIdx] == 0) {
				tempCoef[tempIdx] = p.coef[i];
				tempIdx++;
			} else {
				tempCoef[expIdx] += p.coef[i];
			}
		}
		
		int newLength = tempIdx - countOccur(tempCoef, 0, 0, tempIdx);
		if (newLength == 0) return new Polynomial();

		int[] newExp = new int[newLength];
		double[] newCoef = new double[newLength];

		for (int i = 0, newI = 0; i < tempIdx; i++) {
			if (tempCoef[i] == 0) {
				continue;
			}
			newExp[newI] = tempExp[i];
			newCoef[newI] = tempCoef[i];
			newI++;
		}

		Polynomial newPoly =  new Polynomial(newCoef, newExp);
		return newPoly;
	} 

	public double evaluate(double x) {
		if (exp == null) return 0;
		double result = 0;
		for (int i = 0; i < exp.length; i++) {
			result += coef[i] * Math.pow(x, exp[i]);
		}
		return result;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}

	public Polynomial multiply(Polynomial p) {
		if (exp == null) {
			double[] emptyC = {};
			int[] emptyE = {};
			Polynomial newThis = new Polynomial(emptyC, emptyE);
			return newThis.multiply(p);
		}
		if (p.exp == null) {
			double[] emptyC = {};
			int[] emptyE = {};
			Polynomial newArg = new Polynomial(emptyC, emptyE);
			return this.multiply(newArg);
		}

		int argLength = p.exp.length;
		int thisLength = exp.length;
		int maxLength = argLength * thisLength;
		double[] tempCoef = new double[maxLength];
		int[] tempExp = new int[maxLength];

		int tempIdx = 0;
		for (int i = 0; i < thisLength; i++) {
			if (coef[i] == 0) continue;
			for (int j = 0; j < argLength; j++) {
				if (p.coef[j] == 0) continue;
				int expSum = exp[i] + p.exp[j];
				int expIdx = getIndex(tempExp, expSum);
				if (expIdx == -1) {
					tempExp[tempIdx] = expSum;
					tempCoef[tempIdx] = coef[i]*p.coef[j];
					tempIdx++;
				} else if (expSum == 0 && tempCoef[expIdx] == 0) {
					tempCoef[expIdx] = coef[i]*p.coef[j];
					tempIdx++;
				} else {
					tempCoef[expIdx] += coef[i]*p.coef[j];
				} 
			}
		}

		int newLength = tempIdx - countOccur(tempCoef, 0, 0, tempIdx); 
		if (newLength == 0) return new Polynomial();
		
		int[] newExp = new int[newLength];
		double[] newCoef = new double[newLength];

		for (int i = 0, newI = 0; i < tempIdx; i++) {
			if (tempCoef[i] == 0) {
				continue;
			}
			newExp[newI] = tempExp[i];
			newCoef[newI] = tempCoef[i];
			newI++;
		}

		Polynomial newPoly =  new Polynomial(newCoef, newExp);
		return newPoly;
	}
}