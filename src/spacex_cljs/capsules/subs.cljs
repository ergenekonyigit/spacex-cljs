(ns spacex-cljs.capsules.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
 :capsules
 (fn [db _]
   (:capsules db)))
