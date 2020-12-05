package amu.gl.equipe200.IAEngine;

import java.util.*;

public class AStar {
    Grid grid;
    Cell goal;
    private Heuristic heuristique;

    public AStar(Grid grid){
        this.grid = grid;
    }

    public ArrayList<Cell> start(Cell start, Cell goal) {
        this.goal = goal;
        Stack<Cell> open = new Stack<>();
        Stack<Cell> close = new Stack<>();
        HashMap<Cell, Cell> cameFrom = new HashMap<>();
        open.addAll(grid.getPossibilities(start));
        this.heuristique = new Heuristic(goal);
        open.sort(heuristique);
        close.add(start);

        Cell current;
        while (!open.empty()) {
            current = open.pop();
            //System.out.println(current);
            close.push(current);
            if (current.equals(goal)){
                ArrayList<Cell> res = new ArrayList<>(close);
                return res;
            }else {
                open.addAll(getChilds(current, open, close));
                open.sort(new Heuristic(goal));
                open.remove(current);
            }

        }
        return new ArrayList<>();
    }

    private ArrayList<Cell> getChilds(Cell current, Stack<Cell> open, Stack<Cell> close) {
        ArrayList<Cell> childs = grid.getPossibilities(current);
        ArrayList<Cell> childtoRemove = new ArrayList<>();
        for (Cell child : childs){
            for (Cell opened : open){
                if (child.equals(opened)){
                    if (heuristique.compare(child, opened) >= 0){
                        childtoRemove.add(child);
                        break;
                    }
                }
            }
        }
        childs.removeAll(childtoRemove);
        childtoRemove.clear();

        for (Cell child : childs){
            for (Cell closed : close){
                if (child.equals(closed)){
                    if (heuristique.compare(child, closed) >= 0){
                        childtoRemove.add(child);
                        break;
                    }
                }
            }
        }
        childs.removeAll(childtoRemove);
        //System.out.println(childs);
        return childs;
    }

    private ArrayList<Cell> reconstructPath(Cell cameFrom, Cell current){
        return new ArrayList<>();
    }

    private class Heuristic implements Comparator<Cell>{
        Cell goal;
        float p;

        public Heuristic(Cell goal){
            this.goal = goal;
            this.p = 0.25f;
        }

        @Override
        public int compare(Cell o1, Cell o2) {
            float res = distance(o2) - (distance(o1));
            //System.out.println(res);
            return (int) res;
        }

        private float distance(Cell cell){
            int dx = Math.abs(cell.x - goal.x);
            int dy = Math.abs(cell.y - goal.y);

            return p * (dx * dx + dy * dy);
        }
    }
}
