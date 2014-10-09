package immutant_mr;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.ISeq;
import clojure.lang.Seqable;
import clojure.lang.Var;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;


public class InfinispanMapper implements Mapper<Object, Object, Object, Object> {

    private final String ns;
    private final String sym;
    private IFn fn;

    public InfinispanMapper(String ns, String sym) {
        this.ns = ns;
        this.sym = sym;
    }

    public void map(Object key, Object value, Collector<Object, Object> collector){

        if(fn == null) {
            IFn require = Clojure.var("clojure.core", "require");
            require.invoke(Clojure.read(ns));
            fn = Clojure.var(ns, sym);
        }

        for (Object o : (Iterable)fn.invoke(key, value)){
            Seqable s = (Seqable)o;
            ISeq seq = s.seq();
            Object k = seq.first();
            Object v = seq.next().first();
            collector.emit(k, v);
        }
    }
}
