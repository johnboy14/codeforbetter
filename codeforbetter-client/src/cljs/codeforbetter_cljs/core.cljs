(ns codeforbetter-client.core
  (:require [cljs-http.client :as http]
            [cognitect.transit :refer :all])
  (:use [domina :only [by-id value set-value by-class]]))

(defn init []
    (let [response (http/get "http://localhost:3000/bed/available")]
      (set-value (by-class "beds") response)))

(set! (.-onload js/window) init)

