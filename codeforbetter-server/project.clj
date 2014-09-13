(defproject codeforbetter-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [compojure "1.1.8"]
                 [cheshire "5.3.1"]
                 [korma "0.3.0"]
                 [com.h2database/h2 "1.3.170"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [midje "1.6.3"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-midje "3.1.3"]]
  :ring {:handler codeforbetter-server.handler/app
         :init codeforbetter-server.handler/init
         :destroy codeforbetter-server.handler/destroy}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
