package sample.Repository;

import java.util.HashSet;
import java.util.List;

/**
 * Created by dmitry on 23.12.16.
 */
public class ListedQueryAnd extends QueryAnd {
    private List result;
    public List getResult(){
        return this.result;
    }
    public ListedQueryAnd(Query left, Query right) {
        super(left, right);
        this.result = null;
    }

    @Override
    public Query execute(Object entity) {
        this.result = ((List)this.left.execute(entity)).retainAll((List)this.right.execute(entity)) ? this.left.fetch() : null;
        return this;
    }

    @Override
    public List fetch() {
        return this.result;
    }
}
