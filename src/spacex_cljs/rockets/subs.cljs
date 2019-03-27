(ns spacex-cljs.rockets.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
 :rockets
 (fn [db _]
   (:rockets db)))
