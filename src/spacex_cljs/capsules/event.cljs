(ns spacex-cljs.capsules.events
  (:require [re-frame.core :refer [reg-event-fx reg-event-db]]
            [day8.re-frame.http-fx]
            [spacex-cljs.utils :as utils]))


(reg-event-fx
 :load-capsules
 (fn [{:keys [db]} _]
   (if (-> db :capsules seq)
     {:db db}
     {:http-xhrio (utils/create-request-map :get "/capsules"
                                            :load-capsules-ok)})))


(reg-event-db
 :load-capsules-ok
 (fn [db [_ data]]
   (assoc db :capsules data)))
