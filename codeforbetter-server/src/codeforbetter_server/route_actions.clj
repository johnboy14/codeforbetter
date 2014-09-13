(ns codeforbetter-server.route-actions
  (:require [codeforbetter-server.bed-repo :as bedrepo]))

(defn all-beds [] (bedrepo/all-beds))

(defn add-bed [bed]
  (bedrepo/create-bed bed)
  (bedrepo/single-bed (:name bed)))

(defn delete-bed [name]
  (bedrepo/delete-bed name))

(defn claim-bed [name]
  (bedrepo/update-bed {:name name :available false})
  (bedrepo/single-bed name))
