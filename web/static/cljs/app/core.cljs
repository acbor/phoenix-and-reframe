(ns app.core
  (:require [reagent.core :as r :refer-macros [with-let]]))

(defn button [count text update class]
  [:button.btn
   {:on-click #(swap! count update)
    :class class}
   text])

(defn counter [count]
  [:p "Value = " @count])

(defn app []
  (with-let [count (r/atom 0)]
    [:div.container>div.jumbotron
     [counter count]
     [:div.btn-group
      [button count "-1" dec :btn-danger]
      [button count "+1" inc :btn-primary]]]))

(defn element-by-id [id]
  (.getElementById js/document id))

(defn mount-root []
  (let [root (element-by-id "root")]
    (r/render app root)))

(defn run []
  (mount-root))
