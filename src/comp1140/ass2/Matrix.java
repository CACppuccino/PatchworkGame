package comp1140.ass2;

import java.awt.*;

public class Matrix{
    int col,row;
    double[][] matrix;
    Matrix(int row,int col){this.row = row;
        this.col = col;
        matrix = new double[row][col];
    }
    public void setMatrix (int coordR, int coordC, double element) throws Exception{
        if (coordR>= row || coordC >= col)
            throw new Exception("Matrix out of bound, should be ("+row+','+col+") but have " +
                    "("+coordR+','+coordC+")");
        this.matrix[coordR][coordC] = element;
    }
    public double getMatrix (int coordR, int coordC) throws Exception{
        if (coordR>= row || coordC >= col)
            throw new Exception("Matrix out of bound, should be ("+row+','+col+") but have " +
                    "("+coordR+','+coordC+")");
        return this.matrix[coordR][coordC];
    }
    public void printMatrix(){
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if (j!=col-1)
                System.out.print(matrix[i][j]+',');
                else
                    System.out.print(matrix[i][j] + ';');
            }
            System.out.println();
        }
    }
    public Matrix addMatrix (Matrix a, Matrix b) throws Exception{
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
