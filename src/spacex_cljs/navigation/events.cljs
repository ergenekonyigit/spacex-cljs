(ns spacex-cljs.navigation.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
