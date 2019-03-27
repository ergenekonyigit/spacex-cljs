(ns spacex-cljs.capsules.views
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]
            [spacex-cljs.capsules.subs]
            [spacex-cljs.common.views :as common]))


(defn item-specs [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Specs"]
   [:li [:strong "Crew Capacity: "] (-> item :crew_capacity)]
   [:li [:strong "Height w/ Trunk: "] (-> item :height_w_trunk :meters) "m"]
   [:li [:strong "Diameter: "] (-> item :diameter :meters) "m"]
   [:li [:strong "Sidewall Angle: "] (-> item :sidewall_angle_deg) "°"]
   [:li [:strong "Orbit Duration: "] (-> item :orbit_duration_yr) " years"]])


(defn item-heat-shield [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Heat Shield"]
   [:li [:strong "Material: "] (-> item :heat_shield :material)]
   [:li [:strong "Size: "] (-> item :heat_shield :size_meters) "m"]
   [:li [:strong "Temperature: "] (-> item :heat_shield :temp_degrees) "°"]
   [:li [:strong "Partner: "] (-> item :heat_shield :dev_partner)]])


(defn item-thrusters [thrusters]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Thrusters"]
   (for [thruster thrusters]
     ^{:key (str (random-uuid))}
     [:div
      [:li [:strong "Type: "] (-> thruster :type)]
      [:li [:strong "Pods: "] (-> thruster :pods)]
      [:li [:strong "Fuel 1: "] (-> thruster :fuel_1)]
      [:li [:strong "Fuel 2: "] (-> thruster :fuel_2)]
      [:li [:strong "Thrust: "] (-> thruster :thrust :kN) "kN"]
      [:br]])])


(defn item-payload [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Payload"]
   [:li [:strong "Launch Payload Mass: "]
    (-> item :launch_payload_mass :kg) "kg"]
   [:li [:strong "Launch Payload Volume: "]
    (-> item :launch_payload_vol :cubic_meters) "m" [:sup "3"]]
   [:li [:strong "Return Payload Mass: "]
    (-> item :return_payload_mass :kg) "kg"]
   [:li [:strong "Return Payload Volume: "]
    (-> item :return_payload_vol :cubic_meters) "m" [:sup "3"]]])


(defn capsule-item [item]
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
     [item-heat-shield item]]
    [:div.column
     [item-payload item]
     [:br]
     [item-thrusters (:thrusters item)]]]])


(defn capsules []
  (r/create-class
   {:component-did-mount #(dispatch [:load-capsules])
    :reagent-render
    (fn []
      (let [capsules @(subscribe [:capsules])]
        [:<>
         [common/header-section :capsule "SPACEX CAPSULES" "capsule.webp"]
         (if capsules
           [:div.container
            {:style {:margin-top "-100px"}}
            (for [capsule capsules]
              ^{:key (str (random-uuid))}
              [capsule-item capsule])]
           [:div
            {:style {:color "#000"
                     :text-align "center"
                     :padding "30px"
                     :margin "30px 0px"}}
            "Loading..."])]))}))
