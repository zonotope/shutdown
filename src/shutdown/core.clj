(ns shutdown.core)

(defonce ^:private hooks (atom {}))

(defn- run-hooks []
  (doseq [hook (vals @hooks)]
    (hook)))

(defonce ^:private bind-hooks!
  (delay (let [runtime (Runtime/getRuntime)]
           (.addShutdownHook runtime (Thread. #'run-hooks))
           :bound)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; public api                                                               ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-hook!
  "Register a function to be called with no arguments when the
  application shuts down. `f` must be a function of no arguments, and
  `key` must be a unique."
  [key f]
  (force bind-hooks!)
  (when (contains? @hooks key)
    (throw (IllegalArgumentException. (str "Key " key " is already registered "
                                           "to another shutdown hook."))))
  (swap! hooks assoc key f)
  true)

(defn remove-hook!
  "De-registers the shutdown hook associated with `key`"
  [key]
  (if (contains? @hooks key)
    (let [hook (get @hooks key)]
      (swap! hooks dissoc key)
      true)
    false))

(defn registered-hooks
  "Lists all the currently registered shutdown hooks"
  []
  (keys @hooks))
