package sample.Repository;

/**
 * Created by dmitry on 16.12.16.
 */
public abstract class StorageFactory {
    public abstract Repository createRepository();
    public abstract Query createQuery(Object searchable);
    public abstract Query createQueryOr(Query left, Query right);
    public abstract Query createQueryAnd(Query left, Query right);
}
