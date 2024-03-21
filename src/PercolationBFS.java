import java.util.*;

public class PercolationBFS extends PercolationDefault {
    public PercolationBFS(int n) {
        super(n);
    }

    @Override
    protected void search(int row, int col) {
        int[] rowDelta = { -1, 1, 0, 0 };
        int[] colDelta = { 0, 0, -1, 1 };
        Queue<int[]> queue = new LinkedList<>();
        myGrid[row][col] = FULL;
        queue.add(new int[] { row, col });

        while(queue.size() != 0){
            int[] cur = queue.remove();
            for (int k = 0; k < rowDelta.length; k++) {
                row = cur[0] + rowDelta[k];
                col = cur[1] + colDelta[k];
                if (inBounds(row, col) && (myGrid[row][col] == OPEN)) {
                    myGrid[row][col] = FULL;
                    queue.add(new int[] { row, col });
                }
            }

        }

        /*while (queue.size() != 0) {

            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            // out of bounds?
            if (!inBounds(r, c))
                return;

            // full or NOT open, don't process
            if (isFull(r, c) || !isOpen(r, c))
                return;

            myGrid[r][c] = FULL;

            queue.add(new int[] { r - 1, c });
            queue.add(new int[] { r, c - 1 });
            queue.add(new int[] { r, c + 1 });
            queue.add(new int[] { r + 1, c });
        }*/
    }
}