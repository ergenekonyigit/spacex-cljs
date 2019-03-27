(ns spacex-cljs.rockets.events
  (:require [re-frame.core :refer [reg-event-fx reg-event-db]]
            [day8.re-frame.http-fx]
            [spacex-cljs.utils :as utils]))


(reg-event-fx
 :load-rockets
 (fn [{:keys [db]} _]
   (if (-> db :rockets seq)
     {:db db}
     {:http-xhrio (utils/create-request-map :get "/rockets"
                                            :load-rockets-ok)})))


(reg-event-db
 :load-rockets-ok
 (fn [db [_ data]]
   (assoc db :rockets data)))
