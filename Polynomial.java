class Polynomial {
	double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] a) {
        // making a deep copy of a
        coefficients = new double[a.length];
        for(int i=0;i<a.length;i++) {
            coefficients[i] = a[i];
        }
    }

    public Polynomial add(Polynomial c) {
        // if c is the bigger array,
        if(c.coefficients.length >= coefficients.length) {
            double[] result = new double[c.coefficients.length];
            for(int i=0;i<c.coefficients.length;i++) {
                result[i] = c.coefficients[i];
            }
            for(int i=0;i<coefficients.length;i++) {
                result[i] += coefficients[i];
            }
            Polynomial f = new Polynomial(result);
            return f;
        }
        // otherwise this is the bigger array
        else {
            double[] result = new double[coefficients.length];
            for(int i=0;i<coefficients.length;i++) {
                result[i] = coefficients[i];
            }
            for(int i=0;i<c.coefficients.length;i++) {
                result[i] += c.coefficients[i];
            }
            Polynomial f = new Polynomial(result);
            return f;
        }
    }

    public double evaluate(double x) {
        double result = 0;
        for(int i=0;i<coefficients.length;i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}