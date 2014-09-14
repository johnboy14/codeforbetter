(ns codeforbetter-server.db-config
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))

(def db-store "codeforbetter.db")

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname db-store
              :user "sa"
              :password ""
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})

(defn initialized?
  "Checks that schema is present"
  []
  (.exists (new java.io.File (str db-store ".h2.db"))))

(defn create-bed-table
  []
  (sql/with-connection db-spec
    (sql/create-table
     :beds
     [:name "varchar(255) PRIMARY KEY"]
     [:available "boolean"]
     [:freeat "bigint"])))

(defn create-tables
  "Creates tables"
  []
  (create-bed-table))

(defn drop-tables
  []
  (sql/with-connection db-spec
    (sql/drop-table :beds)))

(defn clean-tables
  []
  (drop-tables)
  (create-tables))

;(sql/with-connection db-spec
;  (sql/insert-values :beds
;                     [:name]
;                     ["johnsbed"]))

;(create-tables)

;(sql/with-connection db-spec
 ; (sql/with-query-results rs ["select * from beds"]
  ;  (doseq [row rs] (println (:name row)))))


