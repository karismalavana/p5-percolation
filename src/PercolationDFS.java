import java.util.Stack;

public class PercolationDFS extends PercolationDefault {

    public PercolationDFS(int n) {
        super(n);
    }

    @Override
    protected void search(int row, int col) {
        Stack<int[]> stack = new Stack<>();
        int[] rowDelta = { -1, 1, 0, 0 };
        int[] colDelta = { 0, 0, -1, 1 };
        myGrid[row][col] = FULL;
        stack.push(new int[] { row, col });

        while (stack.size() != 0) {
            int[] cur = stack.pop();
            for (int k = 0; k < rowDelta.length; k++) {
                row = cur[0] + rowDelta[k];
                col = cur[1] + colDelta[k];
                if (inBounds(row, col) && (myGrid[row][col] == OPEN)) {
                    myGrid[row][col] = FULL;
                    stack.push(new int[] { row, col });
                }
            }
        }

        /*
         * while (stack.size() != 0) {
         * int[] cur = stack.pop();
         * int r = cur[0], c = cur[1];
         * 
         * if (!inBounds(r, c))
         * return;
         * 
         * // full or NOT open, don't process
         * if (isFull(r, c) || !isOpen(r, c))
         * return;
         * 
         * myGrid[r-1][c] = FULL;
         * stack.push(new int[] { r - 1, c });
         * 
         * myGrid[r][c-1] = FULL;
         * stack.push(new int[] { r, c - 1 });
         * 
         * myGrid[r][c+1] = FULL;
         * stack.push(new int[] { r, c + 1 });
         * 
         * myGrid[r+1][c] = FULL;
         * stack.push(new int[] { r + 1, c });
         * 
         * }
         */

    }

}
