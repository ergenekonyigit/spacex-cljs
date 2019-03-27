(ns spacex-cljs.common.views
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch]]))



(defn github-button []
  [:span.navbar-item
   [:a.button.has-text-dark.is-inverted
    {:href "https://github.com/ergenekonyigit/spacex-cljs"
     :target "_blank"}
    [:span
     {:style {:display "flex"
              :flex-direction "row"
              :align-items "center"}}
     [:svg
      {:style {:margin-right "5px"}
       :width "20px"
       :role "img",
       :view-box "0 0 24 24",
       :xmlns "http://www.w3.org/2000/svg"}
      [:path {:d "M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577
0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633
17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809
1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38
1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405
1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84
1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015
2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"}]]
     "Github"]]])


(defn hero-head [active]
  (let [toggle-class (fn [a k class1 class2]
                       (if (= (k @a) class1)
                         (swap! a assoc k class2)
                         (swap! a assoc k class1)))
        toggle-state (r/atom {:burger-class "navbar-burger burger"
                              :menu-class "navbar-menu"})
        reset-selected #(do
                          (dispatch [:selected-year "Any"])
                          (dispatch [:selected-status "Any"]))]
    (fn []
      [:div.hero-head
       [:header.navbar
        {:style {:position "absolute"
                 :left "0"
                 :right "0"}}
        [:div.container
         [:div.navbar-brand
          [:a.navbar-item.has-text-white
           {:style {:text-shadow "0px 2px 2px rgba(0,0,0,0.2)"}
            :href "#/"}
           [:strong "spacex-cljs"]]
          [:span
           {:data-target "navbarMenuHero"
            :class (:burger-class @toggle-state)
            :on-click #(do
                         (toggle-class toggle-state :burger-class
                                       "navbar-burger burger"
                                       "navbar-burger burger is-active")
                         (toggle-class toggle-state :menu-class
                                       "navbar-menu"
                                       "navbar-menu is-active"))}
           [:span.has-text-white]
           [:span.has-text-white]
           [:span.has-text-white]]]
         [:div#navbarMenuHero
          {:class (:menu-class @toggle-state)}
          [:div.navbar-end
           [:a.navbar-item
            {:href "#/launches"
             :on-click reset-selected
             :class (when (= active :launch) "is-active")}
            "Launches"]
           [:a.navbar-item
            {:href "#/rockets"
             :on-click reset-selected
             :class (when (= active :rocket) "is-active")}
            "Rockets"]
           [:a.navbar-item
            {:href "#/capsules"
             :on-click reset-selected
             :class (when (= active :capsule) "is-active")}
            "Capsules"]
           [github-button]]]]]])))


(defn header-section [active title image]
  [:section.hero.is-dark.is-large
   {:style {:background (str "url(../img/" image ")")
            :background-position "center center"
            :background-repeat "no-repeat"
            :background-attachment "fixed"
            :background-size "cover"}}
   [hero-head active]
   [:div.hero-body
    [:div.container
     [:div.title.is-2
      {:style {:font-family "Ubuntu Mono"
               :font-weight "700"
               :text-shadow "0px 2px 2px rgba(0,0,0,0.2)"}}
      title]]]])


(defn item-status [item]
  [:div
   {:style {:display "flex"
            :flex-direction "row"
            :justify-content "space-between"
            :flex-wrap "wrap"}}
   (if (:active item)
     [:span.tag.is-primary.is-medium
      {:style {:margin-bottom "10px"}}
      "STATUS ACTIVE"]
     [:span.tag.is-danger.is-medium
      {:style {:margin-bottom "10px"}}
      "STATUS INACTIVE"])
   [:a
    {:href (:wikipedia item)
     :target "_blank"}
    [:span.tag.is-light.is-medium
     "Read on Wikipedia"]]])


(defn item-header [item]
  [:<>
   [:div.title.has-text-weight-bold
    (-> item :name .toUpperCase)]
   [item-status item]])


(defn item-content [item]
  [:p
   {:style {:margin-top "24px"}}
   (:description item)])
