(ns spacex-cljs.home.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
 :info
 (fn [db _]
   (:info db)))
