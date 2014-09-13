(ns codeforbetter-server.route-actions
  (:require [codeforbetter-server.bed-repo :as bedrepo]))

(defn all-beds [] (bedrepo/all-beds))

(defn add-bed [bed]
  (bedrepo/create-bed bed)
  bed)
