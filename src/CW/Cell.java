package CW;

class Cell {
    boolean visited;
    private Cell leftCell;
    private Cell upCell;
    private Cell rightCell;
    private Cell downCell;
    private boolean leftWall;
    private boolean upWall;
    private boolean rightWall;
    private boolean downWall;

    Cell() {
        visited = false;
        leftCell = null;
        upCell = null;
        rightCell = null;
        downCell = null;
        leftWall = true;
        upWall = true;
        rightWall = true;
        downWall = true;
    }

    Cell addToRight(Cell cell2){
        this.rightCell = cell2;
        cell2.leftCell = this;
        return cell2;
    }

    Cell addToDown(Cell cell2){
        this.downCell = cell2;
        cell2.upCell = this;
        return cell2;
    }

//    void makeWall(Cell cell2) {
//        if (leftCell != null && leftCell == cell2) {
//            leftWall = true;
//            cell2.rightWall = true;
//        } else if (upCell != null && upCell == cell2){
//            upWall = true;
//            cell2.downWall = true;
//        } else if (rightCell != null && rightCell == cell2) {
//            rightWall = true;
//            cell2.leftWall = true;
//        } else if (downCell != null && downCell == cell2){
//            downWall = true;
//            cell2.upWall = true;
//        } else {
//            System.out.println("Странное поведение метода Cell.makeWall");
//        }
//    }

    synchronized void makePass(Cell cell2) {
        if (leftCell == cell2) {
            leftWall = false;
            cell2.rightWall = false;
        } else if (upCell == cell2){
            upWall = false;
            cell2.downWall = false;
        } else if (rightCell == cell2) {
            rightWall = false;
            cell2.leftWall = false;
        } else if (downCell == cell2){
            downWall = false;
            cell2.upWall = false;
        } else {
            System.out.println("Странное поведение метода Cell.makePass");
        }
    }

    int wallCode() {
        int code = 0;
        if (!leftWall) code += 1;
        if (!upWall) code += 2;
        if (!rightWall) code += 4;
        if (!downWall) code += 8;
        return code;
    }

    synchronized int isThereUnvisitedNeighborsG() { //Вариант функции для генератора лабиринтов
        int num = 0;
        if (leftCell != null && !leftCell.visited) num++;
        if (upCell != null && !upCell.visited) num++;
        if (rightCell != null && !rightCell.visited) num++;
        if (downCell != null && !downCell.visited) num++;
        return num;
    }

    int isThereUnvisitedNeighborsPF() { //Вариант функции для поиска пути
        int num = 0;
        if (leftCell != null && !leftCell.visited && !leftWall) num++; //обдумать порядок действий
        if (upCell != null && !upCell.visited && !upWall) num++;
        if (rightCell != null && !rightCell.visited && !rightWall) num++;
        if (downCell != null && !downCell.visited && !downWall) num++;
        return num;
    }

    Cell getUnvisitedNeighborG() {
        int code = 0;
        if ((leftCell != null) && !leftCell.visited) code += 1;
        if ((upCell != null) && !upCell.visited) code += 2;
        if ((rightCell != null) && !rightCell.visited) code += 4;
        if ((downCell != null) && !downCell.visited) code += 8;

        switch (code) {
            case 0:
                return null;
            case 1:
                return leftCell;
            case 2:
                return upCell;
            case 3:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                }
                break;
            case 4:
                return rightCell;
            case 5:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return rightCell;
                }
                break;
            case 6:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return upCell;
                    case 1:
                        return rightCell;
                }
                break;
            case 7:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                    case 2:
                        return rightCell;
                }
                break;
            case 8:
                return downCell;
            case 9:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return downCell;
                }
                break;
            case 10:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return upCell;
                    case 1:
                        return downCell;
                }
                break;
            case 11:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                    case 2:
                        return downCell;
                }
                break;
            case 12:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return rightCell;
                    case 1:
                        return downCell;
                }
                break;
            case 13:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return rightCell;
                    case 2:
                        return downCell;
                }
                break;
            case 14:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return upCell;
                    case 1:
                        return rightCell;
                    case 2:
                        return downCell;
                }
                break;
            case 15:
                switch ((int) (Math.random() * 4)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                    case 2:
                        return rightCell;
                    case 3:
                        return downCell;
                }
                break;
        }
        return null;
    }

    Cell getUnvisitedNeighborPF() {
        int code = 0;
        if ((leftCell != null) && !leftCell.visited && !leftWall) code += 1;
        if ((upCell != null) && !upCell.visited && !upWall) code += 2;
        if ((rightCell != null) && !rightCell.visited && !rightWall) code += 4;
        if ((downCell != null) && !downCell.visited && !downWall) code += 8;

        switch (code) {
            case 0:
                return null;
            case 1:
                return leftCell;
            case 2:
                return upCell;
            case 3:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                }
                break;
            case 4:
                return rightCell;
            case 5:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return rightCell;
                }
                break;
            case 6:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return upCell;
                    case 1:
                        return rightCell;
                }
                break;
            case 7:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                    case 2:
                        return rightCell;
                }
                break;
            case 8:
                return downCell;
            case 9:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return downCell;
                }
                break;
            case 10:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return upCell;
                    case 1:
                        return downCell;
                }
                break;
            case 11:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                    case 2:
                        return downCell;
                }
                break;
            case 12:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return rightCell;
                    case 1:
                        return downCell;
                }
                break;
            case 13:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return rightCell;
                    case 2:
                        return downCell;
                }
                break;
            case 14:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return upCell;
                    case 1:
                        return rightCell;
                    case 2:
                        return downCell;
                }
                break;
            case 15:
                switch ((int) (Math.random() * 4)) {
                    case 0:
                        return leftCell;
                    case 1:
                        return upCell;
                    case 2:
                        return rightCell;
                    case 3:
                        return downCell;
                }
                break;
        }
        return null;
    }
}
