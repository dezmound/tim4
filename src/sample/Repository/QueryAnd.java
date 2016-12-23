package sample.Repository;

import javafx.scene.control.Tab;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dmitry on 16.12.16.
 */
public abstract class QueryAnd implements Query {
    protected Query left;
    protected Query right;
    public QueryAnd(Query left, Query right){

        this.left = left;
        this.right = right;
    }
}
