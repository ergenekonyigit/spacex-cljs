(ns spacex-cljs.rockets.views
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]
            [spacex-cljs.rockets.subs]
            [spacex-cljs.common.views :as common]))


(defn item-specs [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Specs"]
   [:li [:strong "Height: "] (-> item :height :meters) "m"]
   [:li [:strong "Diameter: "] (-> item :diameter :meters) "m"]
   [:li [:strong "Mass: "] (-> item :mass :kg) "kg"]
   [:li [:strong "Boosters: "] (-> item :boosters)]
   [:li [:strong "Success Rate: "] (-> item :success_rate_pct) "%"]])


(defn item-engines [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Engines"]
   [:li [:strong "Engines: "] (-> item :engines :number)]
   [:li [:strong "Type: "] (-> item :engines :type)]
   [:li [:strong "Version: "] (-> item :engines :version)]
   [:li [:strong "Propellant 1: "] (-> item :engines :propellant_1)]
   [:li [:strong "Propellant 2: "] (-> item :engines :propellant_2)]])


(defn item-legs [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Landing Legs"]
   [:li [:strong "Number: "] (-> item :landing_legs :number)]
   [:li [:strong "Material: "] (-> item :landing_legs :material)]])


(defn item-payload [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Payload Weights"]
   (for [weight (:payload_weights item)]
     ^{:key (str (random-uuid))}
     [:div
      [:li [:strong "Name: "] (:name weight)]
      [:li [:strong "Weight: "] (:kg weight) "kg"]])])


(defn rocket-item [item]
  [:div
   {:style {:font-family "Ubuntu Mono"
            :padding "30px"
            :margin "50px 0px"
            :border-radius "8px"
            :background-color "#fff"
            :min-height "400px"
            :box-shadow "-5px 10px 50px -13px rgba(0,0,0,.2)"}}
   [:div.columns.is-variable.is-4-desktop
    [:div.column.is-two-fifths
     [common/item-header item]
     [common/item-content item]]
    [:div.column
     [item-specs item]
     [:br]
     [item-engines item]]
    [:div.column
     [item-legs item]
     [:br]
     [item-payload item]]]])


(defn rockets []
  (r/create-class
   {:component-did-mount #(dispatch [:load-rockets])
    :reagent-render
    (fn []
      (let [rockets @(subscribe [:rockets])]
        [:<>
         [common/header-section :rocket "SPACEX ROCKETS" "falcon-heavy-3.webp"]
         (if rockets
           [:div.container
            {:style {:margin-top "-100px"}}
            (for [rocket rockets]
              ^{:key (str (random-uuid))}
              [rocket-item rocket])]
           [:div
            {:style {:color "#000"
                     :text-align "center"
                     :padding "30px"
                     :margin "30px 0px"}}
            "Loading..."])]))}))
