import java.io.*;
import java.util.Arrays;

public class SparseArray implements Serializable {
    public static final long serialVersionUID = 200L;
    int row;
    int col;
    int distinct;
    int[][] array;

    public SparseArray() {
        // Empty constructor is necessary for serialization
    }

    public SparseArray(int[][] array, int row, int col) {
        this.array = array;
        this.row = row;
        this.col = col;
        this.distinct = array.length;
    }

    public static SparseArray from2DArray(int[][] array, int de) {
        // 遍历获取非 0 数据的个数
        int sum = (int) Arrays.stream(array).flatMapToInt(Arrays::stream).filter(e -> e != de).count();
        int[][] sparseArray = new int[sum + 1][3];
        // 元数据，行、列、不同元素数
        int row = array.length, col = array[0].length;
        // 再次遍历二维数组进行存储。需要两次遍历，因为这样可以避免使用动态的集合列。
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                int res = array[i][j];
                if (res != 0) {
                    // 行、列、元素
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = res;
                    count++;
                }
            }
        }
        return new SparseArray(sparseArray, row, col);
    }

    public int[][] to2DArray() {
        // 行，列
        int[][] result = new int[row][col];
        // 从第二行开始读取数组内的数据
        for (int[] row : array) {
            result[row[0]][row[1]] = row[2];
        }
        return result;
    }

    @Override
    public String toString() {
        var result = new StringBuilder(array.length << 2);
        result.append(String.format("row=%d, col=%d, distinct=%d\n", row, col, distinct));
        for (int[] row : array) {
            result.append(String.format("%d\t%d\t%d\n", row[0], row[1], row[2]));
        }
        return result.toString();
    }

    public static void print(int[][] array) {
        for (int[] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        // 创建原始的二维数组
        int[][] chessArray = new int[11][11];
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        chessArray[4][5] = 2;
        // 原始的二维数组
        print(chessArray);
        // 创建稀疏数组
        SparseArray array = from2DArray(chessArray, 0);
        System.out.println(array);
        print(array.to2DArray());
        // 测试 IO
        try (var out = new ObjectOutputStream(new FileOutputStream("ChessArray.data"))) {
            out.writeObject(array);
        } catch (IOException ignored) {
        }
        try (var in = new ObjectInputStream(new FileInputStream("ChessArray.data"))) {
            array = (SparseArray) in.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        System.out.println(array);
    }
}
