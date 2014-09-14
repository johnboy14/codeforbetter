(ns codeforbetter-server.bed-repo
  (:require [clojure.java.jdbc :as sql]
            [codeforbetter-server.db-config :refer :all]
            [clj-time.core :as clj-time]
            [clj-time.local :as l]
[clj-time.coerce :as clj-time-coerce]))

(defn coerce-with-time
  [rs]
  (vec (map #(conj {}{:name (:name %) :available (:available %) :freeat (clj-time-coerce/from-long (:freeat %))}) rs)))

(defn coerce
  [rs]
  (vec (map #(conj {}{:name (:name %) :available (:available %)}) rs)))

(defn update-bed
  [bed]
  (sql/with-connection db-spec
    (sql/update-values :beds
                       ["name=?" (:name bed)]
                       {:available (:available bed)
                        :freeat (clj-time-coerce/to-long(:freeat bed))})))

(defn create-bed
  [bed]
  (sql/with-connection db-spec
  (sql/insert-values :beds
                     [:name :available]
                     [(:name bed) true]))
)

(defn all-beds
  []
  (sql/with-connection db-spec
  (sql/with-query-results rs ["select * from beds"]
    (doall (coerce rs))))
)

(defn single-bed
  [name]
  (sql/with-connection db-spec
    (sql/with-query-results rs ["select * from beds where name=?" name]
      (doall (if (empty? rs) (throw Exception) (first (coerce rs)))))))

(defn delete-bed
  [name]
  (sql/with-connection db-spec
    (sql/delete-rows :beds ["name=?" name])))


(defn available-beds
  []
  (sql/with-connection db-spec
    (sql/with-query-results rs ["select * from beds where available=true"]
      (doall (if (empty? rs) [] (coerce rs))))))

(defn unavailable-beds
  []
  (sql/with-connection db-spec
    (sql/with-query-results rs ["select * from beds where available=false"]
      (doall (if (empty? rs) [] (coerce rs))))))


(defn available-beds-in
  [duration]
  (sql/with-connection db-spec
    (map #(dissoc % :freeat)(filter #(clj-time/before? (:freeat %) (clj-time/plus (l/local-now) (clj-time/seconds (read-string duration)))) (sql/with-query-results rs ["select * from beds where available=false"]
      (doall (if (empty? rs) [] (coerce-with-time rs))))))))

(available-beds-in "1")
