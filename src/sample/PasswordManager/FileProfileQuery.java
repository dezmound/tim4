package sample.PasswordManager;

import sample.Repository.Query;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dmitry on 23.12.16.
 */
public class FileProfileQuery implements Query {
    private List<Profile> profiles;
    private Profile searchable;

    public FileProfileQuery(Profile searchable){
        this.searchable = searchable;
    }
    @Override
    public Query execute(Object entity) {
        this.profiles = ((List<Profile>)entity).stream().filter(p -> {
            return p.equals(searchable);
        }).collect(Collectors.toList());
        return this;
    }

    @Override
    public List fetch() {
        return profiles;
    }
}
