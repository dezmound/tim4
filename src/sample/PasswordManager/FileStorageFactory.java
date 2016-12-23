package sample.PasswordManager;

import sample.Repository.*;
import sample.SettingsService.Settings;

/**
 * Created by dmitry on 16.12.16.
 */
public class FileStorageFactory extends StorageFactory {
    private Settings settings;
    public FileStorageFactory(Settings settings){
        this.settings = settings;
    }
    public Settings getSettings(){
        return this.settings;
    }
    @Override
    public Repository createRepository() {
        return new FileProfileRepository((Settings) settings.get("FileProfileRepositorySettings"));
    }

    @Override
    public Query createQuery(Object searchable) {
        return new FileProfileQuery((Profile) searchable);
    }

    @Override
    public Query createQueryOr(Query left, Query right) {
        return new ListedQueryOr(left, right);
    }

    @Override
    public Query createQueryAnd(Query left, Query right) {
        return new ListedQueryAnd(left, right);
    }
}
