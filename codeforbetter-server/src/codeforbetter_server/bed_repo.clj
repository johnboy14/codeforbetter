(ns codeforbetter-server.bed-repo
  (:require [clojure.java.jdbc :as sql]
            [codeforbetter-server.db-config :refer :all]))

(defn create-bed
  [bed]
  (sql/with-connection db-spec
  (sql/insert-values :beds
                     [:name]
                     [(:name bed)]))
)

(defn all-beds
  []
  (sql/with-connection db-spec
  (sql/with-query-results rs ["select * from beds"]
    (doall (if (empty? rs) [] rs))))
)

(defn delete-bed
  [name]
  (sql/with-connection db-spec
    (sql/delete-rows :beds ["name=?" name])))
