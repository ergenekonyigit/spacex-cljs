(ns spacex-cljs.routes
  (:import goog.history.Html5History)
  (:require [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary :refer-macros [defroute]]
            [spacex-cljs.common.events]
            [spacex-cljs.home.events]
            [spacex-cljs.launches.events]
            [spacex-cljs.rockets.events]
            [spacex-cljs.capsules.events]
            [spacex-cljs.navigation.events]
            [spacex-cljs.home.views :refer [home]]
            [spacex-cljs.launches.views :refer [launches]]
            [spacex-cljs.rockets.views :refer [rockets]]
            [spacex-cljs.capsules.views :refer [capsules]]))


(defn hook-browser-navigation! []
  (doto (Html5History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))


(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (rf/dispatch [:set-active-panel [home :home]]))

  (defroute "/launches" []
    (rf/dispatch [:set-active-panel [launches :launches]]))

  (defroute "/rockets" []
    (rf/dispatch [:set-active-panel [rockets :rockets]]))

  (defroute "/capsules" []
    (rf/dispatch [:set-active-panel [capsules :capsules]]))

  (hook-browser-navigation!))
