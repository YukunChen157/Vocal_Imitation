/******************************************************************************
 *  Compilation:  javac Matrix.java
 *  Execution:    java Matrix
 *
 *  A bare-bones collection of static methods for manipulating
 *  matrices.
 *
 ******************************************************************************/

public class Matrix {
	// return n-by-n identity matrix I
    public static float[][] identity(int n) {
        float[][] a = new float[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 1;
        return a;
    }

    // return x^T y
    public static float dot(float[] x, float[] y) {
        if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
        float sum = (float)0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    // return B = A^T
    public static float[][] transpose(float[][] a) {
        int m = a.length;
        int n = a[0].length;
        float[][] b = new float[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }

    // return c = a + b
    public static float[][] add(float[][] a, float[][] b) {
        int m = a.length;
        int n = a[0].length;
        float[][] c = new float[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];
        return c;
    }
    
    // return c = a + b
    public static float[] add(float[] a, float[] b) {
        int m = a.length;
        float[] c = new float[m];
        for (int i = 0; i < m; i++)
        	c[i] = a[i] + b[i];
        return c;
    }

    // return c = a - b
    public static float[][] subtract(float[][] a, float[][] b) {
        int m = a.length;
        int n = a[0].length;
        float[][] c = new float[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] - b[i][j];
        return c;
    }

    // return c = a * b
    public static float[][] multiply(float[][] a, float[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        float[][] c = new float[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    // matrix-vector multiplication (y = A * x)
    public static float[] multiply(float[][] a, float[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        float[] y = new float[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static float[] multiply(float[] x, float[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        float[] y = new float[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }
}