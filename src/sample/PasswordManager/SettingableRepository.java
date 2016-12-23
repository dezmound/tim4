package sample.PasswordManager;

import sample.Repository.Repository;
import sample.SettingsService.Settings;

/**
 * Created by dmitry on 17.12.16.
 */
public interface SettingableRepository<T> extends Repository<T> {
    Settings getSettings();
}
