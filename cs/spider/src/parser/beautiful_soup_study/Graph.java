package parser.beautiful_soup_study;

public class Graph {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    public static void main(String[] args) {

    }

    private static class Mountain {
        private int radius;
    }

    private static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            checkOrThrowX(x);
            checkOrThrowY(y);
            this.x = x;
            this.y = y;
        }

        private void checkOrThrowX(int x) {
            if (x < 0 || x > WIDTH) {
                throw new IllegalArgumentException();
            }
        }

        public void checkOrThrowY(int y) {
            if (y < 0 || y > HEIGHT) {
                throw new IllegalArgumentException();
            }
        }
    }

    
}