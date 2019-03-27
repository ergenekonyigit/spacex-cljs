(ns spacex-cljs.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [spacex-cljs.routes :refer [app-routes]]
            [spacex-cljs.navigation.views :as views]))


(defn mount-root []
  (rf/clear-subscription-cache!)
  (r/render [views/main-panel] (.getElementById js/document "app")))


(defn init! []
  (app-routes)
  (rf/dispatch-sync [:initialise-db])
  (mount-root))
