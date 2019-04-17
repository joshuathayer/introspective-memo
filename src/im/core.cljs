(ns im.core
  (:require-macros [im.macros :refer [cached]])
  (:require [cljs.cache :as cache]))

(defn has? [f k]
  (get (:cache (meta f)) k))

(comment
  (cached (defn foo [x] (+ x 100)))
  (cached 10)
  (has? foo 9)
  (has? foo 10)
  )
