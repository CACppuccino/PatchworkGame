package comp1140.ass2;

import java.awt.*;
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

    public static void main(String[] args) {
        Matrix a = new Matrix(9,3);
        a.setZero();
        a.printMatrix();
    }

    public void scalar (double s){

        for (double[] cc: matrix){
            for (double ccc:cc)
                ccc *= s;
        }
    }

    public Matrix transpose(){
        Matrix c = this;
        for (int i=0;i<row;i++)
            for (int j = 0;j<col;j++)
                c.matrix[i][j] = this.matrix[j][i];
        return c;
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
            sum += (-i) * matrix.getElement(0, i) * determinant(createSubMatrix(matrix, 0, i));
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
}
//public class Matrix {
//    int dimension;
//    int[] rowNum;
//    double[] matrix;
//    Matrix(int dimension){this.dimension = dimension;this.rowNum = new int[dimension];}
//
//    public void setDimLength(int[] dimLength) throws Exception{
//        if (dimLength.length!=dimension)
//            throw new Exception("Wrong dimension length input");
//        for (int i=0;i < dimLength.length;i++)
//            rowNum[i] = dimLength[i];
//    }
//    //0 {}/ 1 {0,1,2..}/2 {00,10,01,11} //
//    // 3 {00-0,1,2
//    //    01-0,1,2
//    //    02-0,1,2...}
//    //4{000-0,1,2,3 => 0*4^3+0*4^2+0*4^1+(0/1/2/3)*4^0
//    //  001-0,1,2,3 => 0*4^3+0*4^2+1*4^1+(0/1/2/3)*4^0
//    //  002-0,1,2,3
//    //  003-0,1,2,3}
//    public void setMatrix(int[] dim, double element){
//        int sum = 0;
//        for (int i = 0;i < dim.length;i++){
//            sum += dim[i]*Math.pow(dimension,i);
//        }
//        matrix[sum] = element;
//    }
//    public double getElement(int[] coordinate) throws Exception {
//        if (coordinate.length!=this.dimension)
//            throw new Exception("Not enough number for getElement input, expected "+this.dimension+" but get "+coordinate.length);
//        int sum = 0;
//        for (int i = 0;i < coordinate.length;i++){
//            sum += coordinate[i]*Math.pow(dimension,i);
//        }
//        return matrix[sum];
//    }
//    public void printMatrix() throws Exception{
//        for (int i=0;i<matrix.length;i++){
//
//        }
//    }
//}
