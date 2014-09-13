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
      (:body response) =>"{\"name\":\"bed1\"}"))

    (fact "Add a bed, return a vector containing new bed"
    (let [add-bed-response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          get-beds-response (app (mock/request :get "/bed"))]
      (:status get-beds-response) => 200
      (:body get-beds-response) => "[{\"name\":\"bed1\"}]"))

    (fact "Delete a bed, should return 200 response"
    (let [response (app (mock/request :delete "/bed/whatevs"))]
      (:status response) => 200))

    (fact "Delete a bed, should not find bed"
    (let [add-bed-response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          response (app (mock/request :delete "/bed/bed1"))
          get-beds-response (app (mock/request :get "/bed"))]
      (:body get-beds-response) => "[]"))



