package mpp;

import java.util.List;

/**
 * Created by tish on 04.05.2014.
 */
public interface IDataMapper {

    void save(Object o);

    Object load(long id, Class clazz);

    List<Object> loadAll(Class clazz);

    void update(Object o);

    void delete(long id, Class clazz);
}
