(ns spacex-cljs.navigation.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            [spacex-cljs.navigation.subs]))


(defn navigation-panel [active-panel]
  (let [[panel panel-name] @active-panel]
    [:div
     (when panel [panel])]))


(defn main-panel []
  [navigation-panel (subscribe [:active-panel])])
