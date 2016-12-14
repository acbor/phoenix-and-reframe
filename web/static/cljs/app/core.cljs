(ns app.core
  (:require [reagent.core :as r :refer-macros [with-let]]))

(def counter-value (r/atom 0))

(defn button [text update class]
  [:button.btn
   {:on-click update
    :class class}
   text])

(defn counter [counter-value]
  [:p "Value = " @counter-value])

(defn app [channel]
  (with-let [counter-updater (fn [update-fn]
                               #(.push channel "set" #js{:value (update-fn @counter-value)}))]
    [:div.container>div.jumbotron
     [counter counter-value]
     [:div.btn-group
      [button "-1" (counter-updater dec) :btn-danger]
      [button "+1" (counter-updater inc) :btn-primary]]]))

(defn element-by-id [id]
  (.getElementById js/document id))

(defn mount-root [channel]
  (let [root (element-by-id "root")]
    (r/render [app channel] root)))

(defn connect-socket []
  (let [user-token (.-userToken js/window)
        socket-url "/socket"
        socket (js/Socket. socket-url #js{:params #js{:token user-token}})]
    (doto socket .connect)))

(defn join-channel [socket]
  (let [channel (.channel socket "room:lobby" #js{})]
    (doto channel
      (.on "set" (fn [msg]
                   (let [value (aget msg "value")]
                     (reset! counter-value value))))
      (.join))))

(defn connect-to-ws []
  (-> (connect-socket)
      (join-channel)))

(defn run []
  (-> (connect-to-ws)
      (mount-root)))
