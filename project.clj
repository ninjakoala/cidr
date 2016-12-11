(defproject com.ninjakoala/cidr "1.0.2-SNAPSHOT"
  :description "Working with CIDR ranges"
  :url "https://github.com/ninjakoala/cidr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]]

  :profiles {:dev {:dependencies [[midje "1.8.3"]]
                   :plugins [[lein-midje "3.2.1"]]}}

  :eastwood {:namespaces [:source-paths]})
