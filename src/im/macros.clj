(ns im.macros)

;; given a `defn` expression, rewrite it such that:
;;  - it includes an empty dict in an atom on the fn's metadata at :cache
;;  - on invocation, that dict is first interrogated for a val at the
;;    vec of inputs
;;  - if a val is found, it's returned
;;  - if a val is not found, the original function is called, and the
;;    result value is added to the cache dict

(defmacro cached [f]
  (let [fname (nth f 1)
        fargs (nth f 2)
        fbody (rest (rest (rest f)))]
    `(def ~fname
       (let [c# (atom {})]
         (with-meta
           (fn [& given-args#]
             (let [~fargs given-args#
                   v# (get-in @c# given-args#)]
               (if v#
                 v#
                 (get-in
                  (swap! c# assoc-in given-args# ~@fbody)
                  given-args#))))
           {:cache c#})))))
