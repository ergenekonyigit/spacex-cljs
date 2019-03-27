(ns spacex-cljs.utils
  (:require [ajax.core :as ajax]))

(defonce api-url "https://api.spacexdata.com/v2")


(defn create-request-map
  ([type uri on-success]
   (create-request-map type uri on-success nil))
  ([type uri on-success on-failure]
   (cond-> {:method type
            :uri (str api-url uri)
            :format (ajax/json-request-format)
            :response-format (ajax/json-response-format {:keywords? true})
            :on-success (if (vector? on-success) on-success [on-success])
            :on-failure (if (vector? on-failure) on-failure [on-failure])}
     (nil? on-failure) (dissoc on-failure))))
