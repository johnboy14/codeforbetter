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
      (:body get-beds-response) => (contains "\"available\":true")))

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
            (:body response) => (contains "\"available\":true")))

    (fact "Claim a bed for 30 mins, bed is now unavailable"
          (let [create-response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
                claim-response (app (mock/request :post "/bed/claim/bed1/30" ""))]
            (:status create-response) => 201
            (:body create-response) => (contains "\"available\":true")
            (:status claim-response) => 200
            (:body claim-response) => (contains "\"available\":false")))

    (fact "Release an occupied bed. Bed is available"
          (let [create-response (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
                claim-response (app (mock/request :post "/bed/claim/bed1/30" ""))
                release-response (app (mock/request :post "/bed/release/bed1" ""))]
            (:status release-response) => 200
            (:body release-response) => (contains "\"available\":true")))

    (fact "3 beds, 1 available, query available returns 1 bed"
          (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          (app (mock/request :put "/bed" "{\"name\": \"bed2\"}"))
          (app (mock/request :put "/bed" "{\"name\": \"bed3\"}"))
          (app (mock/request :post "/bed/claim/bed1/30" ""))
          (app (mock/request :post "/bed/claim/bed3/30" ""))
          (let [response (app (mock/request :get "/bed/available"))]
          (:status response) => 200
          (:body response) => (contains "\"name\":\"bed2\"")
          (:body response) => (contains "\"available\":true")))

    (fact "3 beds, 1 available, query unavailable returns 2 beds"
          (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          (app (mock/request :put "/bed" "{\"name\": \"bed2\"}"))
          (app (mock/request :put "/bed" "{\"name\": \"bed3\"}"))
          (app (mock/request :post "/bed/claim/bed1/60" ""))
          (app (mock/request :post "/bed/claim/bed3/60" ""))
          (let [response (app (mock/request :get "/bed/unavailable"))]
          (:status response) => 200
          (:body response) => (contains "\"name\":\"bed1\"")
          (:body response) => (contains "\"name\":\"bed3\"")
          (:body response) => (contains "\"available\":false")))

    (fact "Claim a bed for 10mins. Query for beds availabe next 20mins, should return bed"
          (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          (app (mock/request :post "/bed/claim/bed1/10" ""))
          (let [response (app (mock/request :get "/bed/available/20"))]
            (:status response) => 200
            (:body response) => (contains "\"name\":\"bed1\"")
          ))

        (fact "Claim a bed for 20mins. Query for beds availabe next 10mins, should not return bed"
          (app (mock/request :put "/bed" "{\"name\": \"bed1\"}"))
          (app (mock/request :post "/bed/claim/bed1/20" ""))
          (let [response (app (mock/request :get "/bed/available/10"))]
            (:status response) => 200
            (:body response) => "[]"
          ))

