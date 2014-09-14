(ns codeforbetter-server.route-actions
  (:require [codeforbetter-server.bed-repo :as bedrepo]
            [clj-time.local :as l]
            [clj-time.core :as tm]))


(defn all-beds [] (bedrepo/all-beds))

(defn add-bed [bed]
  (bedrepo/create-bed bed)
  (bedrepo/single-bed (:name bed)))

(defn delete-bed [name]
  (bedrepo/delete-bed name))

(defn claim-bed [name duration]
  (bedrepo/update-bed {:name name :available false :freeat (tm/plus (l/local-now) (tm/minutes (read-string duration)))})
  (bedrepo/single-bed name))

(defn release-bed [name]
  (bedrepo/update-bed {:name name :available true})
  (bedrepo/single-bed name))

(defn available-beds []
  (bedrepo/available-beds))

(defn available-beds-in [duration]
  (bedrepo/available-beds-in duration))
