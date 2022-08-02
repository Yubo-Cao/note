package 12;

class HelloWorld{
    public static void main(String[] args){
        System.out.println("Hello World!");
    }

    // Matrix multiplication
    public static int[][] multiply(int[][] A, int[][] B){
        int[][] C = new int[A.length][B[0].length];
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < B[0].length; j++){
                for(int k = 0; k < A[0].length; k++){
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static double[] eigenvalues(int[][] A){
        int n = A.length;
        double[] eigenvalues = new double[n];
        for(int i = 0; i < n; i++){
            eigenvalues[i] = A[i][i];
        }
        return eigenvalues;
    }
}