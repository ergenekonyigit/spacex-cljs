(ns spacex-cljs.launches.views
  (:require [cljs.core.match :refer-macros [match]]
            [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]
            [spacex-cljs.launches.subs]
            [spacex-cljs.common.views :as common]))


(defn year-select [launches]
  (let [years (-> (map #(:launch_year %) launches)
                  (set)
                  (sort)
                  (reverse)
                  (into ["Any"]))]
  [:div
   {:style {:width "220px"
            :display "flex"
            :flex-direction "row"
            :justify-content "space-between"
            :align-items "center"
            :margin-bottom "10px"}}
   [:span "Year: "]
   [:div.select
    [:select
     {:style {:width "160px"}
      :on-change #(dispatch [:selected-year (-> % .-target .-value)])}
     (for [year years]
       ^{:key year}
       [:option
        {:value year}
        year])]]]))


(defn status-select []
  [:div
   {:style {:width "220px"
            :display "flex"
            :flex-direction "row"
            :justify-content "space-between"
            :align-items "center"}}
   [:span "Status: "]
   [:div.select
    [:select
     {:style {:width "160px"}
      :on-change #(dispatch [:selected-status (-> % .-target .-value)])}
     [:<>
      [:option {:value "Any"} "Any"]
      [:option {:value "true"} "Successful"]
      [:option {:value "false"} "Unsuccessful"]]]]])


(defn filter-section [launches]
  [:div.container
   {:style {:padding "30px"
            :border-radius "8px"
            :background-color "#fff"
            :min-height "200px"
            :box-shadow "-5px 10px 60px -13px rgba(0,0,0,.2)"}}
   [:div.title "Filter Launches"]
   [year-select launches]
   [status-select]])


(defn item-status [item]
  [:div
   (if (:launch_success item)
     [:span.tag.is-primary.is-medium
      {:style {:margin "0px 10px 10px 0px"}}
      "LAUNCH SUCCESSFUL"]
     [:span.tag.is-danger.is-medium
      {:style {:margin "0px 10px 10px 0px"}}
      "LAUNCH UNSUCCESSFUL"])
   [:a
    {:href (-> item :links :mission_patch :video_link)
     :target "_blank"}
    [:span.tag.is-light.is-medium
     "Watch on YouTube"]]])


(defn item-header [item]
  [:<>
   [:div
    {:style {:margin-bottom "10px"}}
    [:div.title.has-text-weight-bold
     {:style {:margin-top "10px"}}
     (-> item :mission_name .toUpperCase)]]
   [item-status item]])


(defn item-detail [item]
  [:<>
   [:div.subtitle.has-text-weight-bold
    {:style {:margin-bottom "12px"}}
    "Mission Details"]
   [:li [:strong "Flight Number: "] (-> item :flight_number)]
   [:li [:strong "Launch Date: "] (.toDateString (js/Date. (:launch_date_utc item)))]
   [:li [:strong "Launch Site: "] (-> item :launch_site :site_name_long)]
   [:br]
   [:li [:strong "Rocket Name: "] (-> item :rocket :rocket_name)]
   [:li [:strong "Rocket Type: "] (-> item :rocket :rocket_type)]
   [:li [:strong "Payload: "] (-> item :rocket :second_stage :payloads (first) :payload_type)]
   [:li [:strong "Payload Nationality: "] (-> item :rocket :second_stage :payloads (first) :nationality)]
   [:li [:strong "Payload Mass: "] (-> item :rocket :second_stage :payloads (first) :payload_mass_kg) "kg"]])


(defn item-content [item]
  [:p
   {:style {:margin-top "24px"}}
   (:details item)])


(defn launch-item [item]
  [:div
   {:style {:padding "30px"
            :margin "50px 0px"
            :border-radius "8px"
            :background-color "#fff"
            :min-height "400px"
            :box-shadow "-5px 10px 50px -13px rgba(0,0,0,.2)"}}
   [:div.columns.is-variable.is-4-desktop
    [:div.column.is-one-quarter
     [:div
      [:img
       {:src (-> item :links :mission_patch_small)}]
      [item-header item]]]
    [:div.column
      [item-content item]]
    [:div.column
      [item-detail item]]]])


(defn filter-launches [launches selected-year selected-status]
  (match [selected-year selected-status]
         ["Any" "Any"] launches
         ["Any" _] (filter #(= selected-status (str (:launch_success %))) launches)
         [_ "Any"] (filter #(= selected-year (:launch_year %)) launches)
         :else (->> launches
                    (filter #(= selected-status (str (:launch_success %))))
                    (filter #(= selected-year (:launch_year %))))))


(defn launches []
  (r/create-class
   {:component-did-mount #(dispatch [:load-launches])
    :reagent-render
    (fn []
      (let [launches @(subscribe [:launches])
            selected-year @(subscribe [:selected-year])
            selected-status @(subscribe [:selected-status])
            filtered-launches (filter-launches launches selected-year selected-status)]
        (js/console.log (str "year: " selected-year " " "status: " selected-status))
        [:<>
         [common/header-section :launch "SPACEX LAUNCHES" "falcon-heavy-1.webp"]
         (if launches
           [:div.container
            {:style {:font-family "Ubuntu Mono"
                     :margin-top "-50px"}}
            [filter-section launches]
            (for [launch (reverse filtered-launches)]
              ^{:key (str (random-uuid))}
              [launch-item launch])]
           [:div
            {:style {:color "#000"
                     :text-align "center"
                     :padding "30px"
                     :margin "30px 0px"}}
            "Loading..."])]))}))
