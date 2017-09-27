package comp1140.ass2;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class MatrixTest {
    Random rng = new Random();
    static final int TESTROUNDS = 20;

    @Test
    public void setZero() throws Exception {
        Matrix a;
        String b;
        for (int i = 0; i <= TESTROUNDS; i++) {
            a = new Matrix(rng.nextInt(7), rng.nextInt(7));
            a.setZero();

            for (double[] c:a.matrix)
                for (double cc:c)
                    assertTrue("matrix should be zero",cc==0);
        }
    }

    @Test
    public void setMatrix() throws Exception {
        Matrix a = new Matrix(rng.nextInt(50)+1,rng.nextInt(50)+1);
        double get,ele ;
        int r,c;
        for (int i=0;i<TESTROUNDS;i++) {
            ele = rng.nextDouble();
            r = rng.nextInt(a.row);
            c = rng.nextInt(a.col);
            a.setMatrix(r, c, ele);
            get = a.matrix[r][c];
            assertFalse("method setMatrix is wrong, should set" + ele + "but get " + get, get != ele);
        }

    }

    @Test
    public void addDiffMatrix() throws Exception {
        Matrix a, b, c;
        a = new Matrix(3,3);
        b = new Matrix(5,5);
        try {
            c = Matrix.addMatrix(a,b);
        }catch (Exception e){
            assertTrue("didn't throw an exception when different size of matrix are added",e.getMessage().equals("Matrix not the same size"));
        }

    }
    @Test
    public void addMatrix() throws Exception{
        Matrix a,b,c;
        double el1,el2;
        for (int i=0;i<TESTROUNDS;i++){
            el1 = rng.nextDouble();
            el2 = rng.nextDouble();
            a = new Matrix(3,3,el1);
            b = new Matrix(3,3,el2);
            c = Matrix.addMatrix(a,b);
            for (double[] jj:c.matrix)
                for (double jjj:jj)
                    assertTrue("wrong addition on matrix, should be "+(el1+el2)+"but is "+jjj,jjj==el1+el2);
        }
    }

    @Test
    public void addByDiffMatrix() throws Exception {
        Matrix a = new Matrix(3,3),b = new Matrix(4,3);
        try {
            a.addBy(b);
        }catch (Exception e) {
            assertTrue("didn't throw exception when matrix is added in wrong size", e.getMessage().equals("Matrix not the same size"));
        }
    }

    @Test
    public void addByMatrix() throws Exception{
        Matrix a = new Matrix(3,3,3),b = new Matrix(3,3,4);
        Matrix c;
        c = Matrix.addMatrix(a,b);
        a.addBy(b);
        for (int i = 0;i<a.row;i++)
            for (int j=0;j<a.col;j++)
                assertTrue ("not equal when using addBy function",a.matrix[i][j]==c.matrix[i][j]);
    }

}