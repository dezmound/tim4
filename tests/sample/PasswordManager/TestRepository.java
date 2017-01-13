package sample.PasswordManager;

import sample.Repository.Query;
import sample.Repository.Repository;
import sample.SettingsService.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dmitry on 28.12.16.
 */
public class TestRepository implements SettingableRepository {
    Settings settings = new Settings(new HashMap<>());
    List list = new ArrayList(Arrays.asList(new Profile("a", "b"), new Profile("b", "a")));
    @Override
    public List find(Query query) {
        return query.execute(list).fetch();
    }

    @Override
    public List findAll() {
        return list;
    }

    @Override
    public boolean add(Object entity) {
        return list.add(entity);
    }

    @Override
    public boolean remove(Object entity) {
        return list.remove(entity);
    }

    @Override
    public boolean save() {
        return true;
    }

    @Override
    public boolean init() {
        this.list = new ArrayList();
        return true;
    }

    @Override
    public Settings getSettings() {
        return this.settings;
    }
}
