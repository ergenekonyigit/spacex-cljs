(ns spacex-cljs.prod
  (:require
    [spacex-cljs.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
