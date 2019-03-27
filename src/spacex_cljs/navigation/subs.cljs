(ns spacex-cljs.navigation.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))
