(ns spacex-cljs.launches.events
  (:require [re-frame.core :refer [reg-event-fx reg-event-db]]
            [day8.re-frame.http-fx]
            [spacex-cljs.utils :as utils]))


(reg-event-fx
 :load-launches
 (fn [{:keys [db]} _]
   (if (-> db :launches seq)
     {:db db}
     {:http-xhrio (utils/create-request-map :get "/launches"
                                            :load-launches-ok)})))


(reg-event-db
 :load-launches-ok
 (fn [db [_ data]]
   (assoc db :launches data)))


(reg-event-db
 :selected-year
 (fn [db [_ data]]
   (assoc-in db [:filter :selected-year] data)))


(reg-event-db
 :selected-status
 (fn [db [_ data]]
   (assoc-in db [:filter :selected-status] data)))
