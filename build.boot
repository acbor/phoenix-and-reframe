(set-env!
  :source-paths #{"web/static/cljs"}
  :dependencies '[[adzerk/boot-cljs          "1.7.228-1"]
                  [adzerk/boot-reload        "0.4.12"]
                  [org.clojure/clojure       "1.8.0"]
                  [org.clojure/clojurescript "1.9.229"]
                  [reagent                   "0.6.0"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[adzerk.boot-reload :refer [reload]])

(def asset-path  "/cljs")

(task-options!
  notify {:audible true
          :visual true}
  target {:dir #{"priv/static/cljs"}}
  cljs   {:compiler-options {:asset-path (str asset-path "/main.out")}}
  reload {:on-jsload 'app.core/run
          :cljs-asset-path asset-path})

(deftask build []
  (comp
    (notify :title "Prod")
    (cljs)
    (target)))

(deftask dev []
  (comp
    (watch)
    (notify :title "Dev")
    (reload)
    (cljs)
    (target)))
