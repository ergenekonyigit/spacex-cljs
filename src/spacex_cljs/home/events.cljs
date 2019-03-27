(ns spacex-cljs.home.events
  (:require [re-frame.core :refer [reg-event-fx reg-event-db]]
            [day8.re-frame.http-fx]
            [spacex-cljs.utils :as utils]))


(reg-event-fx
 :load-info
 (fn [{:keys [db]} _]
   (if (-> db :info seq)
     {:db db}
     {:http-xhrio (utils/create-request-map :get "/info"
                                            :load-info-ok)})))


(reg-event-db
 :load-info-ok
 (fn [db [_ data]]
   (assoc db :info data)))
