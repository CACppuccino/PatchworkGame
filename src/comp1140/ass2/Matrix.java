package comp1140.ass2;


import java.util.Arrays;

public class Matrix{
    int col,row;
    double[][] matrix;
    Matrix(int row,int col){this.row = row;
        this.col = col;
        matrix = new double[row][col];
    }
    Matrix(int row,int col,double ele){
        this.col = col;
        this.row = row;
        matrix = new double[row][col];
        for (int i=0;i<row;i++)
            for (int j=0;j<col;j++)
                matrix[i][j] = ele;
        //this one is not working, dont know why
//        for (double[] cc :matrix)
//            for (double c : cc)
//                c = ele;
    }
    Matrix(double[][] matrix){
        row = matrix.length;
        col = matrix.length;
        this.matrix = matrix;
    }
//    Matrix(double[] vector){
//        row = vector.length;
//        col = 1;
//        for (int i=0;i<vector.length;i++)
//            matrix[0][i] = vector[i];
//    }
    public double[][] getMatrix(){return matrix;}
    void setZero(){
        for (int i =0;i<row;i++)
            for (int j=0;j<col;j++)
                matrix[i][j] = 0;
    }

    boolean equals(Matrix another){
        if (this.row!=another.row || this.col != another.col) return false;
        if (!Arrays.deepToString(this.matrix).equals(Arrays.deepToString(another.matrix))) return false;
        return true;
    }
    public void setMatrix (int coordR, int coordC, double element) throws Exception{
        if (coordR>= row || coordC >= col)
            throw new Exception("Matrix out of bound, should be ("+row+','+col+") but have " +
                    "("+coordR+','+coordC+")");
        this.matrix[coordR][coordC] = element;
    }
    public double getElement (int coordR, int coordC) throws Exception{
        if (coordR>= row || coordC >= col)
            throw new Exception("Matrix out of bound, should be ("+row+','+col+") but have " +
                    "("+coordR+','+coordC+")");
        return this.matrix[coordR][coordC];
    }
    public void printMatrix(){
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if (j!=col-1)
                System.out.print(matrix[i][j]+", ");
                else
                    System.out.print(matrix[i][j] + ";");
            }
            System.out.println();
        }
    }
    //produce a new Matrix
    public static Matrix addMatrix (Matrix a, Matrix b) throws Exception{
        if (a.row!=b.row || a.col!=b.col)
            throw new Exception("Matrix not the same size");
        Matrix c = new Matrix(a.row,a.col);
        for (int i=0;i<a.row;i++)
        {
            for (int j = 0; j < a.col; j++)
            {
                c.matrix[i][j] = a.matrix[i][j]+b.matrix[i][j];
            }
        }
        return c;
    }
    public Matrix addBy (Matrix a) throws Exception{
        if (col != a.col || row != a.row)
            throw new Exception("Matrix not the same size");
        for (int i = 0;i < row;i++)
            for (int j = 0; j < col;j++)
                matrix[i][j] += a.matrix[i][j];
        return this;
    }


    public void scalar (double s){

        for (double[] cc: matrix){
            for (double ccc:cc)
                ccc *= s;
        }
    }

    public static Matrix transpose(Matrix oldM){
//        Matrix c = new Matrix(this.row,this.col);
        Matrix newM = new Matrix(oldM.col,oldM.row);
        for (int i=0;i<oldM.col;i++)
            for (int j = 0; j < oldM.row; j++)
                newM.matrix[i][j] =
                        oldM.matrix[j][i];
        return newM;
    }

    public boolean isSquare(){return row==col;}

    //for the two methods below
    //referencing from https://www.codeproject.com/Articles/405128/Matrix-operations-in-Java
    public static double determinant(Matrix matrix) throws Exception {
        if (!matrix.isSquare())
            throw new Exception("matrix need to be square.");
        if (matrix.row == 1) {
            return matrix.matrix[0][0];
        }
        if (matrix.row==2) {
            return (matrix.getElement(0, 0) * matrix.getElement(1, 1)) -
                    ( matrix.getElement(0, 1) * matrix.getElement(1, 0));
        }
        double sum = 0.0;
        for (int i=0; i<matrix.col; i++) {
            sum += Math.pow(-1,i) * matrix.getElement(0, i) * determinant(createSubMatrix(matrix, 0, i));
        }
        return sum;
    }

    public static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) throws Exception{
        Matrix mat = new Matrix(matrix.row-1, matrix.col-1);
        int r = -1;
        for (int i=0;i<matrix.row;i++) {
            if (i==excluding_row)
                continue;
            r++;
            int c = -1;
            for (int j=0;j<matrix.col;j++) {
                if (j==excluding_col)
                    continue;
                mat.setMatrix(r, ++c, matrix.getElement(i,j));
            }
        }
        return mat;
    }
    static Matrix multiply(Matrix a, Matrix b) throws Exception{
        if (a.col!=b.row) throw new Exception("wrong size matrix"+a.row+" "+a.col+" "+b.row+" "+b.col);
        Matrix c = new Matrix(a.row,b.col,0);
        for (int i = 0; i < a.row; i++) {
            for (int j = 0; j < b.col; j++) {
                for (int k = 0; k < a.col; k++) {
                    c.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }
        return c;
    }

    public static void main(String[] args) throws Exception{

        Matrix a = new Matrix(5, 5);
        for(int i=0;i<5;i++) {
            for (int j = 0; j < 5; j++) a.setMatrix(i, j, i + j);
        }
        a.printMatrix();
        Matrix.createSubMatrix(a, 1, 0).printMatrix();

    }

}
