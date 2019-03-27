(ns ^:figwheel-no-load spacex-cljs.dev
  (:require
    [spacex-cljs.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
