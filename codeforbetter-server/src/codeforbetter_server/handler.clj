(ns codeforbetter-server.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [codeforbetter-server.route-actions :refer :all]
            [codeforbetter-server.db-config :as db]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (generate-string data)})

(defroutes app-routes
  (GET "/bed" [] (json-response (all-beds)))
  (PUT "/bed" request (try (json-response (add-bed (parse-string (slurp (:body request)) true)) 201) (catch Exception e {:status 400 :body e})))
  (DELETE "/bed/:name" [name] (delete-bed name))
  (POST "/bed/claim/:name" [name] (json-response (claim-bed name)))
  (POST "/bed/release/:name" [name] (json-response (release-bed name)))
  (GET "/bed/available" [] (json-response (available-beds)))
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init []
  (println "app is starting")
  (if-not (.exists (java.io.File. "codeforbetter.db.h2.db"))
    (db/create-tables)))

(defn destroy []
  (println "shutdown"))

(def app
  (handler/site app-routes))
