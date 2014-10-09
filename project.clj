(defproject immutant-mr "0.1.0-SNAPSHOT"
  :description "Basic infinispan map reduce support for immutant"
  :url "http://github.com/danstone/immutant-mr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :java-source-paths ["src-java"]
  :profiles {:dev {:dependencies
                    [[org.immutant/immutant "1.1.0" :exclusions [common-codec]]]}}
  :immutant {:nrepl-port 4113
             :resolve-dependencies true})
