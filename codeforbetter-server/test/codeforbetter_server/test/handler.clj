(ns codeforbetter-server.test.handler
  (:require [codeforbetter-server.db-config :refer :all]
            [codeforbetter-server.handler :refer :all]
            [ring.mock.request :as mock])
  (:use midje.sweet))

(background (before :facts (clean-tables)))

    (fact "Search for beds, returns no beds"
    (let [response (app (mock/request :get "/bed"))]
      (:status response) => 200
      (:body response) => "[]"))

    (fact "Add a bed, return new bed"
    (let [response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))]
      (:status response) => 201
      (:body response) =>(contains "\"name\":\"bed1\"")))

    (fact "Add an already existing bed, return 400 response"
    (let [response1 (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          response2 (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))]
      (:status response2) => 400))

    (fact "Add a bed, return a vector containing new bed"
    (let [add-bed-response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          get-beds-response (app (mock/request :get "/bed"))]
      (:status get-beds-response) => 200
      (:body get-beds-response) => (contains "\"name\":\"bed1\"")
      (:body get-beds-response) => (contains "\"available\":false")))

    (fact "Delete a bed, should return 200 response"
    (let [response (app (mock/request :delete "/bed/whatevs"))]
      (:status response) => 200))

    (fact "Delete a bed, should not find bed"
    (let [add-bed-response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          response (app (mock/request :delete "/bed/bed1"))
          get-beds-response (app (mock/request :get "/bed"))]
      (:body get-beds-response) => "[]"))

    (fact "Create a bed, status is available"
          (let [response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))]
            (:body response) => (contains "\"available\":false")))

