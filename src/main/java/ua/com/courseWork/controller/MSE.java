package ua.com.courseWork.controller;

import java.util.ArrayList;
import java.util.List;

public class MSE {

    private static final double EPSILON = 1e-10;

    /**
     * Calculate the matrix of the element
     * 
     * @param h the element's fullLength
     * @return the matrix of the element
     */
    private static double[][] createMatrixElement(double h) {
        double[][] result = new double[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = i == j ? 1 / h + h / 3 : -1 / h + h / 6;
            }
        }

        return result;
    }

    /**
     * Created the matrix, which include all elements`s matrix
     *
     * @param matrix the list of element`s matrix
     * @return the matrix, which include all elements`s matrix
     */
    private static double[][] createFullMatrix(List<double[][]> matrix) {
        double[][] result = new double[matrix.size() + 1][matrix.size() + 1];

        for (int i = 0; i <= matrix.size(); i++) {

            for (int j = 0; j < i - 1; j++) {
                result[i][j] = 0;
            }

            if (i != 0) {
                result[i][i - 1] = (matrix.get(i - 1)[1][0]);
            }

            result[i][i] = i == 0 ? matrix.get(i)[0][0] :
                    i == matrix.size() ? matrix.get(i - 1)[1][1] : (matrix.get(i - 1)[1][1] + matrix.get(i)[0][0]);

            if (i != matrix.size()) {
                result[i][i + 1] = (matrix.get(i)[0][1]);
            }

            for (int j = i + 2; j <= matrix.size() ; j++) {
                result[i][j] = 0;
            }
        }

        return result;
    }

    /**
     * Create the main matrix for Gauss method
     *
     * @param matrix the matrix
     * @return the main matrix for Gauss method
     */
    private static double[][] createGaussMainMatrix(double[][] matrix) {
        double[][] result = new double[matrix.length - 2][matrix.length - 2];

        for (int i = 0; i < result.length; i++) {
            if (result[i].length >= 0) {
                System.arraycopy(matrix[i + 1], 1, result[i], 0, result[i].length);
            }
        }

        return result;
    }

    /**
     * Create the right matrix for Gauss method
     *
     * @param matrix the matrix
     * @param fi0 the value in the begin point
     * @param fiN the value in the end point
     * @return the right matrix for Gauss method
     */
    private static double[] createGaussRightMatrix(double[][] matrix, double fi0, double fiN) {
        double[] result = new double[matrix.length - 2];

        result[0] = -matrix[1][0] * fi0;
        result[result.length - 1] = -matrix[matrix.length - 2][matrix.length - 1] * fiN;

        return result;
    }

    /**
     * Gaussian elimination with partial pivoting
     *
     * @param A the main matrix
     * @param b the right matrix
     * @return the matrix with solve
     */
    private static double[] gaussSolve(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    /**
     * Calculate the all values of the function.
     *
     * @param lengthElements the list with the elements lengths
     * @param firstFi the value the function in the first point
     * @param lastFi the value the function in the last point
     * @return the result (the all values of the function)
     */
    public static double[] calculateResult(List<Double> lengthElements, double firstFi, double lastFi) {

        List<double[][]> listMatrix = new ArrayList<>();

        for (Double elem :
                lengthElements) {
            listMatrix.add(createMatrixElement(elem));
        }

        double[][] matrix = createFullMatrix(listMatrix);

        return gaussSolve(createGaussMainMatrix(matrix),
                createGaussRightMatrix(matrix, firstFi, lastFi));
    }
}

