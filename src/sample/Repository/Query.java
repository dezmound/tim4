package sample.Repository;

import java.util.List;

/**
 * Created by dmitry on 16.12.16.
 */
public interface Query<T, E> {
    Query execute(E entity);
    List<T> fetch();
}
