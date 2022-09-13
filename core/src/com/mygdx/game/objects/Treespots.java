package com.mygdx.game.objects;

public class Treespots {

    private int Tree_X;
    private int Tree_Y;


    public Treespots(int tree_X, int tree_Y) {
        Tree_X = tree_X;
        Tree_Y = tree_Y;
    }

    public int getTree_X() {
        return Tree_X;
    }

    public void setTree_X(int tree_X) {
        Tree_X = tree_X;
    }

    public int getTree_Y() {
        return Tree_Y;
    }

    public void setTree_Y(int tree_Y) {
        Tree_Y = tree_Y;
    }

    @Override
    public String toString() {
        return "Treespots{" +
                "Tree_X=" + Tree_X +
                ", Tree_Y=" + Tree_Y +
                '}';
    }
}
