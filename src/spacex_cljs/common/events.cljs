(ns spacex-cljs.common.events
  (:require [re-frame.core :refer [reg-event-fx]]
            [spacex-cljs.db :as db]))


(reg-event-fx
 :initialise-db
 (fn [{:keys [_ _]} _]
   {:db (assoc db/default-db :filter {:selected-year "Any"
                                      :selected-status "Any"})}))
