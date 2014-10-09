package immutant_mr;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.infinispan.distexec.mapreduce.Reducer;

import java.util.Iterator;

public class InfinispanReducer implements Reducer<Object, Object>
{
    private final String ns;
    private final String sym;
    private final Object seed;
    private IFn fn;

    public InfinispanReducer(String ns, String sym, Object seed) {
        this.ns = ns;
        this.sym = sym;
        this.seed = seed;
    }

    @Override
    public Object reduce(Object o, Iterator<Object> objectIterator) {

        if(fn == null) {
            IFn require = Clojure.var("clojure.core", "require");
            require.invoke(Clojure.read(ns));
            fn = Clojure.var(ns, sym);
        }

        Object result = seed;
        while(objectIterator.hasNext()){
            Object x = objectIterator.next();
            result = fn.invoke(o, result, x);
        }
        return result;
    }
}
