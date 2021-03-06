package CW;

import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Generator{

    public long serialGenerator(Maze maze) {
        System.out.println("Начало генерации лабиринта");
        long start = System.currentTimeMillis();
        Cell currentCell = maze.getCellAt(0, 0);
        Cell startingCell = maze.getCellAt(0, 0);
        Stack<Cell> stack = new Stack<>();

        do {
            currentCell.visited = true;
            if (currentCell.isThereUnvisitedNeighborsG() != 0) {
                stack.push(currentCell);
                Cell randNeighbor = currentCell.getUnvisitedNeighborG();
                currentCell.makePass(randNeighbor);
                //randNeighbor.visited = true;
                currentCell = randNeighbor;
            } else if (!stack.empty()) {
                currentCell = stack.peek();
                stack.pop();
            }
        }while (currentCell != startingCell);

        long end = System.currentTimeMillis();
        maze.makeAllUnvisited();
        System.out.println("Лабиринт сгенерирован");
        return end - start;
    }

    static private volatile int workingThreads = 0;
    static final private int MAX_THREADS = 4;
    private ExecutorService service = Executors.newFixedThreadPool(4);

    public long startParallel(Maze maze) {
        long start = System.currentTimeMillis();
        final Cell staringCell = maze.getCellAt(0,0);
        try {
            service.submit(() -> {
                try {
                    parallelGenerator(maze, staringCell);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            long end = System.currentTimeMillis();
            return end - start;
        } catch (NullPointerException e){
            e.printStackTrace();
            return -2;
        }
    }

    public void parallelGenerator(Maze maze, Cell startingCell) throws InterruptedException {
        workingThreads++;
        //System.out.println("Начало генерации потоком " + Thread.currentThread().getName());

        Cell currentCell = startingCell;
        currentCell.visited = true;
        Stack<Cell> stack = new Stack<>();
        int count = 0;

        do {
            int numOfNeighbors = currentCell.isThereUnvisitedNeighborsG();

            if (numOfNeighbors > 1 && workingThreads <= MAX_THREADS && count == 5) {
                numOfNeighbors--;
                final Cell newStartingCell = currentCell.getUnvisitedNeighborG();
                newStartingCell.visited = true;
                currentCell.makePass(newStartingCell);

                service.submit(() -> {
                    try {
                        parallelGenerator(maze, newStartingCell);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

            if (numOfNeighbors > 0){
                stack.push(currentCell);
                Cell randNeighbor = currentCell.getUnvisitedNeighborPF();
                currentCell.makePass(randNeighbor);
                randNeighbor.visited = true;
                currentCell = randNeighbor;

            } else if (!stack.empty()) {
                currentCell = stack.peek();
                stack.pop();
            }

            if (count == 5) count = 0;
            else count++;

            maze.print();
        } while (currentCell != startingCell);

        workingThreads--;
        //System.out.println("Поток " + Thread.currentThread().getName() + " завершил работу");
    }
}

