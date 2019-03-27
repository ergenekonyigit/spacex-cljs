(ns spacex-cljs.home.views
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]
            [secretary.core :as secretary]
            [spacex-cljs.home.subs]
            [spacex-cljs.common.views :refer [hero-head]]))


(defn about-spacex [info]
  [:<>
   [:p.title.is-spaced.has-text-white
    {:style {:font-weight "700"}}
    "ABOUT SPACEX"]
   [:p.subtitle.is-spaced
    (:summary info)]])


(defn company-info [info]
  [:<>
   [:p.title.is-spaced.has-text-white
    {:style {:font-weight "700"}}
    "COMPANY INFO"]
   [:p.subtitle.is-spaced
    [:li "Founded: " (:founded info)]
    [:li "Employees: " (:employees info)]
    [:li "Launch Sites: " (:launch_sites info)]
    [:li "Test Sites: " (:test_sites info)]
    [:li "Vehicles: " (:vehicles info)]]])


(defn company-leaders [info]
  [:<>
   [:p.title.is-spaced.has-text-white
    {:style {:font-weight "700"}}
    "COMPANY LEADERSHIP"]
   [:p.subtitle.is-spaced
    [:li "CEO: " (:ceo info)]
    [:li "COO: " (:coo info)]
    [:li "CTO: " (:cto info)]
    [:li "CTO of Propulsion: " (:cto_propulsion info)]]])


(defn home []
  (r/create-class
   {:component-did-mount #(dispatch [:load-info])
    :reagent-render
    (fn []
      (let [info @(subscribe [:info])]
        [:section.hero.is-dark.is-fullheight
         {:style {:background "url(../img/falcon-9.webp)"
                  :background-position "center center"
                  :background-repeat "no-repeat"
                  :background-attachment "fixed"
                  :background-size "cover"}}
         [hero-head]
         [:div.hero-body
          [:div.container
           {:style {:font-family "Ubuntu Mono"
                    :text-shadow "0px 2px 2px rgba(0,0,0,0.2)"}}
           [:div.columns.is-desktop
            [:div.column.is-half-desktop.is-two-thirds-tablet
             (when info
               [:<>
                [about-spacex info]
                [company-info info]
                [company-leaders info]])]]]]]))}))
