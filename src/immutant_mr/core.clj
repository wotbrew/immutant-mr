(ns immutant-mr.core
  (require [immutant.cache])
  (:import (org.infinispan.distexec.mapreduce Mapper Collector Reducer MapReduceTask)
           (clojure.lang MapEntry Var)
           (immutant.cache InfinispanCache)
           (immutant_mr InfinispanMapper InfinispanReducer)))

(defn kvmapper
  [^Var var]
  (InfinispanMapper. (-> var .ns .getName str)
                     (-> var .sym str)))

(defn kvreducer
  [^Var var init]
  (InfinispanReducer.
    (-> var .ns .getName str)
    (-> var .sym str)
    init))

(defn map-reduce-task
  [^InfinispanCache cache]
  (MapReduceTask. (.cache cache)))

(defn map-with
  [^MapReduceTask task mapper]
  (.mappedWith task mapper))

(defn reduce-with
  [^MapReduceTask task reducer]
  (.reducedWith task reducer))

(defn combine-with
  [^MapReduceTask task reducer]
  (.combinedWith task reducer))

(defn execute
  [^MapReduceTask task]
  (.execute task))
