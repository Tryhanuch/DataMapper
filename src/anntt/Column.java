package anntt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by tish on 18.05.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name();
}
