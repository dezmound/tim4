package sample.Repository;

import java.util.List;

/**
 * Created by dmitry on 16.12.16.
 */
public interface Repository<T> {
    List<T> find(Query query);
    List<T> findAll();
    boolean add(T entity);
    boolean remove(T entity);
    boolean save();
    boolean init();
}
