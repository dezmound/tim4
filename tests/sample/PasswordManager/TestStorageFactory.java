package sample.PasswordManager;

import sample.Repository.Query;
import sample.Repository.Repository;
import sample.Repository.StorageFactory;

/**
 * Created by dmitry on 28.12.16.
 */
public class TestStorageFactory extends StorageFactory {
    @Override
    public Repository createRepository() {
        return new TestRepository();
    }

    @Override
    public Query createQuery(Object searchable) {
        return null;
    }

    @Override
    public Query createQueryOr(Query left, Query right) {
        return null;
    }

    @Override
    public Query createQueryAnd(Query left, Query right) {
        return null;
    }
}
