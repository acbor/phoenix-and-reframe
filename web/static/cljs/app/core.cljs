(ns app.core
  (:require [reagent.core :as r :refer-macros [with-let]]))

(defn button [counter]
  [:button {:on-click #(swap! counter inc)}
   "Clicked " @counter " times"])

(defn app []
  (with-let [counter (r/atom 0)]
    [button counter]))

(defn element-by-id [id]
  (.getElementById js/document id))

(defn mount-root []
  (let [root (element-by-id "root")]
    (r/render app root)))

(defn run []
  (mount-root))
