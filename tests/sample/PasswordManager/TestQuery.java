package sample.PasswordManager;

import sample.Repository.Query;

import java.util.List;

/**
 * Created by dmitry on 28.12.16.
 */
public class TestQuery implements Query{
    Object entity;
    @Override
    public Query execute(Object entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public List fetch() {
        return (List)entity;
    }
}
