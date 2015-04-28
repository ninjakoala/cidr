(defproject com.ninjakoala/cidr "1.0.1-SNAPSHOT"
  :description "Working with CIDR ranges"
  :url "https://github.com/ninjakoala/cidr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]]

  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}}

  :eastwood {:namespaces [:source-paths]})
