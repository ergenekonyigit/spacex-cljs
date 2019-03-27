(ns spacex-cljs.launches.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
 :launches
 (fn [db _]
   (:launches db)))


(reg-sub
 :selected-year
 (fn [db _]
   (-> db :filter :selected-year)))


(reg-sub
 :selected-status
 (fn [db _]
   (-> db :filter :selected-status)))
