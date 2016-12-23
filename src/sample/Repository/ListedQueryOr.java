package sample.Repository;

import java.util.List;

/**
 * Created by dmitry on 23.12.16.
 */
public class ListedQueryOr extends QueryOr {
    private List result;
    public ListedQueryOr(Query left, Query right) {
        super(left, right);
    }

    @Override
    public Query execute(Object entity) {
        this.result = ((List)this.left.execute(entity).fetch()).addAll((List)this.right.execute(entity).fetch()) ? this.left.fetch() : null;
        return this;
    }

    @Override
    public List fetch() {
        return this.result;
    }
}
